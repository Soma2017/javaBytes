package com.tweetapp.controller;

import static com.tweetapp.util.TweetConstant.ROOT_URL;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tweetapp.exception.TweetAppException;
import com.tweetapp.model.User;
import com.tweetapp.service.UserService;
import com.tweetapp.util.Envelope;

import io.micrometer.core.annotation.Timed;
import lombok.Generated;
import lombok.extern.slf4j.Slf4j;

@RequestMapping(value = ROOT_URL)
@RestController
@Slf4j
@Generated
@CrossOrigin(origins = "${client.url}")
public class UserController {

	@Autowired
	UserService userService;

	@PostMapping(value = "/register")
	@Timed(value = "registerUser.time", description = "Time taken to return registerUser")
	public ResponseEntity<Envelope<String>> registerUser(@RequestBody @Valid User user) {
		log.info("Registration for user {} {}", user.getFirstName(), user.getLastName());
		return userService.register(user);
	}

	@GetMapping(value = "/login")
	@Timed(value = "loginUser.time", description = "Time taken to return login")
	public ResponseEntity<Envelope<String>> login(@RequestParam("emailId") String emailId,
			@RequestParam("password") String password) throws TweetAppException {
		log.info("Login for user {} {}", emailId, password);
		return userService.login(emailId, password);
	}

	@GetMapping(value = "/forgot")
	@Timed(value = "forgotPassword.time", description = "Time taken to return forgotPassword")
	public ResponseEntity<Envelope<String>> forgotPassword(@RequestParam("userName") String userName,
			@RequestParam("newPassword") String Password) {
		log.info("forgot password for user {}", userName);

		return userService.forgotPassword(userName, Password);
	}

	@GetMapping(value = "/users")
	@Timed(value = "users.time", description = "Time taken to return users")
	public ResponseEntity<Envelope<List<User>>> users() {
		log.info("Get All Users");
		return userService.getAllusers();
	}

	@GetMapping(value = "/users/search")
	@Timed(value = "searchUserName.time", description = "Time taken to return searchUserName")
	public ResponseEntity<Envelope<User>> searchUserName(@RequestParam("userName") String userName) {
		log.info("Search UserName {}", userName);
		return userService.username(userName);
	}

}
