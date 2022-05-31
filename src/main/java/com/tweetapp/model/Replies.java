/**
 * 
 */
package com.tweetapp.model;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author 06232N744
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="replies")
@ToString(exclude = {"tweet"})
public class Replies {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;	

	@ManyToOne(fetch = FetchType.EAGER)
    @MapsId("tweetId")
    @JoinColumn(name = "tweetId")
    private Tweet tweet;
	
	public String repliesComment;

}