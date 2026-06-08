package Day1;

import io.restassured.RestAssured;
import pojo.Post;
import io.restassured.http.ContentType;
import io.restassured.response.Response;


import static org.hamcrest.Matchers.*;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import groovy.transform.stc.POJO;
import io.restassured.module.jsv.JsonSchemaValidator;

public class PostRequest {
	
	RestAssured request = new RestAssured();
	
	@BeforeClass
	public void setup() {
		request.baseURI="https://jsonplaceholder.typicode.com";
	}
	
	@Test
	public void PostWithStringBody() {
		
		String requestBody = "{\n" +
                "  \"title\": \"Test Post\",\n" +
                "  \"body\": \"This is a test post body\",\n" +
                "  \"userId\": 1\n" +
                "}";

		request.given()
		.contentType(ContentType.JSON)
		.body(requestBody)
		.when()
		.post("/posts")
		.then()
		.statusCode(201)
		.body("title", equalTo("Test Post"))                              
		.body("body", equalTo("This is a test post body"))   
		.body("userId", equalTo(1));
		
		/* title, userId or name.firstname are called JSONExpression
		 * and if you want to store some response value then we can use jsonPath() with same JSONExpression
		 * e.g. String city = response.jsonPath().getString("address.city");
		 * 
		 * JSONExpression is use to navigate and retrieve attributes and its values from response
		 */
		
	}
	
	/*
	 * Here we are using POJO methodology to send request instead for string body
	 * POJO is basically contains class Post or any name with variables, getter, setter and constructors
	 * 
	 * Once you use this class and set attribute values Rest assured convert Java object to JSON automatically called 
	 * Serialization then we can use same object in body for sending request
	 * 
	 * JSON to Java Object called De-serialization
	 */
	
	/*
	 * body() is basically raw form we can use this method while sending the request in given() block
	 * and validating at same point in then() block for response we get from when() block
	 */
	@Test
	public void PostUsingPOJO() {
		
		Post post = new Post();
		post.setTitle("Soham");
		post.setBody("Soham");
		post.setUserId(1);
				
		request.given()
		.contentType(ContentType.JSON)
		.body(post)
		.when()
		.post("/posts")
		.then()
		.statusCode(201)
		.body("title", equalTo("Soham"));
		
	}

}
