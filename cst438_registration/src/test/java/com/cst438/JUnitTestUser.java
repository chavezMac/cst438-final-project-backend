package com.cst438;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

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

import com.cst438.domain.User;
import com.cst438.domain.UserDTO;
import com.cst438.service.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class JUnitTestUser {
	@Autowired
	private MockMvc mvc;
	
	@Autowired
    private JwtService jwtService;
	
	private UserDTO createUserDTO(User u) {
		UserDTO dto = new UserDTO(
				u.getUser_id(),
				u.getAlias(),
				u.getPassword(),
				u.getRole());
		return dto;
		
	}
	
	@Order(1)
	@Test
	public void addUser() throws Exception {

		MockHttpServletResponse response;

		User testUser = new User();
		testUser.setAlias("mac");
		testUser.setPassword("mac");
		testUser.setRole("user");
		
		UserDTO userDTO = createUserDTO(testUser);
		
		String jwtToken = jwtService.getToken("username");
	
		String jsonString = asJsonString(userDTO);

		response = mvc.perform(
			MockMvcRequestBuilders
			.post("/users")
			.header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken)
			.contentType(MediaType.APPLICATION_JSON)
			.content(jsonString)
			.accept(MediaType.APPLICATION_JSON))
			.andReturn().getResponse();
		
		assertEquals(200, response.getStatus());
	}
	
	@Order(2)
	@Test
	public void deleteUser() throws Exception {
		MockHttpServletResponse response;

		User testUser = new User();
		testUser.setAlias("mac");
		
		UserDTO userDTO = createUserDTO(testUser);
		
		String jwtToken = jwtService.getToken("username");
		
		String jsonString = asJsonString(userDTO);

		response = mvc.perform(
			MockMvcRequestBuilders
			.delete("/users/delete")
			.header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken)
			.contentType(MediaType.APPLICATION_JSON)
			.content(jsonString)
			.accept(MediaType.APPLICATION_JSON))
			.andReturn().getResponse();
		
		assertEquals(200, response.getStatus());
		
	}
	
	@Order(3)
	@Test
	public void updateUser() throws Exception {
		MockHttpServletResponse response;
		
		User testUser = new User();
		testUser.setAlias("mac");
		testUser.setPassword("notmac");
		testUser.setRole("admin");
		
		UserDTO userDTO = createUserDTO(testUser);
		
		String jwtToken = jwtService.getToken("username");
		
		String jsonString = asJsonString(userDTO);
		
		response = mvc.perform(
				MockMvcRequestBuilders
				.put("/users/update")
				.header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonString)
				.accept(MediaType.APPLICATION_JSON))
				.andReturn().getResponse();
				
		assertEquals(200, response.getStatus());
		
		response = mvc.perform(
				MockMvcRequestBuilders
				.get("/user/mac")
				.header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken)
				.accept(MediaType.APPLICATION_JSON))
				.andReturn().getResponse();
		
		UserDTO dto = fromJsonString(response.getContentAsString(), UserDTO.class);
		boolean found = false;
		
		if(dto.role().equals("admin")) {
			found = true;
		}
		
		assertTrue(found);
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
