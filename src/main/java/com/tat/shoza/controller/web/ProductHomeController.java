package com.tat.shoza.controller.web;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tat.shoza.base.BASE_FIELD;
import com.tat.shoza.component.CartHelper;
import com.tat.shoza.component.UserHelper;
import com.tat.shoza.component.WebInfoHelper;
import com.tat.shoza.dto.ListProductImageDTO;
import com.tat.shoza.dto.ProductDTO;
import com.tat.shoza.model.Comment;
import com.tat.shoza.model.CommentStatus;
import com.tat.shoza.model.ListProductImage;
import com.tat.shoza.model.Product;
import com.tat.shoza.model.User;
import com.tat.shoza.service.CategoryService;
import com.tat.shoza.service.CommentService;
import com.tat.shoza.service.CommentStatusService;
import com.tat.shoza.service.ListProductImageService;
import com.tat.shoza.service.ProductService;

@Controller
public class ProductHomeController {

	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private ListProductImageService productImageService;
	
	@Autowired
	private UserHelper authenticationHelper;
	
	@Autowired
	private CartHelper cartHelper;
	
	@Autowired
	private WebInfoHelper infoHelper;
	
	@Autowired
	private CommentService commentService;
	
	@Autowired
	private CommentStatusService commentStatusService;
	
	@GetMapping(value = "/shoza/product/{title}")
	public String productPage(Model model, Authentication authentication, 
			@PathVariable(name = "title")String title,
			@RequestParam(name = "page", defaultValue = "1")int page,
			@RequestParam(name = "size", defaultValue = "5")int size) {
		infoHelper.dataWebLayout("web/view_product", model, authentication);
		Product product = productService.getByCategoryTitle(title);
		List<ListProductImage> listProductImage = productImageService.listByProductId(product.getId());
		List<ListProductImageDTO> listProductImageDTOs = new ArrayList<>();
		ProductDTO productDTO = new ProductDTO(product.getId(),
					product.getProductName(), product.getProductTitle(),
					product.getProductOldPrice(), product.getProductDiscount(),
					product.getProductCurrentPrice(), product.getProductQuantity(),
					product.getProductImage(), product.getProductDescription(), product.getProductSold()
				);
		model.addAttribute("product", productDTO);
		for(ListProductImage imgImage : listProductImage) {
			ListProductImageDTO dto = new ListProductImageDTO(imgImage.getProductImage());
			listProductImageDTOs.add(dto);
		}
		if(authentication != null) {
			User user = authenticationHelper.getUserAuthen(authentication);
			List<CommentStatus> listCommentStatus = commentStatusService.listCommentStatusByUserProduct(user.getId(), product.getId());
			model.addAttribute("sizeCmt", listCommentStatus.size());
			
		}
		model.addAttribute("listImg", listProductImageDTOs);
		Pageable pageable = PageRequest.of(page - 1, size);
		Page<Comment> commentPage = commentService.pageListCommentByProduct(product.getId(), pageable);
		model.addAttribute("listCmt", commentPage);
		model.addAttribute("pageCmt", page);
		model.addAttribute("totalPageCmt", commentPage.getTotalPages());
		
		return BASE_FIELD.WEB_LAYOUT;
	}
	
	@PostMapping(value = "/shoza/comment/save")
	public String saveComment(Authentication authentication,@RequestParam(name = "product-id")Long id, @RequestParam(name = "comment-content") String content) {
		LocalDateTime localDateTime = LocalDateTime.now();
		Product product = productService.getById(id);
		User user = authenticationHelper.getUserAuthen(authentication);
		System.err.println("Comment");
		System.err.println(id);
		System.err.println(content);
		Comment comment = new Comment();
		comment.setContent(content);
		comment.setProduct(product);
		comment.setUser(user);
		comment.setCreatedAt(localDateTime.now() + "");
		commentService.save(comment);
		List<CommentStatus> listCommentStatus = commentStatusService.listCommentStatusByUserProduct(user.getId(), product.getId());
		
		for(CommentStatus commentStatus : listCommentStatus) {
			commentStatusService.delete(commentStatus.getId());
		}
		return "redirect:/shoza/product/"+ product.getProductTitle()+"#comment";
	}
	
}
