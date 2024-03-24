package com.tat.shoza.controller.admin;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tat.shoza.base.BASE_FIELD;
import com.tat.shoza.component.AdminInfoHelper;
import com.tat.shoza.dto.FeedbackDTO;
import com.tat.shoza.model.Feedback;
import com.tat.shoza.service.FeedbackService;

@Controller
@RequestMapping(value = "/admin/feedback")
public class FeedbackController {

	@Autowired
	private AdminInfoHelper adminInfoHelper;
	
	@Autowired
	private FeedbackService feedbackService;
	
	@GetMapping
	public String pageFeedback(Model model, Authentication authentication, 
			@RequestParam(name = "page", defaultValue = "1")int page,
			@RequestParam(name = "size", defaultValue = "10")int size,
			@RequestParam(name = "act", defaultValue = "all")String act) {
		adminInfoHelper.dataAdminLayout("Góp Ý","admin/feedback", "feedback", authentication,model);
		Pageable pageable = PageRequest.of(page - 1, size);
		
		Page<Feedback> feedbackPage = feedbackService.pageListFeedBackDESC(pageable) ;
		
		if(act != null && act.equals("unread")) {
			feedbackPage = feedbackService.pageListFeedBackUnread(pageable);
		}
		if(act != null && act.equals("today")) {
			LocalDateTime localDateTime = LocalDateTime.now();
			int day = localDateTime.getDayOfMonth();
			int month = localDateTime.getMonthValue();
			int year = localDateTime.getYear();
			feedbackPage = feedbackService.pageListFeedBackCurrent(day, month, year, pageable);
			System.err.println(day + "/" + month + "/" + year);
		}
		
		
		List<FeedbackDTO> feedbackDTOs = new ArrayList<>();
		
		for(Feedback feedback : feedbackPage) {
			FeedbackDTO feedbackDTO = new FeedbackDTO(feedback);
			feedbackDTOs.add(feedbackDTO);
		}
		
		Page<FeedbackDTO> feedbackDTOList = new PageImpl<>(feedbackDTOs, pageable, feedbackPage.getTotalElements());
		
		model.addAttribute("list", feedbackDTOList);
		model.addAttribute("currentPage", feedbackDTOList.getNumber());
		model.addAttribute("totalPages", feedbackDTOList.getTotalPages());
		model.addAttribute("page", page);
		model.addAttribute("act", act);
		return BASE_FIELD.ADMIN_LAYOUT;
	}
	
	@GetMapping(value = "/read-all")
	public String readAll() {
		List<Feedback> list = feedbackService.list();
		for(Feedback feedback : list) {
			Feedback feedb = feedbackService.get(feedback.getId());
			feedb.setStatus(1);
			feedbackService.save(feedback);
		}
		return "redirect:/admin/feedback?act=all";
	}
	
	@ResponseBody
	@GetMapping(value = "/read-one")
	public void readOne(@RequestParam(name = "id") Long id) {
		Feedback feedback = feedbackService.get(id);
		feedback.setStatus(1);
		feedbackService.save(feedback);
	}
	
	
	
}
