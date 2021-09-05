package com.mercadolibre.funtional;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.empty;

import java.util.List;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.google.gson.Gson;
import com.mercadolibre.model.DnaSequence;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
@QuarkusTestResource(DynamodbResource.class)
@TestMethodOrder(OrderAnnotation.class)
public class mercadolibreTest {

	@Test
	@Order(1) 
	public void testMutantEndpoint() {
		DnaSequence testOK = new DnaSequence();
		testOK.setDna(List.of("ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"));
		String json = new Gson().toJson(testOK );
		given()
			.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
			.body(json)
			.when()
			.post("/mutantsValidator/mutant")
			.then().statusCode(Status.OK.getStatusCode())
			.body("mutant", equalTo(true));
	}
	
	@Test
	@Order(2) 
	public void testHumanEndpoint() {
		DnaSequence testForbidden = new DnaSequence();
		testForbidden.setDna(List.of("ATGCTA","CAGTGC","TTATGT","AGAAGG","GCCCTA","TCACTG"));
		String json = new Gson().toJson(testForbidden);
		given()
			.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
			.body(json)
			.when()
			.post("/mutantsValidator/mutant")
			.then().statusCode(Status.FORBIDDEN.getStatusCode())
			.body("mutant", equalTo(false));
	}

	@Test
	@Order(3) 
	public void testStatsEndpoint() {
		given().when().get("/mutantsValidator/stats").then().statusCode(Status.OK.getStatusCode())
		.body("count_mutant_dna", not(empty()));
	}

}