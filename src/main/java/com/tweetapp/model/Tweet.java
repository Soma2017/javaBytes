package com.tweetapp.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="tweet")
@Accessors(chain = true)
@ToString(exclude = {"replies"})
public class Tweet {
	//@Transient
	//public static final String SEQUENCE_NAME = "tweet_sequence";
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="tweet_id")
	private Long tweetId;
	
	private String userName;
	private String tweet;
	private Date createdAt;
	@Column(name = "likes", nullable = false, columnDefinition = "int default 0") 
	//@ColumnDefault("0")
	private int likes;
	@OneToMany(mappedBy = "tweet")
	private List<Replies> replies;
}
