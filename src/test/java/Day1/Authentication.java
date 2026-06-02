package Day1;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static org.hamcrest.Matchers.*;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Authentication {

	RestAssured request = new RestAssured();
	@BeforeClass
	public void setup() {
		request.baseURI= "https://httpbin.org";
	
	}
	// Successful Request
	@Test
	public void BasicAuth1() {
		request.given()
		.auth().basic("user", "passwd").log().all()
		.when()
		.get("/basic-auth/user/passwd")
		.then()
		.statusCode(200)
		.body("authenticated", equalTo(true))
		.body("user", notNullValue());
		
	}
	
	// Failure Request
		@Test
		public void BasicAuth2() {
			request.given()
			.auth().basic("user", "wrongpasswd").log().all()
			.when()
			.get("/basic-auth/user/passwd")
			.then()
			.statusCode(401);
		}
		
		//This is Bearer Authentication
		/*Here we are creating our own token but in real applications we receive 
		  token from Login API response or different responses
		*/
		@Test
		public void BearerAuth() {
			String token = "test-bearer-token-12345";
			request.given()
			.header("Authorization", "Bearer " + token)
			.log().headers()
			.when()
			.get("/bearer")
			.then()
			.log().all()
			.statusCode(200);
			
		}
}
