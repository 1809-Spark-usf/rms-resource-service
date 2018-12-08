package com.revature.tests;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.awt.List;
import java.time.LocalDateTime;
import org.junit.Before;
import org.junit.Test;

import com.google.common.primitives.Ints;
import com.revature.enumerations.Type;
import com.revature.exception.BadRequestException;
import com.revature.models.Campus;
import com.revature.models.Resource;
import com.revature.repository.BuildingRepository;
import com.revature.repository.ResourceRepository;
import com.revature.services.ResourceService;

public class ResourceServiceTests {
	Resource r = null;
	// mock implementation
	ResourceRepository mockResourceRepository = mock(ResourceRepository.class);
	BuildingRepository mockBuildingRepository = mock(BuildingRepository.class);
	ResourceService resourceService = new ResourceService(mockResourceRepository, mockBuildingRepository);

	Type type;
	Campus campus;
	String name;
	boolean disabled;
	boolean inactive;
	boolean retired;
	LocalDateTime useableFrom;
	LocalDateTime reservableAfter;
	LocalDateTime reservableBefore;
	boolean hasEthernet;
	boolean hasComputer;
	int numberOfOutlets;
	boolean hasMicrophone;

	@Before
	public void setUp() {
		r = new Resource();
	}

	/**
	 * when the resourceService calls the getResourceById method on the
	 * ResourceRepository with the String "Not Found" the ResourceRepository will
	 * return null. this is an example of a stub method
	 */
	@Test(expected = BadRequestException.class)
	public void TestgetResourceById() throws Exception {
		// because this is the request expected.
		when(mockResourceRepository.findById(0)).thenThrow(BadRequestException.class);
		resourceService.getResourceById(0);
	}

	// List<Resource> getResourcesById
	@Test(expected = BadRequestException.class)
	public void getResourcesById() throws Exception {
		int[] arrThatContainsAZero = new int[0];
		Iterable<Integer> iterable = Ints.asList(arrThatContainsAZero);
		when(mockResourceRepository.findAllById(iterable)).thenThrow(BadRequestException.class);
//		resourceService.getResourcesById(arrThatContainsAZero);
	}

	// testing the save method
//	@Test(expected = BadRequestException.class)
//	public void saveResouceWithNoType() throws Exception {
//		Resource badResource = new Resource(0, null, campus, name, disabled, inactive, retired, useableFrom,
//				reservableAfter, reservableBefore, hasEthernet, hasComputer, numberOfOutlets, hasMicrophone);
//		when(mockResourceRepository.save(badResource)).thenThrow(BadRequestException.class);
//		resourceService.save(badResource);
//	}
//
//	// testing the save method
//	@Test(expected = BadRequestException.class)
//	public void saveResouceWithNoCampus() throws Exception {
//		Resource badResource = new Resource(0, type, null, name, disabled, inactive, retired, useableFrom,
//				reservableAfter, reservableBefore, hasEthernet, hasComputer, numberOfOutlets, hasMicrophone);
//		when(mockResourceRepository.save(badResource)).thenThrow(BadRequestException.class);
//		resourceService.save(badResource);
//	}
//
//	// testing the save method
//	@Test(expected = BadRequestException.class)
//	public void saveResouceWithNoName() throws Exception {
//		Resource badResource = new Resource(0, type, campus, null, disabled, inactive, retired, useableFrom,
//				reservableAfter, reservableBefore, hasEthernet, hasComputer, numberOfOutlets, hasMicrophone);
//		when(mockResourceRepository.save(badResource)).thenThrow(BadRequestException.class);
//		resourceService.save(badResource);
//	}
//
//	// testing the save method
//	@Test(expected = BadRequestException.class)
//	public void saveResouceWithNoUseableFrom() throws Exception {
//		Resource badResource = new Resource(0, type, campus, name, disabled, inactive, retired, null, reservableAfter,
//				reservableBefore, hasEthernet, hasComputer, numberOfOutlets, hasMicrophone);
//		when(mockResourceRepository.save(badResource)).thenThrow(BadRequestException.class);
//		resourceService.save(badResource);
//	}
//
//	// testing the save method
//	@Test(expected = BadRequestException.class)
//	public void saveResouceWithNoReservableAfter() throws Exception {
//		Resource badResource = new Resource(0, type, campus, name, disabled, inactive, retired, useableFrom, null,
//				reservableBefore, hasEthernet, hasComputer, numberOfOutlets, hasMicrophone);
//		when(mockResourceRepository.save(badResource)).thenThrow(BadRequestException.class);
//		resourceService.save(badResource);
//	}
//
//	// testing the save method
//	@Test(expected = BadRequestException.class)
//	public void saveResouceWithNoReservableBefore() throws Exception {
//		Resource badResource = new Resource(0, type, campus, name, disabled, inactive, retired, useableFrom,
//				reservableAfter, null, hasEthernet, hasComputer, numberOfOutlets, hasMicrophone);
//		when(mockResourceRepository.save(badResource)).thenThrow(BadRequestException.class);
//		resourceService.save(badResource);
//	}

	 //testing the update method
	//As a whole we are updating an existing resource
	@Test(expected = BadRequestException.class)
	public void UpdateOldResource() throws Exception {
		when(mockResourceRepository.findById(0)).thenThrow(BadRequestException.class);
		Resource oldResource = new Resource(0, type, campus, name, disabled, inactive, retired, useableFrom,
				reservableAfter, reservableBefore, hasEthernet, hasComputer, numberOfOutlets, hasMicrophone);
		resourceService.save(oldResource);
	}
	
	

}
