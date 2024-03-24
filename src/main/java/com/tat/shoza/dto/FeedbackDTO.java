package com.tat.shoza.dto;

import com.tat.shoza.model.Feedback;

public class FeedbackDTO {

	private Long id;
	
	private int status;
	
	private String content;
	
	private Long id_user;
	
	private String createdAt;
	
	

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Long getId_user() {
		return id_user;
	}

	public void setId_user(Long id_user) {
		this.id_user = id_user;
	}

	public FeedbackDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public FeedbackDTO (Feedback feedback) {
		this.content = feedback.getContent();
		this.status = feedback.getStatus();
		if(feedback.getUser() != null) {
			this.id_user = feedback.getUser().getId();
		}
		this.id = feedback.getId();
		this.createdAt = feedback.getCreatedAt();
	}

	@Override
	public String toString() {
		return "FeedbackDTO [id=" + id + ", status=" + status + ", content=" + content + ", id_user=" + id_user
				+ ", createdAt=" + createdAt + "]";
	}
	
	
	public String classList() {
		return (this.status == 0) ? "unread" : "seen";
	}
	
}
