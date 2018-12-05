package com.revature.tests;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Test;

import com.revature.exception.BadRequestException;
import com.revature.models.Resource;
import com.revature.repository.ResourceRepository;
import com.revature.services.ResourceService;

public class ResourceServiceTests {
	Resource r = null;
	// mock implementation
	ResourceRepository mockResourceRepository = mock(ResourceRepository.class);
	ResourceService resourceService = new ResourceService(mockResourceRepository);
	enum Type {
		CUBICLE, OFFICE
	}
	enum Location {
		RESTON, USF
	}
	Type type;
	Location location;
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
		when(mockResourceRepository.getResourceById(0)).thenThrow(BadRequestException.class);
		resourceService.getResourceById(0);
	}

	// List<Resource> getResourcesById
	@Test(expected = BadRequestException.class)
	public void getResourcesById() throws Exception {
		int[] arrThatContainsAZero = new int[0];
		when(mockResourceRepository.getResourcesById(arrThatContainsAZero)).thenThrow(BadRequestException.class);
		resourceService.getResourcesById(arrThatContainsAZero);
	}

	// testing the save method
	@Test(expected=BadRequestException.class)
	public void saveResouceWithNoType()throws Exception {
		Resource badResource = new Resource
				(0,null, location, name, disabled, inactive, retired, useableFrom, reservableAfter, reservableBefore, hasEthernet, hasComputer, numberOfOutlets, hasMicrophone)
		when(mockResourceRepository.save)
	}
}
