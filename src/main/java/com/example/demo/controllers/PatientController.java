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
import com.example.demo.models.HospitalClinicInfo;
import com.example.demo.models.Question;
import com.example.demo.models.User;
import com.example.demo.repository.AnswerRepository;
import com.example.demo.repository.HospitalClinicRepository;
import com.example.demo.repository.QuestionRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.services.UserDetailsImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")


public class PatientController {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private QuestionRepository questionRepository;
	@Autowired
	private AnswerRepository answerRepository;
	@Autowired
	private HospitalClinicRepository hospitalClinicRepository;
	
	@GetMapping("/patient/hospitalclinic")
	@PreAuthorize("hasRole('PATIENT')")
	public ResponseEntity<List<HospitalClinicInfo>> getAllHospitalClinic(){
		try {
			List<HospitalClinicInfo> hospiclins = new ArrayList<HospitalClinicInfo>();
			hospitalClinicRepository.findAll().forEach(hospiclins::add);
			if (hospiclins.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(hospiclins, HttpStatus.OK);
		}
		catch (Exception e) {
			return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
		}
			
	}
	
	@GetMapping("/patient/clinic")
	@PreAuthorize("hasRole('PATIENT')")
	public ResponseEntity<List<User>> getUserByRoleClinic(){
		try {
			List<User> users = new ArrayList<User>();
		    userRepository.findClinic().forEach(users::add);
		    if (users.isEmpty()) {
		    	return new ResponseEntity<>(HttpStatus.NO_CONTENT);
				}
		    return new ResponseEntity<>(users, HttpStatus.OK);				
			} 
		catch (Exception e) {
			return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/patient/question")
	@PreAuthorize("hasRole('PATIENT')")
	public ResponseEntity<List<Question>> getQuestion(){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		long userId = userDetails.getId();
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
	
	@PostMapping("/patient/question")
	@PreAuthorize("hasRole('PATIENT')")
	public ResponseEntity<Question> createQuestion(@RequestBody Question question){
		try {
			Question questionInfo = questionRepository
					.save(new Question(question.getUserId(), question.getFullname(), question.getPhone(), question.getQuestionDetail()));
			return new ResponseEntity<>(questionInfo, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping("/patient/question/{id}")
	@PreAuthorize("hasRole('PATIENT')")
	public ResponseEntity<Question> deleteQuestionById(@PathVariable("id") long id){
		try {
			questionRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/patient/answer")
	@PreAuthorize("hasRole('PATIENT')")
	public ResponseEntity<List<Answer>> getAnswer(){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		long userId = userDetails.getId();
		List<Answer> answers = new ArrayList<Answer>();
		answerRepository.findByUserId(userId).forEach(answers::add);
		try {
			if (answers.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<>(answers, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/patient/myProfile")
	@PreAuthorize("hasRole('PATIENT')")
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

	@PutMapping("/patient/myProfile")
	@PreAuthorize("hasRole('PATIENT')")
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
