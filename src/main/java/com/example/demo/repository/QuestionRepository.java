package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

import com.example.demo.models.Question;

public interface QuestionRepository extends JpaRepositoryImplementation<Question,Long> {
	List<Question> findByUserId(long userId);

}
