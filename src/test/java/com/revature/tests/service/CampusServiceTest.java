package com.revature.tests.service;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;

import com.revature.models.Building;
import com.revature.models.Campus;
import com.revature.repository.BuildingRepository;
import com.revature.repository.CampusRepository;
import com.revature.services.CampusService;

/**
 * Tests for all methods in the CampusService
 * 
 * @author Kyne Liu
 *
 */
@RunWith(SpringRunner.class)
public class CampusServiceTest {
	
	@Mock
	private CampusRepository mockCampusRepo;
	
	@Mock
	private BuildingRepository mockBuildingRepo;
	
	@InjectMocks
	private CampusService service;
	
	/*---------------------Tests for getCampuses() method -------------------------*/
	
	@Test
	public void successfulGetCampuses() {
		List<Campus> returned = new ArrayList<>();
		returned.add(new Campus());
		
		Mockito.when(mockCampusRepo.findAll()).thenReturn(returned);
		
		List<Campus> result = service.getCampuses();
		
		assertNotNull("Method should always return a list.", result);
		assertThat("Method should return the list it gets from the repo.", result, is(returned));
	}
	
	@Test
	public void emptyListReturnGetCampuses() {
		List<Campus> returned = new ArrayList<>();
		
		Mockito.when(mockCampusRepo.findAll()).thenReturn(returned);
		
		List<Campus> result = service.getCampuses();
		
		assertNotNull("Method should always return a list", result);
		assertThat("Method should return an empty list", result, is(returned));
	}
	
	@Test
	public void repoErrorGetCampuses() {
		Mockito.when(mockCampusRepo.findAll()).thenThrow(new DataAccessResourceFailureException(null));
		
		List<Campus> result = null;
		DataAccessException resultException = null;
		try {
			result = service.getCampuses();
			fail("DataAccessException was not thrown");
		} catch (DataAccessException e) {
			resultException = e;
		}
		
		assertNull("Result should be null because nothing should be returned from the repo", result);
		assertNotNull("An exception should of been thrown by the repo", resultException);
	}

	/*---------------------Tests for getBuilding(int) method -------------------------*/
	
	@Test
	public void successfulGetBuilding() {
		int targetId = 10;
		Building returned = new Building();
		returned.setId(targetId);
		
		Mockito.when(mockBuildingRepo.getOne(targetId)).thenReturn(returned);
		
		Building result = service.getBuilding(targetId);
		
		assertNotNull("Method never return a null value", result);
		assertThat("Method should return the object returned from the repository.", result, is(returned));
	}
	
	@Test
	public void buildingNotFoundForGetBuilding() {
		int badId = 3;
		
		Mockito.when(mockBuildingRepo.getOne(badId)).thenThrow(new EntityNotFoundException());
		
		Building result = null;
		HttpClientErrorException returnedException =  null;
		
		try {
			result = service.getBuilding(badId);
			fail("Method should throw an http not found exception if the enitiy is not found in the table");
		} catch(HttpClientErrorException e) {
			returnedException = e;
		}
		
		assertNull("Result should be null because the repo should throw an exception", result);
		assertNotNull("Method should throw an exception.", returnedException);
		assertThat("Status sent back should be a 404 not found", returnedException.getStatusCode(), is(HttpStatus.NOT_FOUND));
	}
	
	@Test
	public void repoErrorForGetBuilding() {
		int targetId = 10;
		
		Mockito.when(mockBuildingRepo.getOne(targetId)).thenThrow(new DataAccessResourceFailureException(null));
		
		Building result = null;
		DataAccessException returnedException = null;
		
		try {
			result = service.getBuilding(targetId);
			fail("Method should throw an exception");
		} catch(DataAccessException e) {
			returnedException = e;
		}
		
		assertNull("The result variable should be null because the repo should throw an exception", result);
		assertNotNull("An excpetion should be thrown.", returnedException);
	}
	
	/*---------------------Tests for getCampus(int) method -------------------------*/
	
	@Test
	public void successfulGetCampus() {
		int targetId = 10;
		Campus returned = new Campus();
		returned.setId(targetId);
		
		Mockito.when(mockCampusRepo.getOne(targetId)).thenReturn(returned);
		
		Campus result = service.getCampus(targetId);
		
		assertNotNull("Should not return null if the campus is found.", result);
		assertThat("Should return the same campus that the repository returns", result, is(returned));
	}
	
	@Test
	public void campusNotFoundForGetCampus() {
		int badId = 3;
		
		Mockito.when(mockCampusRepo.getOne(badId)).thenThrow(new EntityNotFoundException());
		
		Campus result =  null;
		EntityNotFoundException returnedException = null;
		
		try {
			result = service.getCampus(badId);
			fail("The method should thow an exception when the enitity is not found");
		} catch(EntityNotFoundException e) {
			returnedException = e;
		}
		
		assertNull("The repository returned nothing should result should be null.", result);
		assertNotNull("Method should throw an EntityNotFoundException.", returnedException);
	}
	
	@Test
	public void repoErrorForGetCampus() {
		int targetId = 10;
		
		Mockito.when(mockCampusRepo.getOne(targetId)).thenThrow(new DataAccessResourceFailureException(null));
		
		Campus result = null;
		DataAccessException returnedException =  null;
		
		try {
			result = service.getCampus(targetId);
			fail("Method should throw an excpetion");
		} catch(DataAccessException e) {
			returnedException = e;
		}
		
		assertNull("Repository should throw exception so should return nothing", result);
		assertNotNull("An exception should be thrown by the method.", returnedException);
	}
}
