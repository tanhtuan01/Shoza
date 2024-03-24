package com.tat.shoza.controller.admin;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tat.shoza.base.BASE_FIELD;
import com.tat.shoza.base.BASE_METHOD;
import com.tat.shoza.component.AdminInfoHelper;
import com.tat.shoza.component.UserHelper;
import com.tat.shoza.dto.ProductDTO;
import com.tat.shoza.model.Category;
import com.tat.shoza.model.ListProductImage;
import com.tat.shoza.model.Product;
import com.tat.shoza.service.CategoryService;
import com.tat.shoza.service.ListProductImageService;
import com.tat.shoza.service.ProductService;

@Controller
public class ProductController {

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private ProductService productService;

	@Autowired
	private ListProductImageService listProductImageService;
	
	@Autowired
	private UserHelper userHelper;
	
	@Autowired
	private AdminInfoHelper adminInfoHelper;

	Long savedIDProduct = null;

	@GetMapping(value = { "/admin/product/list", "/admin/product" })
	public String productListPage(Model model, @RequestParam(name = "sort", required = false) String sort,
			Authentication authentication) {
		adminInfoHelper.dataAdminLayout("Danh Sách Sản Phẩm","admin/list_product", "list-product", authentication, model);
		if(sort != null) {
			String Sort = sort.toUpperCase().trim();
			if(Sort.equals("DESC")) {
				List<Product> list = productService.listProductSortDESC();
				model.addAttribute("list", list);
			}
			if(Sort.equals("ASC")) {
				List<Product> list = productService.listProductSortASC();
				model.addAttribute("list", list);
			}
		}else {
			List<Product> list = productService.list();
			model.addAttribute("list", list);
		}
		return BASE_FIELD.ADMIN_LAYOUT;
	}

	@GetMapping(value = "/admin/product/add")
	public String addProductPage(Model model, Authentication authentication) {
		model.addAttribute("titleAddEdit", "Thêm Sản Phẩm");
		model.addAttribute("product", new ProductDTO());
		model.addAttribute("listCategory", categoryService.list());
		model.addAttribute("act", "add");
		model.addAttribute("enable", "enable");
		adminInfoHelper.dataAdminLayout("Thêm Mới Sản Phẩm","admin/add_product", "add-product", authentication, model);
		return BASE_FIELD.ADMIN_LAYOUT;
	}

	@PostMapping(value = "/admin/product/save")
	public String saveProduct(@ModelAttribute(name = "product") ProductDTO productDTO,
			@RequestParam(name = "productIMG") MultipartFile productIMG,
			@RequestParam(name = "productIMGList[]") MultipartFile[] productIMGList,
			RedirectAttributes redirectAttributes) {

		

		if (productIMG != null) {
			String imageName = BASE_METHOD.randomImgName();
			productDTO.setProductImage(imageName);
			String productIMGPath = BASE_METHOD.productPathUploadImg(imageName);
			Category category = categoryService.findById(productDTO.getIdCategory());
			Product product = new Product(productDTO.getId(), productDTO.getProductName(), productDTO.getProductTitle(),
					productDTO.getProductOldPrice(), productDTO.getProductDiscount(),
					productDTO.getProductCurrentPrice(), productDTO.getProductQuantity(), productDTO.getProductImage(),
					productDTO.getProductDescription());
			product.setCategory(category);
			product.setProductSold(0);

			try {
				savedIDProduct = productService.saveAndGetId(product);
			
					Files.write(Paths.get(productIMGPath), productIMG.getBytes());
				
			} catch (Exception e) {
				System.err.println("LỖI: " + e.getMessage());
			}
		}

		if (savedIDProduct != null) {
			if (productIMGList != null && productIMGList.length != 0) {
				if (productIMGList != null && productIMGList.length != 0) {
				    for (int i = 0; i < productIMGList.length; i++) {
				        MultipartFile file = productIMGList[i];
				        System.err.println("___________");
				        System.err.println(file.getOriginalFilename());
				        String imageName2 = BASE_METHOD.randomImgName() + i;
				        String productImagePath2 = BASE_METHOD.productPathUploadImg(imageName2);
				        try {
				            ListProductImage listProductImage = new ListProductImage(imageName2, productService.getById(savedIDProduct));
				            listProductImageService.save(listProductImage);
				            Files.write(Paths.get(productImagePath2), file.getBytes());
				        } catch (Exception e) {
				            // TODO: handle exception
				        }
				    }
				}
			}
		}
		redirectAttributes.addAttribute("notify","product-addsuccess");
		return "redirect:/admin/product/preview/" + savedIDProduct + "/";
	}

	@GetMapping(value = "/admin/product/edit")
	public String editProductPage(Authentication authentication,Model model, @RequestParam(name = "id", required = false) Long id) {
		model.addAttribute("titleAddEdit", "Chỉnh Sửa Sản Phẩm");
		model.addAttribute("act", "edit");
		model.addAttribute("listProduct", productService.list());
		model.addAttribute("product", new ProductDTO());
		adminInfoHelper.dataAdminLayout("Chỉnh Sửa Sản Phẩm","admin/add_product", "edit-product", authentication, model);
		if(id != null) {
			return "redirect:/admin/product/edit/"+id +"/";
		}
		return BASE_FIELD.ADMIN_LAYOUT;
	}

	@GetMapping(value = "/admin/product/edit/{id}")
	public String editProduct(Authentication authentication,Model model, @PathVariable(name = "id") Long id) {
		model.addAttribute("act", "edit");
		adminInfoHelper.dataAdminLayout("Chỉnh Sửa Sản Phẩm","admin/add_product", "edit-product", authentication, model);
		model.addAttribute("listProduct", productService.list());
		model.addAttribute("enable", "enable");
		Category category = categoryService.getCategoryByProductId(id);
		Product product = productService.getById(id);
		ProductDTO productDTO = new ProductDTO(product.getId(), product.getProductName(), product.getProductTitle(),
				product.getProductOldPrice(), product.getProductDiscount(), product.getProductCurrentPrice(),
				product.getProductQuantity(), product.getProductImage(), product.getProductDescription(),
				category.getId());
		model.addAttribute("product", productDTO);
		List<Category> listCategory = categoryService.list();
		model.addAttribute("listCategory", listCategory);
		model.addAttribute("titleAddEdit", "Chỉnh Sửa Sản Phẩm");
		return BASE_FIELD.ADMIN_LAYOUT;
	}

	@PostMapping(value = "/admin/product/edit/save")
	public String saveEdit(@ModelAttribute(name = "product") ProductDTO productDTO,
			@RequestParam(name = "productIMG") MultipartFile productIMG, RedirectAttributes redirectAttributes) {
		Product product = productService.getById(productDTO.getId());
		String imageName = product.getProductImage();
		String imagePath = null;

		if (productIMG != null && !productIMG.isEmpty()) {
			imageName = BASE_METHOD.randomImgName();
			imagePath = BASE_METHOD.productPathUploadImg(imageName);
			product.setProductImage(imageName);
		}
		Category category = categoryService.findById(productDTO.getIdCategory());
		product.setData(productDTO.getProductName(), productDTO.getProductTitle(), productDTO.getProductOldPrice(),
				productDTO.getProductDiscount(), productDTO.getProductCurrentPrice(), productDTO.getProductQuantity(),
				product.getProductImage(), productDTO.getProductDescription(), category);

		try {
			savedIDProduct = productService.saveAndGetId(product);
			if(imagePath != null) {
				Files.write(Paths.get(imagePath), productIMG.getBytes());
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		redirectAttributes.addAttribute("notify","product-editsuccess");
		return "redirect:/admin/product/preview/"+savedIDProduct;
	}

	@GetMapping(value = "/admin/product/preview")
	public String previewProduct(Model model, Authentication authentication) {
		model.addAttribute("product", new ProductDTO());
		List<Product> list = productService.list();
		model.addAttribute("list", list);
		adminInfoHelper.dataAdminLayout("Chi Tiết Sản Phẩm","admin/preview_product", "preview", authentication, model);
		return BASE_FIELD.ADMIN_LAYOUT;
	}

	@GetMapping(value = "/admin/product/preview/{id}")
	public String previewProductId(Authentication authentication,Model model, @PathVariable(name = "id") Long id) {
		adminInfoHelper.dataAdminLayout("Chi Tiết Sản Phẩm","admin/preview_product", "preview", authentication, model);
		Product product = productService.getById(id);
		Category category = categoryService.getCategoryByProductId(id);
		ProductDTO productDTO = new ProductDTO(product.getProductName(), product.getProductTitle(),
				product.getProductOldPrice(), product.getProductDiscount(), product.getProductCurrentPrice(),
				product.getProductQuantity(), product.getProductImage(), product.getProductDescription(),
				product.getProductSold(), category.getCategoryName());
		List<Product> list = productService.list();
		model.addAttribute("list", list);
		model.addAttribute("product", productDTO);
		return BASE_FIELD.ADMIN_LAYOUT;
	}

	@GetMapping(value = "/admin/product/delete/{id}")
	public String deleteProduct(@PathVariable(name = "id") Long id, RedirectAttributes redirectAttributes) {
		productService.delete(id);
		redirectAttributes.addAttribute("notify","product-delsuccess");
		return "redirect:/admin/product/list";
	}
	
}
