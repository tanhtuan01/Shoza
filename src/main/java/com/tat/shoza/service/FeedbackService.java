package com.tat.shoza.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.tat.shoza.dto.FeedbackDTO;
import com.tat.shoza.model.Feedback;

public interface FeedbackService {

	Feedback save(Feedback feedback);
	
	Page<Feedback> pageListFeedBackDESC(Pageable pageable);
	
	Page<Feedback> pageListFeedBackUnread(Pageable pageable);
	
	List<Feedback> list();
	
	Feedback get(Long id);
	
	Page<Feedback> pageListFeedBackCurrent(int day, int month, int year, Pageable pageable);
}
