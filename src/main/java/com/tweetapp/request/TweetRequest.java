package com.tweetapp.request;

import java.util.Date;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import org.springframework.data.annotation.Id;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Component
@Getter
@Setter
@ToString
public class TweetRequest {
	@Id
	private int tweetId;
	@NotBlank(message = "userName cannot be Null")
	private String userName;
	@NotBlank(message = "Tweet cannot be Null")
	private String tweet;
	private Date created;
}
