package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

import com.example.demo.models.Answer;

public interface AnswerRepository extends JpaRepositoryImplementation<Answer, Long> {
	List<Answer> findByUserId (long userId);

}
