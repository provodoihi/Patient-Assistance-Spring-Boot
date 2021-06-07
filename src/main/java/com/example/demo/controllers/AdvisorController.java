package com.example.demo.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.Answer;
import com.example.demo.models.Question;
import com.example.demo.models.User;
import com.example.demo.repository.AnswerRepository;
import com.example.demo.repository.QuestionRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.services.UserDetailsImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class AdvisorController {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private QuestionRepository questionRepository;
	@Autowired
	private AnswerRepository answerRepository;
	
	@GetMapping("/advisor/patient")
	@PreAuthorize("hasRole('ADVISOR')")
	public ResponseEntity<List<User>> getUserByRolePatient(){
		try {
			List<User> users = new ArrayList<User>();
		    userRepository.findPatient().forEach(users::add);
		    if (users.isEmpty()) {
		    	return new ResponseEntity<>(HttpStatus.NO_CONTENT);
				}
		    return new ResponseEntity<>(users, HttpStatus.OK);				
			} 
		catch (Exception e) {
			return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/advisor/question")
	@PreAuthorize("hasRole('ADVISOR')")
	public ResponseEntity<List<Question>> getAllQuestion(){
		try {
			List<Question> questions = new ArrayList<Question>();
			questionRepository.findAll().forEach(questions::add);
			if (questions.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(questions, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/advisor/question/{id}")
	@PreAuthorize("hasRole('ADVISOR')")
	public ResponseEntity<List<Question>> getQuestionById(@PathVariable("id") long id){
		List<Question> questions = new ArrayList<Question>();
		questionRepository.findByUserId(id).forEach(questions::add);
		try {
			if (questions.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<>(questions, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
		} 
	}
	
	@GetMapping("/advisor/question/{userId}")
	@PreAuthorize("hasRole('ADVISOR')")
	public ResponseEntity<List<Question>> getQuestionByUserId(@PathVariable("userId") long userId){
		List<Question> questions = new ArrayList<Question>();
		questionRepository.findByUserId(userId).forEach(questions::add);
		try {
			if (questions.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<>(questions, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
		} 
	}
	
	@GetMapping("/advisor/answer")
	@PreAuthorize("hasRole('ADVISOR')")
	public ResponseEntity<List<Answer>> getAnswerByUserId(){
		try {
			List<Answer> answers = new ArrayList<Answer>();
			answerRepository.findAll().forEach(answers::add);
			if (answers.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(answers, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/advisor/answer/{userId}")
	@PreAuthorize("hasRole('ADVISOR')")
	public ResponseEntity<List<Answer>> getAllAnswer(@PathVariable("userId") long userId){
		try {
			List<Answer> answers = new ArrayList<Answer>();
			answerRepository.findByUserId(userId).forEach(answers::add);
			if (answers.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(answers, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/advisor/answer")
	@PreAuthorize("hasRole('ADVISOR')")
	public ResponseEntity<Answer> addAnswer(@RequestBody Answer answer){
		try {
			Answer answerInfo = answerRepository.
					save(new Answer(answer.getUserId(),answer.getQuestionId(),answer.getQuestionDetail(),answer.getAnswerDetail()));
			return new ResponseEntity<>(answerInfo, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping("/advisor/answer/{id}")
	@PreAuthorize("hasRole('ADVISOR')")
	public ResponseEntity<Answer> updateAnswerById(@PathVariable("id") long id,@RequestBody Answer answer){
		Optional<Answer> answerId = answerRepository.findById(id);
		if (answerId.isPresent()) {
			Answer answerInfo = answerId.get();
			answerInfo.setAnswerDetail(answer.getAnswerDetail());;
			return new ResponseEntity<>(answerRepository.save(answerInfo), HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}	
	}
	
	@DeleteMapping("/advisor/answer/{id}")
	@PreAuthorize("hasRole('ADVISOR')")
	public ResponseEntity<Answer> deleteAnswerById(@PathVariable("id") long id){
		try {
			answerRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/advisor/myProfile")
	@PreAuthorize("hasRole('ADVISOR')")
	public ResponseEntity<User> getMyProfile(){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		long userId = userDetails.getId();
		Optional<User> user = userRepository.findById(userId);
		if (user.isPresent()) {
			return new ResponseEntity<>(user.get(),HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PutMapping("/advisor/myProfile")
	@PreAuthorize("hasRole('ADVISOR')")
	public ResponseEntity<User> updateMyProfile(@RequestBody User user){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		long userId = userDetails.getId();
		Optional<User> userID = userRepository.findById(userId);
		if (userID.isPresent()) {
			User userInfo = userID.get();
			userInfo.setFirstname(user.getFirstname());
			userInfo.setLastname(user.getLastname());
			userInfo.setAdress(user.getAddress());
			userInfo.setAge(user.getAge());
			return new ResponseEntity<>(userRepository.save(userInfo),HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

}
