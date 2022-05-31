/**
 * 
 */
package com.tweetapp.repo;

import org.springframework.data.repository.CrudRepository;

import com.tweetapp.model.Replies;
import com.tweetapp.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author 06232N744
 *
 */
@Repository
public interface RepliesRepo  extends CrudRepository<Replies, Long> {

}
