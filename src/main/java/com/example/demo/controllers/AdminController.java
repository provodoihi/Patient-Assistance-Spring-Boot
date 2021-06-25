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
import org.springframework.web.bind.annotation.RequestParam;
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
public class AdminController {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private QuestionRepository questionRepository;
	
	@Autowired
	private AnswerRepository answerRepository;
	
	@Autowired
	private HospitalClinicRepository hospitalClinicRepository;
	
	@GetMapping("/admin")
	@PreAuthorize("hasRole('ADMIN')")
	public String adminHomePage() {
		return "Welcome Admin";
	}
	
	
	@GetMapping("/admin/user")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<User>> getAllUsers(){
		try {
			List<User> users = new ArrayList<User>();
			userRepository.findAll().forEach(users::add);
			if (users.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(users, HttpStatus.OK);
		}
		catch (Exception e) {
			return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
		}
			
	}
	
	@GetMapping(value = "/admin/role/admin")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<User>> getUserByRoleAdmin(){
		try {
			List<User> users = new ArrayList<User>();
		    userRepository.findAdmin().forEach(users::add);
		    if (users.isEmpty()) {
		    	return new ResponseEntity<>(HttpStatus.NO_CONTENT);
				}
		    return new ResponseEntity<>(users, HttpStatus.OK);				
			} 
		catch (Exception e) {
			return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping(value = "/admin/role/clinic")
	@PreAuthorize("hasRole('ADMIN')")
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
	
	@GetMapping(value = "/admin/role/patient")
	@PreAuthorize("hasRole('ADMIN')")
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
	
	@GetMapping(value = "/admin/role/advisor")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<User>> getUserByRoleAdvisor(){
		try {
			List<User> users = new ArrayList<User>();
		    userRepository.findAdvisor().forEach(users::add);
		    if (users.isEmpty()) {
		    	return new ResponseEntity<>(HttpStatus.NO_CONTENT);
				}
		    return new ResponseEntity<>(users, HttpStatus.OK);				
			} 
		catch (Exception e) {
			return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("admin/user/find")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<User>> searchUserByKeyword(@RequestParam("keyword") String keyword){
		try {
			List<User> users = new ArrayList<User>();
			if (keyword == null) {
				userRepository.findAll().forEach(users::add);
			}
			else {
				userRepository.search(keyword).forEach(users::add);
			}
			if (users.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(users, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("admin/user/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<User> getUserById(@PathVariable("id") long id){
		Optional<User> userId = userRepository.findById(id);
		if (userId.isPresent()) {
			return new ResponseEntity<>(userId.get(),HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PutMapping("admin/user/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<User> updateUserById(@PathVariable("id") long id, @RequestBody User user){
		Optional<User> userId = userRepository.findById(id);
		if (userId.isPresent()) {
			User userInfo = userId.get();
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
	
	@DeleteMapping("admin/user/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<User> deleteUserById(@PathVariable("id") long id){
		try {
			userRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.OK);
		} 
		catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/admin/hospitalclinic")
	@PreAuthorize("hasRole('ADMIN')")
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
	
	@PostMapping("admin/hospitalclinic")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<HospitalClinicInfo> createHosclinInfo(@RequestBody HospitalClinicInfo hospitalClinicInfo){
		try {
			HospitalClinicInfo hosInfo = hospitalClinicRepository
					.save(new HospitalClinicInfo(hospitalClinicInfo.getName(),hospitalClinicInfo.getAddress(),hospitalClinicInfo.getPhone(),hospitalClinicInfo.getSpeciality(),hospitalClinicInfo.getDescription(),hospitalClinicInfo.getLatitude(),hospitalClinicInfo.getLongtitude()));
			return new ResponseEntity<>(hosInfo, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping("admin/hospitalclinic/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<HospitalClinicInfo> updateHosclinInfoById(@PathVariable("id") long id,@RequestBody HospitalClinicInfo hospitalClinicInfo){
		Optional<HospitalClinicInfo> hospitalClinicId = hospitalClinicRepository.findById(id);
		if (hospitalClinicId.isPresent()) {
			HospitalClinicInfo hosInfo = hospitalClinicId.get();
			hosInfo.setAddress(hospitalClinicInfo.getAddress());
			hosInfo.setPhone(hospitalClinicInfo.getPhone());
			return new ResponseEntity<>(hospitalClinicRepository.save(hosInfo), HttpStatus.OK);		
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping("admin/hospitalclinic/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<HospitalClinicInfo> deleteHosclinInfoById(@PathVariable("id") long id){
		try {
			hospitalClinicRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("admin/question")
	@PreAuthorize("hasRole('ADMIN')")
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
	
	@GetMapping("admin/question/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Question> getQuestionById(@PathVariable("id") long id){
		Optional<Question> questionID = questionRepository.findById(id);
		if (questionID.isPresent()) {
			return new ResponseEntity<>(questionID.get(),HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping("/admin/question/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Question> deleteQuestionById(@PathVariable("id") long id){
		try {
			questionRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("admin/answer")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<Answer>> getAllAnswer(){
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
	
	@GetMapping("admin/answer/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Answer> getAnswerById(@PathVariable("id") long id){
		Optional<Answer> answerID = answerRepository.findById(id);
		if (answerID.isPresent()) {
			return new ResponseEntity<>(answerID.get(),HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping("/admin/answer/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Question> deleteAnswerById(@PathVariable("id") long id){
		try {
			questionRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("admin/myProfile")
	@PreAuthorize("hasRole('ADMIN')")
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
	
}
