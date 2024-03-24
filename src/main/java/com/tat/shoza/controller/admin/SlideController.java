package com.tat.shoza.controller.admin;

import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.tat.shoza.base.BASE_FIELD;
import com.tat.shoza.base.BASE_METHOD;
import com.tat.shoza.component.AdminInfoHelper;
import com.tat.shoza.component.UserHelper;
import com.tat.shoza.model.Slide;
import com.tat.shoza.service.SlideService;

@Controller
public class SlideController {

	@Autowired
	private SlideService slideService;

	@Autowired
	private UserHelper userHelper;
	
	@Autowired
	private AdminInfoHelper adminInfoHelper;
	
	@GetMapping(value = "/admin/slide")
	public String slidePage(Model model, Authentication authentication) {
		model.addAttribute("slides", slideService.list());
		adminInfoHelper.dataAdminLayout("Slide - Trình Chiếu","admin/slide", "slide", authentication, model);
		return BASE_FIELD.ADMIN_LAYOUT;
	}
	
	@PostMapping(value = "/admin/slide/addSlide")
	public String AddSlide(@RequestParam(name = "slideImg")MultipartFile slideImg,
			@RequestParam(name = "link") String link) {
		Slide slide = new Slide();
		if (slideImg != null && !slideImg.isEmpty()) {
			String imageName = BASE_METHOD.randomImgName();
			slide.setLink(link);
			slide.setImage(imageName);
			String slidePath = BASE_METHOD.slidePathUploadImg(imageName);
			
			try {
				Files.write(Paths.get(slidePath), slideImg.getBytes());
				slideService.save(slide);
			} catch (Exception e) {
				// TODO: handle exception
			}
				
		}
		return "redirect:/admin/slide";
		
	}
	
	@GetMapping(value = "/admin/slide/deleteSlide")
	public String deleteSlide(@RequestParam(name = "id")Long id) {
		slideService.delete(id);
		return "redirect:/admin/slide";
	}
	
}
