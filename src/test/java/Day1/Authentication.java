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
		/* Authentication using API key
		 * The API Key is an unique value provided by API Provider
		 * Server identify value and authorize the client to make request
		 * 1. With Header
		 * 2. With query param
		 */
		@Test
		public void APIKey() {
			request.given()
			.header("X-API-Key", "my-secret-api-key")
			.when()
			.get("/headers")
			.then()
			.statusCode(200)
			.body("headers.Host", equalTo("httpbin.org"));
		}
		@Test
		public void APIKeyWithQueryParam() {
			request.given()
			.queryParam("api-key", "12345-abcde-67890")
			.when()
			.get("/get")
			.then()
			.statusCode(200)
			.body("args.api-key", equalTo("12345-abcde-67890"));
		}
		
	/* Query Parameter is a data that we sent in URL after ?    Like above eg https://httpbin.org/get?api_key=12345-abcde-67890
	 * Response received from the server will reflect in args
	 * Query Parameter is mainly used for Filtering and Searching 
	 * 
	 * As Header mainly use for Authentication and Authorization as it sent separately or apart from URL
	 * For Authentication and Authorization sometimes we can go for query parameter but its unsafe as it appears in browser history so instead use Bearer token
	 * 
	 * Query Parameter Vs Path Parameter - Both are sent in / within URL
	 * Query Parameter - Optional Information
	 * Path Parameter - Its a part of resource path
	 * eg - 
	 * given()
        .pathParam("id", 101)
        .when()
        .get("/users/{id}");
        
        URL - /users/101
	 */
		
		
	/* Digest authentication
	 * Digest Authentication is an authentication method that improves on Basic Authentication by not sending the password directly over the network.
	 * Basic Authentication uses Base64 which only encode but not encrypt
	 * 
	 * In this client internally create hash response after server challenge the first request when client again send request with created hash server is then
	 * performs the same hash calculation if match then we get 200 OK 
	 * 
	 * Commonly used in legacy system	
	 */
	@Test
	public void DigestAuthentication() {
		request.given()
		.auth().digest("user", "passwd")
		.when()
		.get("/digest-auth/auth/user/passwd")
		.then()
		.statusCode(200)
		.body("authenticated", equalTo(true))
		.body("user", equalTo("user"));
	}
	
	// Here we are checking for Authentification fail scenario by sending Bad request
	
	@Test
	public void NoAuthentication() {
		request.given()
		.when()
		.get("/basic-auth/user/passwd")
		.then()
		.statusCode(401);
	}
	
	/*Here we are using Post request by sending or using request body
	 * Positive scenario to check status code 201 and verifying required response in attribute id
	 */
	@Test
	public void ReqResRegister() {
		
		request.baseURI="https://jsonplaceholder.typicode.com";
		
		String requestBody = "{\n" +
	            "  \"userId\": \"1\",\n" +
	            "  \"title\": \"Hello Soham\", \n" +
	            "  \"body\": \"Hello Soham\"\n" +
	            "}";
		request.given()
		.contentType("application/json")
		.body(requestBody)
		.log().all()
		.when()
		.post("/posts")
		.then()
		.log().all()
		.statusCode(201)
		.body("id", equalTo(101))
		.body("title", notNullValue())
		.body("title", equalTo("Hello Soham"));
	
	}
		
	/*Here we are using Post request by sending or using request body
	 * Negative scenario to check status code 500
	 */
	@Test
	public void ReqResRegisterFail() {
		
		request.baseURI="https://jsonplaceholder.typicode.com";
		
		String requestBody = "{\n" +
	            "  \"userId\": \"1\",\n" +
	            
	            "}";
		request.given()
		.contentType("application/json")
		.body(requestBody)
		.log().all()
		.when()
		.post("/posts")
		.then()
		.log().all()
		.statusCode(500);
		
		
	
	}
		
		
		
		
		
		
		
		
}
