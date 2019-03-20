package com.revature.tests.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.persistence.EntityNotFoundException;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.controllers.ResourceController;
import com.revature.models.Building;
import com.revature.models.Resource;
import com.revature.models.ResourceObject;
import com.revature.services.CampusService;
import com.revature.services.ResourceService;

public class ResourceControllerTest {
	
	private MockMvc mockMvc;
	
	private ObjectMapper om;
	
	@Mock
	private CampusService mockCampus;
	
	@Mock
	private ResourceService mockResource;
	
	@InjectMocks
	private ResourceController controller;
	
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
		om = new ObjectMapper();
	}
	
	@Test
	public void successfulSaveResource() throws JsonProcessingException, Exception {
		//setup all objects needed for the test
		int targetBuildingId = 10;
		ResourceObject resource = new ResourceObject();
		resource.setBuildingId(targetBuildingId);
		Building building = new Building();
		building.setId(targetBuildingId);
		
		System.out.println(resource);
		
		//setup return object
		int newResourceId = 3;
		ResourceObject tempObject = new ResourceObject();
		tempObject.setId(newResourceId);
		tempObject.setBuilding(building);
		Resource returnedObject = new Resource(tempObject);
		
		byte[] result = om.writeValueAsBytes(returnedObject);
		
		Mockito.when(mockCampus.getBuilding(targetBuildingId)).thenReturn(building);
		Mockito.when(mockResource.save(any(Resource.class))).thenReturn(returnedObject);
		
		mockMvc.perform(post("").contentType(MediaType.APPLICATION_JSON)
								.content(om.writeValueAsString(resource))
								.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isCreated())
				.andExpect(content().bytes(result));
	}
	
	@Test
	public void nullParameterForSaveResource() throws Exception {
		
		mockMvc.perform(post("").contentType(MediaType.APPLICATION_JSON)
								.content("{}")
								.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isBadRequest());
	}
	
	@Test
	public void noBuildingFoundWithIdForSaveResource() throws JsonProcessingException, Exception {
		int badBuildingId = 3;
		ResourceObject badObject = new ResourceObject();
		badObject.setBuildingId(badBuildingId);
		
		Mockito.when(mockCampus.getBuilding(anyInt())).thenThrow(new EntityNotFoundException());
		
		mockMvc.perform(post("").contentType(MediaType.APPLICATION_JSON)
								.content(om.writeValueAsString(badObject))
								.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isNotFound());
		
	}
	
	

}
