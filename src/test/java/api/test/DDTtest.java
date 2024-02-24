package api.test;

import org.testng.Assert;
import org.testng.annotations.Test;

import api.payload.User;
import api.payload.UserEndpoints;
import api.payload.UserEndpoints2;
import api.utilities.DataProviders;
import io.restassured.response.Response;

public class DDTtest {
	@Test(priority=1,dataProvider="Data",dataProviderClass=DataProviders.class)
	void testPostUser(String userID,String userName, String fmane, String lname,String useremail, String pwd, String ph )
	{
	  User userpayload=new User();
	  
	  userpayload.setId(Integer.parseInt(userID));
	  userpayload.setUsername(userName);
	  userpayload.setFirstname(fmane);
	  userpayload.setLastname(lname);
	  userpayload.setEmail(useremail);
	  userpayload.setPassword(pwd);
	  userpayload.setPhone(ph);
	  
	  Response response=UserEndpoints2.createUser(userpayload);
	  Assert.assertEquals(response.getStatusCode(),200);  
	}
	@Test(priority=2, dataProvider="UserNames", dataProviderClass=DataProviders.class)
	void deleteUser(String username)
	{
		Response response=UserEndpoints2.deleteUser(username);
		Assert.assertEquals(response.getStatusCode(),200);
	}

}
