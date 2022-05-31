package com.tweetapp.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import javax.persistence.Id;
import org.springframework.data.annotation.Transient;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name="user")
public class User {
	
	//@Transient
	//public static final String SEQUENCE_NAME = "users_sequence";
	
	@Id
	//@Min(1)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userId;

	@NotBlank(message = "firstName cannot be null")
	@Pattern(regexp = "^[a-zA-Z]+$", message = "Invalid firstName")
	private String firstName;

	@NotBlank(message = "lastName cannot be null")
	@Pattern(regexp = "^[a-zA-Z]+$", message = "Invalid LastName")
	private String lastName;

	@Pattern(regexp = "male|female", message = "Invalid Gender")
	@NotBlank(message = "gender cannot be null")
	private String gender;

	@Pattern(regexp = "^\\d{4}\\-(0[1-9]|1[012])\\-(0[1-9]|[12][0-9]|3[01])$", message = "Date OF birth Should be in YYYY-MM-DD format")
	private String dob;

	@Pattern(regexp = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$", message = "Invalid EmailId")
	@NotBlank(message = "Enter Valid Email Id")
	private String emailId;

	@NotBlank(message = "password cannot be null")
//	@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[*.!@$%^&(){}[]:;<>,.?/~_+-=|\\]).{8,32}$", message = "Password Should Atleast contain one lowerCase,one UpperCase, one Special Char, one digit and length should be greater than 8.")
	private String password;
}
