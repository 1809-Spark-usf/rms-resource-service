package com.revature.tests.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.controllers.ResourceController;
import com.revature.models.Building;
import com.revature.models.Campus;
import com.revature.models.Resource;
import com.revature.models.ResourceObject;
import com.revature.services.CampusService;
import com.revature.services.ResourceService;

/**
 * Tests for all methods in the ResourceController
 * 
 * @author Kyne Liu
 *
 */
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

	/*----------------Testing for saveResource method in ResourceController------------------*/

	@Test
	public void successfulSaveResource() throws JsonProcessingException, Exception {
		// setup all objects needed for the test
		int targetBuildingId = 10;
		ResourceObject resource = new ResourceObject();
		resource.setBuildingId(targetBuildingId);
		Building building = new Building();
		building.setId(targetBuildingId);

		// setup return object
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
								.accept(MediaType.APPLICATION_JSON)).andDo(print())
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

	@Test
	public void serverErrorForSaveResource() throws JsonProcessingException, Exception {
		//setup all objects needed for the test
		int targetBuildingId = 10;
		ResourceObject resource = new ResourceObject();
		resource.setBuildingId(targetBuildingId);
		Building building = new Building();
		building.setId(targetBuildingId);
		
		//setup return object
		int newResourceId = 3;
		ResourceObject tempObject = new ResourceObject();
		tempObject.setId(newResourceId);
		tempObject.setBuilding(building);
		
		Mockito.when(mockCampus.getBuilding(anyInt())).thenReturn(building);
		Mockito.when(mockResource.save(any(Resource.class))).thenThrow(new DataIntegrityViolationException(null));
		
		mockMvc.perform(post("").accept(MediaType.APPLICATION_JSON)
								.contentType(MediaType.APPLICATION_JSON)
								.content(om.writeValueAsString(resource)))
				.andDo(print())
				.andExpect(status().is5xxServerError());
	}

	/*----------------Testing for getBuildings method in ResourceController------------------*/

	@Test
	public void succesfulGetBuildings() throws JsonProcessingException, Exception {
		List<Campus> results = new ArrayList<>();
		results.add(new Campus());

		Mockito.when(mockCampus.getCampuses()).thenReturn(results);

		mockMvc.perform(get("/campuses").accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isOk())
				.andExpect(content().bytes(om.writeValueAsBytes(results)));
	}

	// Test if connection drops or something thing goes wrong during the accessing of the data
	@Test
	public void serverErrorForGetBuildings() throws JsonProcessingException, Exception {
		Mockito.when(mockCampus.getCampuses()).thenThrow(new DataIntegrityViolationException(null));
		
		mockMvc.perform(get("/campuses").accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().is5xxServerError());
		
	}

	/*----------------Testing for getBuildingById method in ResourceController------------------*/

	@Test
	public void successfulGetBuildingById() throws JsonProcessingException, Exception {
		int targetId = 10;
		List<Resource> result = new ArrayList<>();
		Resource targetResource = new Resource();
		targetResource.setId(targetId);
		result.add(targetResource);

		Mockito.when(mockResource.getResourceByBuildingId(targetId)).thenReturn(result);

		mockMvc.perform(get("/building/" + targetId).accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(content().bytes(om.writeValueAsBytes(result)));
	}

	// Test if the resource id is not found in the database
	@Test
	public void badIdGetBuildingById() throws JsonProcessingException, Exception {
		int badId = 3;

		Mockito.when(mockResource.getResourceByBuildingId(badId)).thenThrow(new EntityNotFoundException());

		mockMvc.perform(get("/building/" + badId).accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isNotFound());
	}

	@Test
	public void serverErrorForGetBuildingById() throws JsonProcessingException, Exception {
		int targetId = 10;
		
		Mockito.when(mockResource.getResourceByBuildingId(targetId)).thenThrow(new DataIntegrityViolationException(null));
		
		mockMvc.perform(get("/building/" + targetId).accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().is5xxServerError());
	}
	/*----------------Testing for getByCampus method in ResourceController------------------*/

	@Test
	public void successfulGetByCampus() throws JsonProcessingException, Exception {
		int targetCampusId = 10;
		Campus targetCampus = new Campus();
		targetCampus.setId(targetCampusId);
		Resource resultResouce = new Resource();

		List<Resource> result = new ArrayList<>();
		result.add(resultResouce);

		Mockito.when(mockCampus.getCampus(targetCampusId)).thenReturn(targetCampus);
		Mockito.when(mockResource.getResourcesByCampus(targetCampus)).thenReturn(result);

		mockMvc.perform(get("/campus/" + targetCampusId).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(content().bytes(om.writeValueAsBytes(result)));
	}

	@Test
	public void campusNotFoundGetByCampus() throws JsonProcessingException, Exception {
		int badId = 3;
		List<Resource> emptyResult = new ArrayList<>();

		Mockito.when(mockCampus.getCampus(badId)).thenThrow(new EntityNotFoundException());
		Mockito.when(mockResource.getResourcesByCampus(null)).thenReturn(emptyResult);

		mockMvc.perform(get("/campus/" + badId).accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isNotFound());
	}

	@Test
	public void serverErrorForGetByCampus() throws JsonProcessingException, Exception {
		int targetId = 10;
		
		Mockito.when(mockCampus.getCampus(targetId)).thenThrow(new DataIntegrityViolationException(null));
		
		mockMvc.perform(get("/campus/" + targetId).accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().is5xxServerError());
	}

	/*----------------Testing for updateResource method in ResourceController------------------*/

	@Test
	public void successfulUpdateResource() throws JsonProcessingException, Exception {
		int targetId = 10;
		ResourceObject updateObject = new ResourceObject();
		Resource result = new Resource(updateObject);
		result.setId(targetId);

		Mockito.doNothing().when(mockResource).updateResource(result, targetId);

		mockMvc.perform(put("/" + targetId).accept(MediaType.APPLICATION_JSON)
											.contentType(MediaType.APPLICATION_JSON)
											.content(om.writeValueAsString(updateObject)))
				.andDo(print())
				.andExpect(status().isOk());
	}

	// The resource to be updated could not be found
	@Test
	public void resourceNotFoundForUpdateResource() throws JsonProcessingException, Exception {
		int badId = 3;

		Mockito.doThrow(new EntityNotFoundException()).when(mockResource).updateResource(any(Resource.class), anyInt());

		mockMvc.perform(put("/" + badId).accept(MediaType.APPLICATION_JSON)
										.contentType(MediaType.APPLICATION_JSON)
										.content(om.writeValueAsString(new ResourceObject())))
				.andDo(print())
				.andExpect(status().isNotFound());
	}

	// The resource is found but the update fails for some reason
	@Test
	public void unsuccessfulUpdateResource() throws JsonProcessingException, Exception {
		int targetId = 10;
		ResourceObject input = new ResourceObject();
		input.setId(targetId);

		Mockito.doThrow(new DataIntegrityViolationException(null)).when(mockResource)
				.updateResource(any(Resource.class), anyInt());

		mockMvc.perform(put("/" + targetId).accept(MediaType.APPLICATION_JSON)
										   .contentType(MediaType.APPLICATION_JSON)
										   .content(om.writeValueAsString(input)))
				.andDo(print())
				.andExpect(status().is5xxServerError());
	}

	/*----------------Testing for findResources method in ResourceController------------------*/

	@Test
	public void successfulFindResources() throws JsonProcessingException, Exception {
		List<Resource> result = new ArrayList<>();
		result.add(new Resource());

		Mockito.when(mockResource.getAllResources()).thenReturn(result);

		mockMvc.perform(get("").accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(content().bytes(om.writeValueAsBytes(result)));
	}

	@Test
	public void serverErrorForFindResources() throws JsonProcessingException, Exception {
		Mockito.when(mockResource.getAllResources()).thenThrow(new DataIntegrityViolationException(null));
		
		mockMvc.perform(get("").accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().is5xxServerError());
		
	}
	/*----------------Testing for getResources method in ResourceController------------------*/

	@Test
	public void successfulGetResources() throws JsonProcessingException, Exception {
		String arrayOfValidIds = "10, 20, 30";
		List<Resource> result = new ArrayList<>();
		Resource first = new Resource();
		first.setId(10);
		result.add(first);
		Resource second = new Resource();
		second.setId(20);
		result.add(second);
		Resource third = new Resource();
		third.setId(30);
		result.add(third);

		Mockito.when(mockResource.getResourcesById(anyList())).thenReturn(result);

		mockMvc.perform(get("/" + arrayOfValidIds).accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(content().bytes(om.writeValueAsBytes(result)));
	}

	@Test
	public void serverErrorFoundGetResources() throws JsonProcessingException, Exception {
		String ids = "10, 20, 30";

		Mockito.when(mockResource.getResourcesById(anyList())).thenThrow(new DataIntegrityViolationException(null));

		mockMvc.perform(get("/" + ids).accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().is5xxServerError());
	}
}
