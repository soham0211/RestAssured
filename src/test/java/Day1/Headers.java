package Day1;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;


import static org.hamcrest.Matchers.*;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import io.restassured.module.jsv.JsonSchemaValidator;

/*
 * Here we are looking into headers and how we can pass single and multiple headers
 * Usually headers are not received in response bcoz it send internally or under the hood along through HTTPs
 * 
 * Here we are using website for testing which generally made to receive headers as endpoint in response.
 * https://httpbin.org/headers
 * 
 * We can pass content-type in header and we can extract header from response as well
 */
public class Headers {

	RestAssured request = new RestAssured();
	@BeforeClass
	public void setup() {
		
		request.baseURI= "https://httpbin.org";
		
	}
	
	//Single header in request
	@Test
	public void SendSingleHeader() {
		request.given()
		.header("Custom-Header", "CustomValue")
		.log().all()
		.when()
		.get("/headers")
		.then()
		.statusCode(200)
		.body("headers.Customer-Header", equalTo("CustomValue"));
		
	}
	
	//Multiple headers in request
	@Test
	public void SendMultipleHeaders() {
		
		request.given()
		.header("Name","Soham")
		.header("Id","01")
		.when()
		.get("/headers")
		.then()
		.statusCode(200)
		.body("headers.Name", equalTo("Soham"))
		.body("headers.Id", equalTo(1));
	}
	
	//Multiple headers in request but using list
	@Test
	public void testHeadersWithHashMap() {
        HashMap<String, String> headers = new HashMap<>();
        
        headers.put("Accept", "application/json");
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", "Bearer fake-token-12345");

        request.given()
            .headers(headers)
            .log().headers()
        .when()
            .get("/headers")
        .then()
            .statusCode(200)
            .body("headers.Authorization", equalTo("Bearer fake-token-12345"));
    }
	
	
}
