package Day1;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;


import static org.hamcrest.Matchers.*;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import io.restassured.module.jsv.JsonSchemaValidator;


/*
 * Here we are performing data driven testing usually known as parameterization using dataProvider in TestNG
 * So we are basically returning data from 2D array with type as Object so we can pass any data type
 * and same is then pass to request method as parameters
 * 
 * $ means entire response
 */
public class Parameterization {

	RestAssured request = new RestAssured();
	@BeforeClass
	public void setup() {
		
		request.baseURI= "https://jsonplaceholder.typicode.com/";
		
	}
	@DataProvider(name = "userId")
	public Object[][] userIdProvider() {
		
		return new Object [][] {
			{1},{2},{3},{4},{5}
		};
		
		}
	
	@DataProvider(name = "endpoints")
	public Object[][] endPoints() {
		
		return new Object [][] {
			{"/users", 200},
            {"/posts", 200},
            {"/comments", 200},
            {"/albums", 200},
            {"/todos", 200}
		};
		
		}
	
	
	@Test(dataProvider="userId")
	public void userWithDifferentIDS(int id) {
		
		request.given()
		.when()
		.get("/users/"+ id)
		.then()
		.statusCode(200)
		.body("id", equalTo(id))
		.body("name", notNullValue());
	}
	
	@Test(dataProvider="endpoints")
	public void differentEndpointsWithStatuscodes(String endpoint, int statuscode) {
		request.given()
		.when()
		.get(endpoint)
		.then()
		.statusCode(statuscode)
		.body("$", not(empty()))
		.time(lessThan(2000L));
	}
	
	
	
	}