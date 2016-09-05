package de.jonashackt.weatherbackend;

import static com.jayway.restassured.RestAssured.*;
import static org.junit.Assert.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.ObjectMapper;
import com.mashape.unirest.http.Unirest;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jayway.restassured.http.ContentType;

import de.codecentric.soap.businesslogic.IncredibleLogic;
import de.codecentric.soap.internalmodel.GeneralOutlook;
import de.codecentric.soap.internalmodel.Product;
import de.codecentric.soap.internalmodel.Weather;

import java.io.IOException;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = WeatherBackendApplication.class)
@WebIntegrationTest
public class WeatherBackendApplicationTests {
  
	@Test
    public void testWithRestAssured() {
	    
	    Weather weather = new Weather();
	    weather.setFlagColor("blue");
	    weather.setPostalCode("99425");
	    weather.setProduct(Product.ForecastBasic);
	    
	    
	    
	    given() // can be ommited when GET only
	        .contentType(ContentType.JSON)
            .body(weather)
        .when() // can be ommited when GET only
            .post("http://localhost:8080/weatherbackend/general/outlook")
        .then()
            .statusCode(HttpStatus.SC_OK)
            .contentType(ContentType.JSON)
            .assertThat()
                .equals(IncredibleLogic.generateGeneralOutlook());
	    
	    GeneralOutlook outlook = given() // can be ommited when GET only
	            .contentType(ContentType.JSON)
	            .body(weather).post("http://localhost:8080/weatherbackend/general/outlook").as(GeneralOutlook.class);
	    
	    assertEquals("Weimar", outlook.getCity());
    }

	@Test
	public void testWithUniRest() throws Exception {

        Unirest.setObjectMapper(new ObjectMapper() {
            private com.fasterxml.jackson.databind.ObjectMapper jacksonObjectMapper
                    = new com.fasterxml.jackson.databind.ObjectMapper();

            public <T> T readValue(String value, Class<T> valueType) {
                try {
                    jacksonObjectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                    return jacksonObjectMapper.readValue(value, valueType);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            public String writeValue(Object value) {
                try {
                    jacksonObjectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                    return jacksonObjectMapper.writeValueAsString(value);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        Weather weather = new Weather();
        weather.setFlagColor("blue");
        weather.setPostalCode("99425");
        weather.setProduct(Product.ForecastBasic);

        HttpResponse<GeneralOutlook> generalOutlookHttpResponse = Unirest.post("http://localhost:8080/weatherbackend/general/outlook")
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .body(weather)
                .asObject(GeneralOutlook.class);

        assertEquals("Weimar", generalOutlookHttpResponse.getBody().getCity());
	}
}
