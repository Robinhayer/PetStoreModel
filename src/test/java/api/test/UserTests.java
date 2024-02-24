package api.test;

import org.testng.Assert;
import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import api.payload.User;
import api.payload.UserEndpoints;
import io.restassured.response.Response;

public class UserTests {
	Faker faker;
	User userPayload;
	public Logger logger;
	@BeforeClass
	public void setupData()
	{
		faker=new Faker();
		userPayload=new User();
		
		userPayload.setId(faker.idNumber().hashCode());
		userPayload.setUsername(faker.name().username());
		userPayload.setFirstname(faker.name().firstName());
		userPayload.setLastname(faker.name().lastName());
		userPayload.setEmail(faker.internet().safeEmailAddress());
		userPayload.setPassword(faker.internet().password(5, 10));
		userPayload.setPhone(faker.phoneNumber().cellPhone());
		//logs
				logger= LogManager.getLogger(this.getClass());
				
		
	}
	
	@Test(priority=1)
	public void testPostUser()
	{  logger.info("**********************  Creating an Account ************");
		Response response=UserEndpoints.createUser(userPayload);
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(),200);
		logger.info("**********************   Account Created ************");
			
	}
	
	@Test(priority=2)
	public void getuser()
	{logger.info("**********************  Reading User ************");
	  Response response=UserEndpoints.readUser(userPayload.getUsername());
	  response.then().log().all();
	  Assert.assertEquals(response.getStatusCode(),200);
	  logger.info("**********************  Reading done ************");
	}
	@Test(priority=3)
	void updateUserbyUserName()
	{
		logger.info("**********************  Updating User  ************");
		userPayload.setFirstname(faker.name().firstName());
		userPayload.setLastname(faker.name().lastName());
		userPayload.setEmail(faker.internet().safeEmailAddress());
		Response response=UserEndpoints.updateUser(this.userPayload.getUsername(), userPayload);
		response.then().log().body();
		Assert.assertEquals(response.getStatusCode(),200);
		
		//checking response after update
		Response responseafterupdate=UserEndpoints.readUser(this.userPayload.getUsername());
		Assert.assertEquals(responseafterupdate.getStatusCode(), 200);
		logger.info("**********************  User Updated ************");
	}
	@Test(priority=4)
	void delteusername()
	{ logger.info("**********************  Deleting Account ************");
		Response response=UserEndpoints.deleteUser(this.userPayload.getUsername());
		response.then().log().body();
		Assert.assertEquals(response.getStatusCode(),200);
		logger.info("**********************  Account Deleted ************");
	}
}
