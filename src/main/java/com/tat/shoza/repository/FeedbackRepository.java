package com.tat.shoza.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tat.shoza.model.Feedback;

public interface FeedbackRepository extends JpaRepository<Feedback, Long>{

	@Query("select f from Feedback f order by f.id DESC")
	Page<Feedback> pageListFeedBackDESC(Pageable pageable);
	
	@Query("select f from Feedback f where f.status = 0")
	Page<Feedback> pageListFeedBackUnread(Pageable pageable);
	
	@Query("select f from Feedback f where DAY(f.createdAt) = :day AND MONTH(f.createdAt) = :month AND YEAR(f.createdAt) = :year")
	Page<Feedback> pageListFeedBackCurrent(@Param("day")int day, @Param("month")int month, @Param("year") int year, Pageable pageable);
}
