package com.tweetapp.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import org.springframework.stereotype.Repository;

import com.tweetapp.model.Tweet;

@Repository
public interface TweetRepo extends CrudRepository<Tweet, Long>{

	@Query(value="select t from Tweet t where t.userName= :userName")
	Optional<List<Tweet>> findByUserName(String userName);

}
