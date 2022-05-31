package com.tweetapp.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.tweetapp.configuration.KafkaProducerConfig;
import com.tweetapp.exception.TweetAppException;
import com.tweetapp.model.Replies;
import com.tweetapp.model.Tweet;
import com.tweetapp.model.User;
import com.tweetapp.repo.TweetRepo;
import com.tweetapp.repo.UserRepo;
import com.tweetapp.request.TweetRequest;
import com.tweetapp.util.Envelope;
import com.tweetapp.util.TweetConstant;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TweetService {

	@Autowired
	TweetRepo tweetRepository;

	/*
	 * @Autowired MongoOperations mongoOperations;
	 */

	@Autowired
	UserRepo userRepository;

	@Autowired
    private KafkaProducerConfig producer;

	public ResponseEntity<Envelope<String>> postTweet(String userName, TweetRequest tweetRequest) {
		log.info(TweetConstant.IN_REQUEST_LOG, "postTweet", tweetRequest);
		Optional<User> findByEmailIdName = userRepository.findByEmailId(userName);
		long count = tweetRepository.count();
		log.info("total tweets " + count);
		if (!findByEmailIdName.isPresent())
			throw new TweetAppException(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST,
					TweetConstant.USER_NAME_NOT_PRESENT);
		//tweetRequest.setTweetId((int) count + 1);
		Tweet tweet = new Tweet();
		tweet.setUserName(tweetRequest.getUserName());
		tweet.setTweet(tweetRequest.getTweet());
		tweetRepository.save(tweet);
		log.info(TweetConstant.EXITING_RESPONSE_LOG, "postTweet", tweet);
		return ResponseEntity.ok(new Envelope<String>(HttpStatus.OK.value(), HttpStatus.OK, "Saved"));
	}

	public ResponseEntity<Envelope<List<Tweet>>> getAllTweet() {
		log.info(TweetConstant.IN_REQUEST_LOG, "getAllTweet", "getting All Tweets");
		List<Tweet> findAll = (List<Tweet>) tweetRepository.findAll();
		if (findAll.isEmpty())
			throw new TweetAppException(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR,
					TweetConstant.NO_TWEETS_FOUND);
		log.info(TweetConstant.EXITING_RESPONSE_LOG, "getAllTweet", findAll);
		return ResponseEntity.ok(new Envelope<List<Tweet>>(HttpStatus.OK.value(), HttpStatus.OK, findAll));
	}

	public ResponseEntity<Envelope<List<Tweet>>> getAllUserTweet(String userName) {
		log.info(TweetConstant.IN_REQUEST_LOG, "getAllUserTweet", userName);
		Optional<List<Tweet>> findByUserName = tweetRepository.findByUserName(userName);
		if (findByUserName.get().isEmpty())
			throw new TweetAppException(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR,
					TweetConstant.NO_TWEETS_FOUND);
		log.info(TweetConstant.EXITING_RESPONSE_LOG, "getAllUserTweet", findByUserName);
		return ResponseEntity.ok(new Envelope<List<Tweet>>(HttpStatus.OK.value(), HttpStatus.OK, findByUserName.get()));
	}
	

	public ResponseEntity<Envelope<String>> updateTweet(String userName, int tweetId, TweetRequest tweetRequest) {
		log.info(TweetConstant.IN_REQUEST_LOG, "updateTweet", tweetRequest);
		Tweet tweetExist=tweetAndUserValidation(userName, tweetId);
		Tweet tweet = new Tweet();
		tweet.setUserName(userName);
		tweet.setTweetId(tweetExist.getTweetId());
		tweet.setTweet(tweetRequest.getTweet());
		tweetRepository.save(tweet);
		if (tweet == null)
			throw new TweetAppException(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR,
					"Error While Updating Tweet");
		log.info(TweetConstant.EXITING_RESPONSE_LOG, "updateTweet", tweet);
		return ResponseEntity
				.ok(new Envelope<String>(HttpStatus.OK.value(), HttpStatus.OK, TweetConstant.TWEET_UPDATED));
	}

	public ResponseEntity<Envelope<String>> deleteTweet(String userName, int tweetId) {
		log.info(TweetConstant.IN_REQUEST_LOG, "deleteTweet", tweetId);
		tweetAndUserValidation(userName, tweetId);
		tweetRepository.deleteById(new Long(tweetId));
		log.info(TweetConstant.EXITING_RESPONSE_LOG, "deleteTweet", TweetConstant.TWEET_DELETED);
		return ResponseEntity
				.ok(new Envelope<String>(HttpStatus.OK.value(), HttpStatus.OK, TweetConstant.TWEET_DELETED));
	}

	public ResponseEntity<Envelope<String>> likeTweet(String userName, int tweetId) {
		log.info(TweetConstant.IN_REQUEST_LOG, "likeTweet", tweetId);
		tweetAndUserValidation(userName, tweetId);
		Optional<Tweet> findById = tweetRepository.findById(new Long(tweetId));
		Tweet tweet = findById.get();
		int oldLikes=tweet.getLikes();
		if(oldLikes>0) {
			tweet.setLikes(++oldLikes);
		}
		else {
			tweet.setLikes(1);
		}
		tweetRepository.save(tweet);
		if (tweet == null)
			throw new TweetAppException(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR,
					"Error While Liking");
		return ResponseEntity.ok(new Envelope<String>(HttpStatus.OK.value(), HttpStatus.OK, TweetConstant.LIKED_TWEET));
	}

	public ResponseEntity<Envelope<String>> replyTweet(String userName, int tweetId, String reply) {
		log.info(TweetConstant.IN_REQUEST_LOG, "replyTweet", reply);
		Optional<List<Tweet>> findByUserName = tweetRepository.findByUserName(userName);
		Tweet tweet = findByUserName.get().get(0);
		//Long tweetIdL=new Long(tweet.getTweetId());
		Tweet tweetExists=tweetAndUserValidation(userName, tweet.getTweetId().intValue());
		Replies replies=new Replies();
		replies.setRepliesComment(reply);
		replies.getTweet().setTweetId(tweetExists.getTweetId());
		 List<Replies> repliesList = new ArrayList<Replies>();
		 repliesList.add(replies);
		 tweet.setReplies(repliesList);
			
		log.info(TweetConstant.EXITING_RESPONSE_LOG, "replyTweet", tweet);
		if (tweet == null)
			throw new TweetAppException(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR,
					"Error While replying");
		return ResponseEntity
				.ok(new Envelope<String>(HttpStatus.OK.value(), HttpStatus.OK, TweetConstant.REPLIED_TO_TWEET));
	}

	private Tweet tweetAndUserValidation(String userName, int tweetId) {
		log.info(TweetConstant.IN_REQUEST_LOG, "tweetAndUserValidation :: Validating User", userName);
		Optional<Tweet> findById = tweetRepository.findById(new Long(tweetId));
		Optional<User> findByEmailIdName = userRepository.findByEmailId(userName);
		if (!findByEmailIdName.isPresent())
			throw new TweetAppException(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST,
					TweetConstant.USER_NAME_NOT_PRESENT);
		log.info(TweetConstant.IN_REQUEST_LOG, "tweetAndUserValidation :: Validating Tweet", tweetId);
		if (!findById.isPresent())
			throw new TweetAppException(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR,
					TweetConstant.NO_TWEETS_FOUND);
		log.info(TweetConstant.EXITING_RESPONSE_LOG, "tweetAndUserValidation", "User And Tweet Validated");
		return findById.get();
	}

}
