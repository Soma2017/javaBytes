package com.tweetapp.unit.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class UserServiceTest {
	/*
	 * 
	 * @Mock UserRepo userRepository;
	 * 
	 * @InjectMocks UserService userService;
	 * 
	 * @Mock MongoOperations mongoperation;
	 * 
	 * @Test void loginTest() {
	 * Mockito.when(userRepository.findByemailIdAndPassword("soumya@gmail.com",
	 * "123456")) .thenReturn(Optional.of(new User()));
	 * ResponseEntity<Envelope<String>> login =
	 * userService.login("soumya@gmail.com", "123456"); Assertions.assertEquals(
	 * ResponseEntity.ok(new Envelope<String>(HttpStatus.OK.value(), HttpStatus.OK,
	 * "Login Success")), login); }
	 * 
	 * @Test void loginTestFailed() {
	 * Mockito.when(userRepository.findByemailIdAndPassword("soumya@gmail.com",
	 * "1234")) .thenReturn(Optional.empty()); TweetAppException exceptionResponse =
	 * assertThrows(TweetAppException.class, () ->
	 * userService.login("soumya@gmail.com", "1234"));
	 * Assertions.assertEquals("Login Failed", exceptionResponse.getData());
	 * Assertions.assertEquals(HttpStatus.BAD_REQUEST,
	 * exceptionResponse.getStatus());
	 * Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(),
	 * exceptionResponse.getStatusCode()); }
	 * 
	 * @Test void registerTest() { User user = new User(); user.setUserId(1);
	 * user.setPassword("818009@Man"); user.setLastName("kadadi");
	 * user.setFirstName("Soumya"); user.setEmailId("soumya@gmail.com");
	 * user.setGender("male"); user.setDob("1998\06\04");
	 * ResponseEntity<Envelope<String>> register = userService.register(user);
	 * Assertions.assertEquals(ResponseEntity.ok(new
	 * Envelope<String>(HttpStatus.OK.value(), HttpStatus.OK, user.getEmailId() +
	 * " " + "UserName Registered Successfully")), register); }
	 * 
	 * @Test void registerTestFailed() { User user = getUser(1, "818009@Man",
	 * "kadadi", "Soumya", "soumya@gmail.com", "male", "1998\06\04");
	 * Mockito.when(userRepository.findByEmailIdName("soumya@gmail.com")).thenReturn
	 * (Optional.of(user)); TweetAppException exceptionResponse =
	 * assertThrows(TweetAppException.class, () -> userService.register(user));
	 * Assertions.assertEquals("UserName Already Exist",
	 * exceptionResponse.getData()); Assertions.assertEquals(HttpStatus.BAD_REQUEST,
	 * exceptionResponse.getStatus());
	 * Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(),
	 * exceptionResponse.getStatusCode()); }
	 * 
	 * @Test void forgotPasswordTestUserNotFound() { TweetAppException
	 * exceptionResponse = assertThrows(TweetAppException.class, () ->
	 * userService.forgotPassword("soumya@gmail.com", "8212"));
	 * Assertions.assertEquals(HttpStatus.BAD_REQUEST,
	 * exceptionResponse.getStatus());
	 * Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(),
	 * exceptionResponse.getStatusCode());
	 * Assertions.assertEquals("userName not Present", exceptionResponse.getData());
	 * }
	 * 
	 * @Test void forgotPasswordTest() {
	 * Mockito.when(userRepository.findByEmailIdName("soumya@gmail.com")).thenReturn
	 * (Optional.of(new User())); Query query = new Query();
	 * query.addCriteria(Criteria.where("emailId").is("soumya@gmail.com")); Update
	 * update = new Update(); update.set("password", "8212");
	 * Mockito.when(mongoperation.findAndModify(query, update,
	 * User.class)).thenReturn(new User()); ResponseEntity<Envelope<String>>
	 * forgotPassword = userService.forgotPassword("soumya@gmail.com", "8212");
	 * Assertions.assertEquals( ResponseEntity.ok(new
	 * Envelope<String>(HttpStatus.OK.value(), HttpStatus.OK, "Password Updated")),
	 * forgotPassword); }
	 * 
	 * @Test void forgotPasswordExceptionTest() {
	 * Mockito.when(userRepository.findByEmailIdName("soumya@gmail.com")).thenReturn
	 * (Optional.of(new User())); Query query = new Query();
	 * query.addCriteria(Criteria.where("emailId").is("soumya@gmail.com")); Update
	 * update = new Update(); update.set("password", "8212");
	 * Mockito.when(mongoperation.findAndModify(query, update,
	 * User.class)).thenReturn(null); TweetAppException exceptionResponse =
	 * assertThrows(TweetAppException.class, () ->
	 * userService.forgotPassword("soumya@gmail.com", "8212"));
	 * Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,
	 * exceptionResponse.getStatus());
	 * Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(),
	 * exceptionResponse.getStatusCode());
	 * Assertions.assertEquals("Error While Updating Password",
	 * exceptionResponse.getData()); }
	 * 
	 * @Test void usernameExceptionTest() { TweetAppException exceptionResponse =
	 * assertThrows(TweetAppException.class, () ->
	 * userService.username("soumya@gmail.com"));
	 * Assertions.assertEquals(HttpStatus.BAD_REQUEST,
	 * exceptionResponse.getStatus());
	 * Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(),
	 * exceptionResponse.getStatusCode());
	 * Assertions.assertEquals("userName not Present", exceptionResponse.getData());
	 * }
	 * 
	 * @Test void usernameTest() {
	 * Mockito.when(userRepository.findByEmailIdName("soumya@gmail.com")).thenReturn
	 * (Optional.of(new User())); ResponseEntity<Envelope<User>> usernameResponse =
	 * userService.username("soumya@gmail.com");
	 * Assertions.assertEquals(ResponseEntity.ok(new
	 * Envelope<>(HttpStatus.OK.value(), HttpStatus.OK, "soumya@gmail.com" +
	 * " User present in Database")), usernameResponse); }
	 * 
	 * @Test void getAllusersExceptionTest() { TweetAppException exceptionResponse =
	 * Assertions.assertThrows(TweetAppException.class, () ->
	 * userService.getAllusers()); Assertions.assertEquals(HttpStatus.BAD_REQUEST,
	 * exceptionResponse.getStatus());
	 * Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(),
	 * exceptionResponse.getStatusCode()); Assertions.assertEquals("No Users Found",
	 * exceptionResponse.getData()); }
	 * 
	 * @Test void getAllusersTest() { List<User> users = Arrays.asList( new User(1,
	 * "818009@Man", "kadadi", "Soumya", "soumya@gmail.com", "male", "1998\06\04"),
	 * new User(2, "818009@Man", "kadadi", "Soumya", "soum66@gmail.com", "male",
	 * "1998\06\04")); Mockito.when(userRepository.findAll()).thenReturn(users);
	 * ResponseEntity<Envelope<List<User>>> allusers = userService.getAllusers();
	 * Assertions.assertEquals(ResponseEntity.ok(new
	 * Envelope<>(HttpStatus.OK.value(), HttpStatus.OK, users)), allusers); }
	 * 
	 * private User getUser(int id, String password, String lastName, String
	 * firstName, String emailId, String gender, String date) { User user = new
	 * User(); user.setUserId(id); user.setPassword(password);
	 * user.setLastName(lastName); user.setFirstName(firstName);
	 * user.setEmailId(emailId); user.setGender(gender); user.setDob(date); return
	 * user; }
	 */}
