package com.revature.tests.service;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;

import com.revature.models.Building;
import com.revature.models.Resource;
import com.revature.repository.BuildingRepository;
import com.revature.repository.ResourceRepository;
import com.revature.services.ResourceService;

/**
 * Tests for all functions in the ResourceService
 * 
 * @author Kyne Liu
 *
 */
@RunWith(SpringRunner.class)
public class ResourceServiceTest {

	@Mock
	ResourceRepository mockResourceRepo;
	
	@Mock
	BuildingRepository mockBuildingRepo;
	
	@InjectMocks
	ResourceService testService;
	
	/*-----------------------Tests for findResources(Resource) method ------------------------------*/
	
	@Test
	public void successfulFindResources() {
		int targetBuildingId = 10;
		Building building = new Building();
		building.setId(targetBuildingId);
		Resource input = new Resource();
		input.setBuildingId(targetBuildingId);
		
		int targetResourceId = 11;
		List<Resource> returned = new ArrayList<>();
		Resource firstResult = new Resource();
		firstResult.setBuildingId(targetBuildingId);
		firstResult.setId(targetResourceId);
		returned.add(firstResult);
		
		Mockito.when(mockBuildingRepo.getOne(targetBuildingId)).thenReturn(building);
		Mockito.when(mockResourceRepo.findAll(Example.of(input, ExampleMatcher.matchingAll().withIgnoreNullValues())))
				.thenReturn(returned);
		
		List<Resource> result = testService.findResources(input);
		
		assertNotNull("The returned result should never be null.", result);
		assertThat("The returned list should be same as what the repository returend", result, is(returned));
	}
	
	@Test
	public void buildingNotFoundForFindResources() {
		int badBuildingId = 3;
		Resource input = new Resource();
		input.setBuildingId(badBuildingId);
		
		Mockito.when(mockBuildingRepo.getOne(badBuildingId)).thenThrow(new EntityNotFoundException());
		
		List<Resource> result = null;
		EntityNotFoundException returnedException = null;
		
		try {
			result = testService.findResources(input);
			fail("Method should throw an exception.");
		} catch (EntityNotFoundException e) {
			returnedException = e;
		}
		
		assertNull("The repository should throw an exception so result should be null.", result);
		assertNotNull("Method should throw EntityNotFoudException.", returnedException);
	}
	
	@Test
	public void noResourcesFoundForFindResouces() {
		int targetBuildingId = 10;
		Building building = new Building();
		building.setId(targetBuildingId);
		Resource input = new Resource();
		input.setBuildingId(targetBuildingId);
		List<Resource> returned = new ArrayList<>();
		
		Mockito.when(mockBuildingRepo.getOne(targetBuildingId)).thenReturn(building);
		Mockito.when(mockResourceRepo.findAll(Example.of(input, ExampleMatcher.matchingAll().withIgnoreNullValues())))
				.thenReturn(returned);
		
		List<Resource> result = testService.findResources(input);
		
		assertNotNull("Method should never return null value.", result);
		assertThat("Method should return an empty list because the repository returened an empty list", result, is(returned));
	}
	
	//Somthing goes wrong during the access of the building repo
	@Test
	public void buildingRepoErrorForFindResources() {
		int targetBuildingId = 10;
		Resource input = new Resource();
		input.setBuildingId(targetBuildingId);
		
		Mockito.when(mockBuildingRepo.getOne(targetBuildingId)).thenThrow(new DataAccessResourceFailureException(null));
		
		List<Resource> result = null;
		DataAccessException returnedException = null;
		
		try {
			result = testService.findResources(input);
			fail("Method should throw and excpetion");
		} catch(DataAccessException e) {
			returnedException = e;
		}
		
		assertNull("Method should throw an exception so result should be null.", result);
		assertNotNull("Method should throw a DataAccessException.", returnedException);
	}
	
	//Something goes wrong during the access of the resource repo
	@Test
	public void resourceRepoErrorForFindResources() {
		int targetBuildingId = 10;
		Building building = new Building();
		building.setId(targetBuildingId);
		Resource input = new Resource();
		input.setBuildingId(targetBuildingId);
		
		Mockito.when(mockBuildingRepo.getOne(targetBuildingId)).thenReturn(building);
		Mockito.when(mockResourceRepo.findAll(Example.of(input, ExampleMatcher.matchingAll().withIgnoreNullValues())))
				.thenThrow(new DataAccessResourceFailureException(null));
		
		List<Resource> result = null;
		DataAccessException returnedException = null;
		
		try {
			result = testService.findResources(input);
			fail("Method should throw an exception.");
		} catch (DataAccessException e) {
			returnedException = e;
		}
		
		assertNull("Method should throw an exception so result should be null.", result);
		assertNotNull("Method should throw an exception.", returnedException);
	}
	
	/*-----------------------Tests for getResourceById(int) method ------------------------------*/
	
	@Test
	public void successfulGetResourceById() {
		int targetId = 10;
		Resource returned = new Resource();
		returned.setId(targetId);
		
		Mockito.when(mockResourceRepo.findById(targetId)).thenReturn(Optional.ofNullable(returned));
		
		Resource result = testService.getResourceById(targetId);
		
		assertNotNull("Method should never return null.", result);
		assertThat("Method should return the Resource held withing the Optional returned by the repository", result, is(returned));
	}
	
	@Test
	public void resourceNotFoundForGetResourceById() {
		int badId = 3;
		
		Mockito.when(mockResourceRepo.findById(badId)).thenReturn(Optional.empty());
		
		Resource result = null;
		HttpClientErrorException returnedException = null;
		try {
			result = testService.getResourceById(badId);
			fail("Method should throw exception");
		} catch (HttpClientErrorException e) {
			returnedException = e;
		}
		
		assertNull("Method should not return anything so result should be null.", result);
		assertThat("Method should throw an HttpClientErrorException", returnedException.getStatusCode(), is(HttpStatus.NOT_FOUND));
		
	}
	
	@Test
	public void resourceRepoErrorFOrGetResourceById() {
		int targetId = 10;
		
		Mockito.when(mockResourceRepo.findById(targetId)).thenThrow(new DataAccessResourceFailureException(null));
		
		Resource result = null;
		DataAccessException returnedException = null;
		
		try {
			result = testService.getResourceById(targetId);
			fail("Method should throw an exception");
		} catch (DataAccessException e) {
			returnedException = e;
		}
		
		assertNull("Method should not return anything.", result);
		assertNotNull("Method should throw an exception.", returnedException);
	}
	
	/*-----------------------Tests for getResourcesById(Iterable<Integer>) method ------------------------------*/
	
	@Test
	public void successfulGetResourcesById() {
		List<Integer> input = new ArrayList<>();
		input.add(10);
		input.add(20);
		
		List<Resource> returned = new ArrayList<>();
		Resource first = new Resource();
		first.setId(10);
		returned.add(first);
		Resource second = new Resource();
		second.setId(20);
		returned.add(second);
		
		Mockito.when(mockResourceRepo.findAllById(input)).thenReturn(returned);
		
		List<Resource> result = testService.getResourcesById(input);
		
		assertNotNull("Method should never return a null value.", result);
		assertThat("Method should return what the repository returned.", result, is(returned));
	}
	
	@Test
	public void emptyListReturnForGetResourcesById() {
		List<Integer> input = new ArrayList<>();
		input.add(10);
		input.add(20);
		
		List<Resource> returned = new ArrayList<>();
		
		Mockito.when(mockResourceRepo.findAllById(input)).thenReturn(returned);
		
		List<Resource> result = testService.getResourcesById(input);
		
		assertNotNull("Method should never return a null value.", result);
		assertThat("Method should return an empty list.", result, is(returned));
	}
	
	@Test
	public void resourceRepoErrorForGetResourcesById() {
		List<Integer> input = new ArrayList<>();
		input.add(10);
		input.add(20);
		
		Mockito.when(mockResourceRepo.findAllById(input)).thenThrow(new DataAccessResourceFailureException(null));
		
		List<Resource> result = null;
		DataAccessException returnedException = null;
		try {
			result = testService.getResourcesById(input);
			fail("Method should throw an exception.");
		} catch (DataAccessException e) {
			returnedException = e;
		}
		
		assertNull("Method should never get to return statement.", result);
		assertNotNull("Method should throw an exception.", returnedException);
	}
	
	/*-----------------------Tests for save(Resource) method ------------------------------*/
	
	@Test
	public void successfulSave() {
		Resource input = new Resource();
		int newId = 10;
		Resource returned = new Resource();
		returned.setId(newId);
		
		Mockito.when(mockResourceRepo.save(input)).thenReturn(returned);
		
		Resource result = testService.save(input);
		
		assertNotNull("Method should never return null.", result);
		assertThat("Method should return the saved object.", result, is(returned));
	}
}
