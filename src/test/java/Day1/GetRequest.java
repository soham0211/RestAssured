package Day1;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static org.hamcrest.Matchers.*;

import org.testng.annotations.Test;

public class GetRequest {
	RestAssured request = new RestAssured();
@Test	
public void BasicGetRequest() {	
	


request.baseURI = "https://jsonplaceholder.typicode.com";

request.given().log().all().when().get("/posts").then().statusCode(200).contentType(ContentType.JSON).body("size()", greaterThan(0)) ;     


}

@Test
public void GetSinglePost() {
	request.baseURI = "https://jsonplaceholder.typicode.com";
	
	request.given().pathParam("id", 1).log().all().when().get("/posts/{id}").then().statusCode(200).body("id", equalTo(1)).body("userId", notNullValue());
}
@Test
public void getResponseTime() {
	
	request.baseURI = "https://jsonplaceholder.typicode.com";
	request.given().log().all().when().get("/posts/1").then().statusCode(200).time(lessThan(3000L));
}

@Test
public void ExtractAndValidateData() {
	
	request.baseURI = "https://jsonplaceholder.typicode.com";
	Response response =request.given().log().all().when().get("/posts/1").then().statusCode(200).extract().response();
	
	int userid = response.path("userId");
	String title = response.path("title");
	
	System.out.println(userid);
	System.out.println(title);
	
	assert userid > 0;
	//assert title != null && ! title.isEmpty();
	assert title.equals("Hello");
	
}
@Test
public void GetCommentsForThePost() {
	request.baseURI = "https://jsonplaceholder.typicode.com";
	Response response = request.given()
            .pathParam("postId", 1)
        .when()
            .get("/posts/{postId}/comments")
        .then()
            .statusCode(200)
            .body("size()", greaterThan(0))
            .body("[0].postId", equalTo(1))
            .body("[0].email", containsString("@")).extract().response();
	
	String firstEmail = response.path("[0].email");
	String SecondEmail = response.path("[1].email");
	
	System.out.println(firstEmail);
	System.out.println(SecondEmail);
         
           
}

@Test
public void ValidateData() {
	
	request.baseURI = "https://jsonplaceholder.typicode.com";
	request.given()
	.when()
	.get("/users")
	.then()
	.statusCode(200)
	.body("size()", equalTo(10))
	.body("id", notNullValue())
	.body("[0].address.zipcode", equalTo("92998-3874"))
	.body("username", everyItem(not(emptyString())));
	
}

@Test
public void ValidateFieldData() {
	request.baseURI="https://jsonplaceholder.typicode.com";
	request.given()
	.when()
	.get("/users/1")
	.then()
	.statusCode(200)
	.log().all()
	.body("id", equalTo(1))
	.body("address.street", equalToIgnoringCase("Kulas Light"));
	
}
@Test
public void ErrorCode() {
	
	request.baseURI="https://jsonplaceholder.typicode.com";
	request.given()
	.when()
	.get("/users/11")
	.then()
	.statusCode(404)
	.log().all();
}





}
