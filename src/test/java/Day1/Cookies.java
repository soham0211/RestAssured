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

public class Cookies {

	RestAssured request = new RestAssured();
	
	@BeforeClass
	public void setup() {
		request.baseURI="https://httpbin.org";
	}
	
	//Scenario - Sending previously received cookie from server and checking if same session we are getting or not 
	//1. With single cookie
	//2. With multiple cookie
	//3. With sending cookie and extracting response
	
	@Test
	public void sendSingleCookie() {
		request.given()
		.cookie("session-id", "abc123xyz")
		.when()
		.get("/cookies")
		.then()	
		.statusCode(200)
		.body("cookies.session-id", equalTo("abc123xyz"));
	}
	
	@Test
	public void sendMultipleCookie() {
		request.given()
		.cookie("session-id", "abc123xyz")
		.cookie("token", "AB1234")
		.cookie("name", "soham")
		.when()
		.get("/cookies")
		.then()
		.statusCode(200)
		.body("cookies.session-id", equalTo("abc123xyz"))
		.body("cookies.token", equalTo("AB1234"))
		.body("cookies.name", equalTo("soham"));
	}
	@Test
	public void sendAndVerifyCookie() {
		request.given()
		.queryParam("Name", "Soham")
		.when()
		.get("/cookies/set")
		.then()
		.statusCode(200);
		
		Response response = request.given()
				.when()
				.get("/cookies")
				.then()
				.extract()
				.response();
		
		System.out.println(response.asPrettyString());
				
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
