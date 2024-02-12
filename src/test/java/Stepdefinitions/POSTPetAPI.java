package Stepdefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONObject;
import utility.commonFunctions;

import java.io.IOException;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

public class POSTPetAPI {
    public RequestSpecification httpRequest;
    public Response response;
    public int ResponseCode;
    public JSONObject requestParams;
    public String jsonPath;

    @Given("I have new pet to add to the store")
    public void i_have_new_pet_to_add_to_the_store() throws IOException {
        RestAssured.baseURI = commonFunctions.getConfigPropertyAsString("baseUrl").toString();
        httpRequest = given();
        requestParams = new JSONObject();
    }

    @When("I pass the url of add pet in the request")
    public void i_pass_the_url_of_add_pet_in_the_request() {
        httpRequest = RestAssured.given();
        response = httpRequest.post("/v2/pet");
    }

    @Then("I receive the response code as {int}")
    public void i_receive_the_response_code_as(Integer id) {
        ResponseCode = response.getStatusCode();
        assertSame(ResponseCode,id);
    }

    @And("I pass the request body of add new pet {int} {} {} {} {} {}")
    public void i_pass_the_request_body_of_add_new_pet(Integer id, String name, String categoryName, String photoUrls, String tagName, String status) {
        requestParams.put("id", id);
        requestParams.put("category.id", id);
        requestParams.put("category.name",categoryName);
        requestParams.put("name",name);
        requestParams.put("photoUrls",photoUrls);
        requestParams.put("tags.[0].id",id);
        requestParams.put("tags.[0].name",tagName);
        requestParams.put("status",status);
        httpRequest.body(requestParams.toJSONString());
        Response response =httpRequest.post("/v2/pet");
        JsonPath path = response.jsonPath();
        jsonPath = path.getJsonObject("id").toString();
    }

    @And("I pass invalid request body{} {} {} {} {} {}")
    public void i_pass_invalid_request_body(String id, String name, String categoryName, String photoUrls, String tagName, String status) {
        requestParams.put("id", id);
        requestParams.put("category.id", id);
        requestParams.put("category.name",categoryName);
        requestParams.put("name",name);
        requestParams.put("photoUrls",photoUrls);
        requestParams.put("tags.[0].id",id);
        requestParams.put("tags.[0].name",tagName);
        requestParams.put("status",status);
        httpRequest.body(requestParams.toJSONString());
        Response response =httpRequest.post("/v2/pet");
        ResponseCode = response.getStatusCode();
        assertEquals(ResponseCode, 400);
    }

    @Then("I receive the response body with id as {}")
    public void i_receive_the_responseBody_with_id_As(String id) {
        assertEquals(id, jsonPath);
    }

}
