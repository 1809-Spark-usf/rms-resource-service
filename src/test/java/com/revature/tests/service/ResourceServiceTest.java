package com.revature.tests.service;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;

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
import com.revature.models.Campus;
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
	
	@Test
	public void resourceRepoErrorSave() {
		Resource input = new Resource();
		
		Mockito.when(mockResourceRepo.save(input)).thenThrow(new DataAccessResourceFailureException(null));
		
		Resource result = null;
		DataAccessException returnedException = null;
		
		try {
			result = testService.save(input);
			fail("Method should throw an excpetion");
		} catch(DataAccessException e) {
			returnedException = e;
		}
		
		assertNull("Method should never get the the return statement", result);
		assertNotNull("Method should throw a DataAccessException.", returnedException);
	}
	
	//TODO: add test for invalid or data that breaks the constraints of the database
	
	/*-----------------------Tests for updateResource(Resource, int) method ------------------------------*/
	
	@Test
	public void successfulUpdateResource() {
		Resource update = new Resource();
		int targetId = 10;
		Building building = new Building();
		building.setId(10);
		update.setBuilding(building);
		Resource returned = new Resource();
		returned.setId(targetId);
		
		Mockito.when(mockBuildingRepo.getOne(building.getId())).thenReturn(building);
		Mockito.when(mockResourceRepo.save(update)).thenReturn(returned);
		
		testService.updateResource(update, targetId);
		
		Mockito.verify(mockResourceRepo, times(1)).save(update);
	}
	
	@Test
	public void buildingNotFoundForUpdateResource() {
		Resource update = new Resource();
		int targetResourceId = 10;
		int badBuildingId = 3;
		update.setBuildingId(badBuildingId);
		Resource returned = new Resource();
		returned.setId(targetResourceId);
		
		Mockito.when(mockBuildingRepo.getOne(badBuildingId)).thenThrow(new EntityNotFoundException());
		Mockito.when(mockResourceRepo.save(update)).thenReturn(returned);
		
		EntityNotFoundException returnedException = null;
		
		try {
			testService.updateResource(update, targetResourceId);
			fail("Method should throw an exception.");
		} catch (EntityNotFoundException e) {
			returnedException = e;
		}
		
		assertNotNull("Method should throw an excpetion.", returnedException);
		Mockito.verify(mockResourceRepo, never()).save(update);
	}
	
	//Something goes wrong while trying to get the building for the building repo
	@Test
	public void buildingRepoErrorForUpdateResource() {
		Resource update = new Resource();
		int targetResourceId = 10;
		Resource returned = new Resource();
		returned.setId(targetResourceId);
		
		Mockito.when(mockBuildingRepo.getOne(anyInt())).thenThrow(new DataAccessResourceFailureException(null));
		Mockito.when(mockResourceRepo.save(update)).thenReturn(returned);
		
		DataAccessException returnedException = null;
		
		try {
			testService.updateResource(update, targetResourceId);
			fail("Method should throw an exception");
		} catch (DataAccessException e) {
			returnedException = e;
		}
		
		assertNotNull("Method should throw an excpeton.", returnedException);
		Mockito.verify(mockResourceRepo, never()).save(update);
	}
	
	//Something goes wrong while trying to update in the resource repo
	@Test
	public void resourceRepoErrorForUpdateResource() {
		Resource update = new Resource();
		int targetResourceId = 10;
		int targetBuildingId = 11;
		Building building = new Building();
		building.setId(targetBuildingId);
		Resource returned = new Resource();
		returned.setId(targetResourceId);
		
		Mockito.when(mockBuildingRepo.getOne(targetBuildingId)).thenReturn(building);
		Mockito.when(mockResourceRepo.save(update)).thenThrow(new DataAccessResourceFailureException(null));
		
		DataAccessException returnedException = null;
		
		try {
			testService.updateResource(update, targetResourceId);
			fail("Method should throw an exception");
		} catch (DataAccessException e) {
			returnedException = e;
		}
		
		assertNotNull("Method should throw an excpeton.", returnedException);
		Mockito.verify(mockResourceRepo, times(1)).save(update);
	}
	
	/*-----------------------Tests for getResourcesByBuildingId(int) method ------------------------------*/
	
	@Test
	public void successfulGetResourcesByBuildingId() {
		int targetBuildingId = 10;
		List<Resource> returned = new ArrayList<>();
		Resource first = new Resource();
		returned.add(first);
		
		Mockito.when(mockResourceRepo.findAllByBuilding_Id(targetBuildingId)).thenReturn(returned);
		
		List<Resource> result = testService.getResourceByBuildingId(targetBuildingId);
		
		assertNotNull("Method should never return a null value.", result);
		assertThat("Method should return what the repository returns.", result, is(returned));
	}
	
	@Test
	public void emptyListReturnForGetResourcesByBuildingId() {
		int targetBuilingId = 10;
		List<Resource> returned = new ArrayList<>();
		
		Mockito.when(mockResourceRepo.findAllByBuilding_Id(targetBuilingId)).thenReturn(returned);
		
		List<Resource> result = testService.getResourceByBuildingId(targetBuilingId);
		
		assertNotNull("Method should never return null.", result);
		assertThat("Method should return what the repo returns.", result, is(returned));
	}
	
	@Test
	public void resourceRepoErrorForGetResourcesByBuilingId() {
		int targetBuildingId = 10;
		
		Mockito.when(mockResourceRepo.findAllByBuilding_Id(targetBuildingId)).thenThrow(new DataAccessResourceFailureException(null));
		
		List<Resource> result = null;
		DataAccessException returnedException = null;
		
		try {
			result = testService.getResourceByBuildingId(targetBuildingId);
			fail("Method should throw an exception.");
		} catch (DataAccessException e) {
			returnedException = e;
		}
		
		assertNull("Method should not get the return call.", result);
		assertNotNull("Method should throw an exception", returnedException);
	}
	
	/*-----------------------Tests for getResourcesByCampus(Campus) method ------------------------------*/
	
	@Test
	public void successfulGetResourcesByCampus() {
		Campus input = new Campus();
		List<Building> buildings = new ArrayList<>();
		
		Building first = new Building();
		first.setId(10);
		buildings.add(first);
		
		Building second = new Building();
		first.setId(20);
		buildings.add(second);
		
		input.setBuildings(buildings);
		List<Resource> firstReturn = new ArrayList<>();
		Resource r1 = new Resource();
		r1.setId(1);
		firstReturn.add(r1);
		List<Resource> secondReturn = new ArrayList<>();
		Resource r2 = new Resource();
		r2.setId(2);
		secondReturn.add(r2);
		
		List<Resource> returned = new ArrayList<>();
		returned.addAll(secondReturn);
		returned.addAll(firstReturn);
		
		Mockito.when(mockResourceRepo.findAllByBuilding_Id(anyInt())).thenReturn(firstReturn);
		Mockito.when(mockResourceRepo.findAllByBuilding_Id(20)).thenReturn(secondReturn);
		
		List<Resource> result = testService.getResourcesByCampus(input);
		
		assertNotNull("Method should never return null.", result);
		Mockito.verify(mockResourceRepo, times(2)).findAllByBuilding_Id(anyInt());
		assertThat("Method should return the added lists of all return calls from the repo call", result, is(returned));
	}
	
	@Test
	public void emptyListForGetResourcesByCampus() {
		Campus input = new Campus();
		List<Building> buildings = new ArrayList<>();
		
		Building first = new Building();
		first.setId(10);
		buildings.add(first);
		
		Building second = new Building();
		first.setId(20);
		buildings.add(second);
		
		input.setBuildings(buildings);
		
		List<Resource> returned = new ArrayList<>();
		
		Mockito.when(mockResourceRepo.findAllByBuilding_Id(anyInt())).thenReturn(returned);
		
		List<Resource> result = testService.getResourcesByCampus(input);
		
		Mockito.verify(mockResourceRepo, times(2)).findAllByBuilding_Id(anyInt());
		assertNotNull("Method should never return a null value.", result);
		assertThat("Should return an empty list if no results were found.", result, is(returned));
	}
	
	//Campus provided for input is null
	@Test
	public void noCampusProvidedForGetResourcesByCampus() {
		Campus input = null;
		
		List<Resource> result = null;
		HttpClientErrorException returnedException = null;
		try {
			result = testService.getResourcesByCampus(input);
			fail("Should throw an exception.");
		} catch(HttpClientErrorException e) {
			returnedException = e;
		}
		
		assertNull("Method should not get to return statement.", result);
		assertThat("Should throw bad requst exception.", returnedException.getStatusCode(), is(HttpStatus.BAD_REQUEST));
	}
	
	//Something goes wrong while accessing the resource repo
	@Test
	public void resourceRepoErrorForGetResourcesByCampus() {
		Campus input = new Campus();
		List<Building> buildings = new ArrayList<>();
		
		Building first = new Building();
		first.setId(10);
		buildings.add(first);
		
		Building second = new Building();
		first.setId(20);
		buildings.add(second);
		
		input.setBuildings(buildings);
		
		Mockito.when(mockResourceRepo.findAllByBuilding_Id(anyInt())).thenThrow(new DataAccessResourceFailureException(null));
		
		List<Resource> result = null;
		DataAccessException returnedException = null;
		
		try {
			result = testService.getResourcesByCampus(input);
			fail("Method should throw an exception.");
		} catch (DataAccessException e) {
			returnedException = e;
		}
		
		assertNull("Method should not reach the return statemenet.", result);
		assertNotNull("Method should throw an exception.", returnedException);
	}
	
	/*-----------------------Tests for getAllResources() method ------------------------------*/
	
	@Test
	public void successfulGetAllResources() {
		List<Resource> returned = new ArrayList<>();
		returned.add(new Resource());
		
		Mockito.when(mockResourceRepo.findAll()).thenReturn(returned);
		
		List<Resource> result = testService.getAllResources();
		
		assertNotNull("Method should never return null.", result);
		assertThat("Method should return what the repository returns.", result, is(returned));
	}
	
	@Test
	public void emptyListReturnFOrGetAllResrouces() {
		List<Resource> returned = new ArrayList<>();
		
		Mockito.when(mockResourceRepo.findAll()).thenReturn(returned);
		
		List<Resource> result = testService.getAllResources();
		
		assertNotNull("Method should never return null.", result);
		assertThat("Method should return empty list just like the repo.", result, is(returned));
	}
	
	@Test
	public void resourceRepoErrorForGetAllResources() {
		Mockito.when(mockResourceRepo.findAll()).thenThrow(new DataAccessResourceFailureException(null));
		
		List<Resource> result = null;
		DataAccessException returnedException = null;
		
		try {
			result = testService.getAllResources();
			fail("Method should throw an exception.");
		} catch (DataAccessException e) {
			returnedException = e;
		}
		
		assertNull("Method should not get the the return statement.", result);
		assertNotNull("Method should throw an exception.", returnedException);
	}
}
