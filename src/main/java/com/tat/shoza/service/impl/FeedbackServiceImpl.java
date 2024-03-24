package com.tat.shoza.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import com.tat.shoza.dto.FeedbackDTO;
import com.tat.shoza.model.Feedback;
import com.tat.shoza.repository.FeedbackRepository;
import com.tat.shoza.service.FeedbackService;

@Service
public class FeedbackServiceImpl implements FeedbackService{

	@Autowired
	private FeedbackRepository feedbackRepository;
	
	@Override
	public Feedback save(Feedback feedback) {
		// TODO Auto-generated method stub
		return feedbackRepository.save(feedback);
	}
	
	@Override
	public Page<Feedback> pageListFeedBackDESC(Pageable pageable) {
		// TODO Auto-generated method stub
		return feedbackRepository.pageListFeedBackDESC(pageable);
	}
	
	@Override
	public Page<Feedback> pageListFeedBackUnread(Pageable pageable) {
		// TODO Auto-generated method stub
		return feedbackRepository.pageListFeedBackUnread(pageable);
	}
	
	@Override
	public List<Feedback> list() {
		// TODO Auto-generated method stub
		return feedbackRepository.findAll();
	}
	
	@Override
	public Feedback get(Long id) {
		// TODO Auto-generated method stub
		return feedbackRepository.getById(id);
	}
	
	@Override
	public Page<Feedback> pageListFeedBackCurrent(@Param("day")int day,@Param("month") int month,@Param("year") int year, Pageable pageable) {
		// TODO Auto-generated method stub
		return feedbackRepository.pageListFeedBackCurrent(day, month, year, pageable);
	}
}
