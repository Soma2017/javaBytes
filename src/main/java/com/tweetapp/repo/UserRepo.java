package com.tweetapp.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tweetapp.model.User;

@Repository
public interface UserRepo extends CrudRepository<User, Long> {
	@Query(value="select u.* from user u where u.email_Id=:emailId and u.password=:password",nativeQuery=true)
	Optional<User> findByemailIdAndPassword(String emailId, String password);

	@Query(value="select u from User u where u.emailId= :emailId")
	Optional<User> findByEmailId(String emailId);

}
