package com.example.demo.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

import com.example.demo.models.User;

@Repository
public interface UserRepository extends JpaRepositoryImplementation<User, Long> {
	Optional<User> findByUsername(String username);
	List<User> findByPhone(String phone);	
	
	@Query("SELECT s FROM User s WHERE s.username LIKE %?1%"
			+ "OR s.email LIKE %?1%"
			+ "OR s.firstname LIKE %?1%"
			+ "OR s.lastname LIKE %?1%"
			+ "OR s.phone LIKE %?1%")
	List<User> search(String keyword);
	
	// using native query because of the easy
	@Query(value = "select * from users u inner join user_roles ur on u.id = ur.user_id inner join roles r on r.id = ur.role_id where r.name=\"ROLE_ADMIN\"",nativeQuery = true)
	List<User> findAdmin();
	
	@Query(value = "select * from users u inner join user_roles ur on u.id = ur.user_id inner join roles r on r.id = ur.role_id  where r.name=\"ROLE_CLINIC\"",nativeQuery = true)
	List<User> findClinic();
	
	@Query(value = "select * from users u inner join user_roles ur on u.id = ur.user_id inner join roles r on r.id = ur.role_id  where r.name=\"ROLE_PATIENT\"",nativeQuery = true)
	List<User> findPatient();
	
	@Query(value = "select * from users u inner join user_roles ur on u.id = ur.user_id inner join roles r on r.id = ur.role_id  where r.name=\"ROLE_ADVISOR\"",nativeQuery = true)
	List<User> findAdvisor();
	

	Boolean existsByUsername(String username);

	Boolean existsByEmail(String email);
	
	Boolean existsByPhone(String phone);
}