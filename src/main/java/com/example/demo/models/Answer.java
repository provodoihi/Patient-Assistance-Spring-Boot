package com.example.demo.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "answer")

public class Answer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@NotBlank
	private long userId;

	@NotBlank
	private long questionId;
	
	@NotBlank
	private String questionDetail;
	
	@NotBlank
	private String answerDetail;
	
	

	public Answer() {	
	}

	public Answer(long userId, long questionId, String questionDetail, String answerDetail) {
		this.userId = userId;
		this.questionId = questionId;
		this.questionDetail = questionDetail;
		this.answerDetail = answerDetail;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}
	
	public long getQuestionId() {
		return questionId;
	}

	public void setQuestionId(long questionId) {
		this.questionId = questionId;
	}

	public String getQuestionDetail() {
		return questionDetail;
	}

	public void setQuestionDetail(String questionDetail) {
		this.questionDetail = questionDetail;
	}

	public String getAnswerDetail() {
		return answerDetail;
	}

	public void setAnswerDetail(String answerDetail) {
		this.answerDetail = answerDetail;
	}


}
