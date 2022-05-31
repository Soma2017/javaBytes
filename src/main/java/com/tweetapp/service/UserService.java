package com.tweetapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.tweetapp.configuration.KafkaProducerConfig;
import com.tweetapp.exception.TweetAppException;
import com.tweetapp.model.User;
import com.tweetapp.repo.UserRepo;
import com.tweetapp.util.Envelope;
import com.tweetapp.util.TweetConstant;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserService {

	@Autowired
	UserRepo userRepository;

	//@Autowired
	//MongoOperations mongoperation;

	@Autowired
	private KafkaProducerConfig producer;

	public ResponseEntity<Envelope<String>> login(String userName, String password) throws TweetAppException {
		log.info(TweetConstant.IN_REQUEST_LOG, "login", userName.concat(" " + password));
		Optional<User> isValid = userRepository.findByemailIdAndPassword(userName, password);
		String userValid = isValid.isPresent() ? TweetConstant.LOGIN_SUCCESS : TweetConstant.LOGIN_FAILED;
		if (!isValid.isPresent())
			throw new TweetAppException(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST, userValid);
		log.info(TweetConstant.EXITING_RESPONSE_LOG, "login", userValid);
		return ResponseEntity.ok(new Envelope<String>(HttpStatus.OK.value(), HttpStatus.OK, userValid));
	}

	public ResponseEntity<Envelope<String>> register(User user) {
		log.info(TweetConstant.IN_REQUEST_LOG, "register", user.toString());
		Optional<User> isValid = userRepository.findByEmailId(user.getEmailId());
		String userRegister = isValid.isPresent() ? TweetConstant.USER_NAME_ALREADY_EXIST
				: TweetConstant.USER_NAME_REGISTERED_SUCCESSFULLY;
		if (isValid.isPresent())
			throw new TweetAppException(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST, userRegister);
		userRepository.save(user);
		log.info(TweetConstant.EXITING_RESPONSE_LOG, "register", userRegister);
		return ResponseEntity
				.ok(new Envelope<String>(HttpStatus.OK.value(), HttpStatus.OK, user.getEmailId() + " " + userRegister));
	}

	public ResponseEntity<Envelope<String>> forgotPassword(String userName, String password) {
		log.info(TweetConstant.IN_REQUEST_LOG, "forgotPassword", userName.concat(" " + password));
		Optional<User> findByEmailIdName = userRepository.findByEmailId(userName);
		if (!findByEmailIdName.isPresent()) {
			throw new TweetAppException(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST,
					TweetConstant.USER_NAME_NOT_PRESENT);
		}
		//producer.sendMessage("Forgot Password for :: " + userName.concat(" " + password));
		/*
		 * Query query = new Query();
		 * query.addCriteria(Criteria.where(TweetConstant.EMAIL_ID).is(userName));
		 * 
		 * Update update = new Update(); update.set(TweetConstant.PASSWORD, password);
		 * 
		 * User user = mongoperation.findAndModify(query, update, User.class);
		 */

		/*
		 * if (user == null) throw new
		 * TweetAppException(HttpStatus.INTERNAL_SERVER_ERROR.value(),
		 * HttpStatus.INTERNAL_SERVER_ERROR,
		 * TweetConstant.ERROR_WHILE_UPDATING_PASSWORD);
		 */
		//log.info(TweetConstant.EXITING_RESPONSE_LOG, "forgotPassword", user.toString());
		return ResponseEntity.ok(new Envelope<>(HttpStatus.OK.value(), HttpStatus.OK, TweetConstant.PASSWORD_UPDATED));
	}

	public ResponseEntity<Envelope<List<User>>> getAllusers() {
		log.info(TweetConstant.IN_REQUEST_LOG, "getAllusers", "Getting All Users");
		List<User> findAll = (List<User>) userRepository.findAll();
		log.debug("allUsers from list {}", findAll);
		if (findAll.isEmpty())
			throw new TweetAppException(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST,
					TweetConstant.NO_USERS_FOUND);
		log.info(TweetConstant.EXITING_RESPONSE_LOG, "getAllusers", findAll);
		return ResponseEntity.ok(new Envelope<>(HttpStatus.OK.value(), HttpStatus.OK, findAll));
	}

	public ResponseEntity<Envelope<User>> username(String userName) {
		log.info(TweetConstant.IN_REQUEST_LOG, "username", userName);
		Optional<User> userPresent = userRepository.findByEmailId(userName);
		log.debug("{}", userPresent);
		if (!userPresent.isPresent())
			throw new TweetAppException(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST, userPresent.toString());
		log.info(TweetConstant.EXITING_RESPONSE_LOG, "username", userPresent.isPresent() ? "Present" : "Not Present");
		return ResponseEntity.ok(new Envelope(HttpStatus.OK.value(), HttpStatus.OK, userPresent));
	}
}

