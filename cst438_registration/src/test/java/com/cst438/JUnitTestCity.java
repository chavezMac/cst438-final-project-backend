package com.cst438;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.cst438.domain.City;
import com.cst438.domain.CityDTO;
import com.cst438.service.JwtService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class JUnitTestCity {

    @Autowired
    private MockMvc mvc;
    
    @Autowired
    private JwtService jwtService;
    
    private CityDTO createCityDTO(City c) {
    	CityDTO dto = new CityDTO(
				c.getCity_id(),
				c.getName(),
				c.getTemp(),
				c.getMax(),
				c.getMin(),
				c.getIcon());
		return dto;
    }
    
    @Order(1)
    @Test
    public void testAddCity() throws Exception {
    	MockHttpServletResponse response;
    	
    	City testCity = new City();
    	testCity.setName("Marina");
    	testCity.setTemp(65);
    	testCity.setMax(70);
    	testCity.setMin(60);
    	testCity.setIcon("10d");
    	
    	CityDTO cityDTO = createCityDTO(testCity);
    	
    	String jwtToken = jwtService.getToken("username");
    	
    	String jsonString = asJsonString(cityDTO);
    	
    	response = mvc.perform(
    			MockMvcRequestBuilders
    			.post("/city/1")
    			.param("name", "Marina")
    			.header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken)
    			.contentType(MediaType.APPLICATION_JSON)
    			.content(jsonString)
    			.accept(MediaType.APPLICATION_JSON))
    			.andReturn().getResponse();
    	
    	
    	System.out.println("Response status: " + response.getStatus());
    	System.out.println("Response body: " + response.getContentAsString());
    	
    	assertEquals(200, response.getStatus());
    	
    }
    
    @Order(3)
    @Test
    public void testDeleteCity() throws Exception {
        MockHttpServletResponse response;
        
        City testCity = new City();
        testCity.setName("Aptos");
        
        CityDTO cityDTO = createCityDTO(testCity);
        
        String jwtToken = jwtService.getToken("username");
        
        String jsonString = asJsonString(cityDTO);
        
        response = mvc.perform(
        		MockMvcRequestBuilders
        		.delete("/city/1")
        		.param("name", "Aptos")
        		.header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken)
    			.contentType(MediaType.APPLICATION_JSON)
    			.content(jsonString)
    			.accept(MediaType.APPLICATION_JSON))
        		.andReturn().getResponse();
        
        assertEquals(200, response.getStatus());
        
        response = mvc.perform(
        		MockMvcRequestBuilders
        		.get("/city/1")
        		.header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken)
        		.accept(MediaType.APPLICATION_JSON))
				.andReturn().getResponse();
        
        
        boolean found = false;
        
        if(response.getErrorMessage() == null) {
        	found = true;
        }
        
        assertTrue(found);
    }
    
    @Order(2)
    @Test
    public void testUpdateCity() throws Exception {
    	MockHttpServletResponse response;
    	
    	City testCity = new City();
    	testCity.setName("Marina");
    	
    	CityDTO cityDTO = createCityDTO(testCity);
    	String jwtToken = jwtService.getToken("username");
    	
    	String jsonString = asJsonString(cityDTO);
    	
    	response = mvc.perform(
    			MockMvcRequestBuilders
    			.put("/city/update/1")
    			.param("name", "Aptos")
                .param("city_id", "1")  
    			.header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken)
    			.contentType(MediaType.APPLICATION_JSON)
    			.content(jsonString)
    			.accept(MediaType.APPLICATION_JSON))
    			.andReturn().getResponse();
    	
    	assertEquals(200, response.getStatus());
    	
    	response = mvc.perform(
    			MockMvcRequestBuilders
    			.get("/city/1")
    			.header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken)
        		.accept(MediaType.APPLICATION_JSON))
				.andReturn().getResponse();
    	
    	
    	boolean found = false;
    	
    	String responseBody = response.getContentAsString();
    	System.out.println(responseBody);
    	
    	ObjectMapper objectMapper = new ObjectMapper();
    	JsonNode jsonNode = objectMapper.readTree(responseBody);

    	String timezoneValue = jsonNode.get(0).get("timezone").asText();
    	
    	System.out.println("Timezone value: " + timezoneValue);

    	assertEquals("Aptos", timezoneValue);
    }

    
    private static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static <T> T  fromJsonString(String str, Class<T> valueType ) {
		try {
			return new ObjectMapper().readValue(str, valueType);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}