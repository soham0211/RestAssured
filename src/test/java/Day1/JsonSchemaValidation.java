package Day1;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;


import static org.hamcrest.Matchers.*;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import io.restassured.module.jsv.JsonSchemaValidator;

public class JsonSchemaValidation {

	/*
	 * JsonSchemaValidation - The purpose of JsonSchemaValidation is to compare the received response with predefined API structure, due to this we can verify
	 * different things such as field presence, data types, required attributes, nested objects, and overall response structure, helping to detect early API structure changes. 
	 * 
	 * JsonSchemaValidation is class which consist matchesJsonSchemaInClasspath() method which loads a predefined JSON schema from the resources folder and returns a Hamcrest matcher. 
	 * Rest Assured uses this matcher to validate and compare with actual response to rectify any changes.
	 */
	RestAssured request = new RestAssured();
	@BeforeClass	
	public void setup() {
		request.baseURI = "https://jsonplaceholder.typicode.com";
	}
	@Test
	public void ValidateUserSchema() {
		request.given()
		.when()
		.get("/users/1")
		.then()
		.statusCode(200)
		.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("Schemas/user-schema.json"));
	}
	
	@Test
	public void ValidateSinglePostSchema() {
		request.given()
		.pathParam("id", 1)
		.when()
		.get("/posts/{id}")
		.then()
		.statusCode(200)
		.body("userId", equalTo(1))
		.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("Schemas/post-schema.json"));
	}
	
}
