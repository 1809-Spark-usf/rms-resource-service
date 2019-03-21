//package com.revature.tests;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertNotEquals;
//import static org.junit.Assert.assertNull;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.when;
//
//import java.time.DayOfWeek;
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.Optional;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.springframework.web.client.HttpClientErrorException;
//
//import com.revature.enumerations.Type;
//import com.revature.exception.BadRequestException;
//import com.revature.models.Building;
//import com.revature.models.Campus;
//import com.revature.models.Resource;
//import com.revature.repository.BuildingRepository;
//import com.revature.repository.ResourceRepository;
//import com.revature.services.ResourceService;
//
//public class ResourceServiceTests {
//	Resource r = null;
//	// mock implementation
//	ResourceRepository mockResourceRepository = mock(ResourceRepository.class);
//	BuildingRepository mockBuildingRepository = mock(BuildingRepository.class);
//	ResourceService resourceService = new ResourceService(mockResourceRepository, mockBuildingRepository);
//
//	Type type;
//	Campus campus;
//	Building building;
//	int buildingId;
//	String name;
//	boolean disabled;
//	boolean inactive;
//	boolean retired;
//	LocalDateTime availableStartDate;
//	LocalDateTime reservableAfter;
//	LocalDateTime reservableBefore;
//	List<DayOfWeek> availableDays;
//	boolean hasEthernet;
//	boolean hasComputer;
//	int numberOfOutlets;
//	boolean hasMicrophone;
//
//	@Before
//	public void setUp() {
//		r = new Resource();
//	}
//
//	/**
//	 * when the resourceService calls the getResourceById method on the
//	 * ResourceRepository with the String "Not Found" the ResourceRepository will
//	 * return null. this is an example of a stub method
//	 */
//	@Test(expected = HttpClientErrorException.class)
//	public void TestgetResourceById() throws Exception {
//		// because this is the request expected.
//		when(mockResourceRepository.findById(0)).thenReturn(Optional.empty());
//		resourceService.getResourceById(0);
//	}
//	
//	@Test
//	public void testGetResourceExpected() throws Exception {
//		Resource resource = new Resource();
//		int id = 10;
//		when(mockResourceRepository.findById(id)).thenReturn(Optional.of(resource));
//		Resource result = resourceService.getResourceById(id);
//		assertEquals(resource, result);
//		
//	}
//
////	// testing the save method
////	@Test(expected = BadRequestException.class)
////	public void saveResouceWithNoType() throws Exception {
////		Resource badResource = new Resource(0, null, campus, building, buildingId, name, disabled, inactive, retired,
////				availableStartDate, reservableAfter, reservableBefore, availableDays, hasEthernet, hasComputer,
////				numberOfOutlets, hasMicrophone);
////		when(mockResourceRepository.save(badResource)).thenThrow(BadRequestException.class);
////		resourceService.save(badResource);
////	}
////
////	// testing the save method
////	@Test(expected = BadRequestException.class)
////	public void saveResouceWithNotCampus() throws Exception {
////		Resource badResource = new Resource(0, type, null, building, buildingId, name, disabled, inactive, retired,
////				availableStartDate, reservableAfter, reservableBefore, availableDays, hasEthernet, hasComputer,
////				numberOfOutlets, hasMicrophone);
////		when(mockResourceRepository.save(badResource)).thenThrow(BadRequestException.class);
////		resourceService.save(badResource);
////	}
////
////	// testing the save method
////	@Test(expected = BadRequestException.class)
////	public void saveResouceWithNoBuilding() throws Exception {
////		Resource badResource = new Resource(0, type, campus, null, buildingId, name, disabled, inactive, retired,
////				availableStartDate, reservableAfter, reservableBefore, availableDays, hasEthernet, hasComputer,
////				numberOfOutlets, hasMicrophone);
////		when(mockResourceRepository.save(badResource)).thenThrow(BadRequestException.class);
////		resourceService.save(badResource);
////	}
////
////	// testing the save method
////	@Test(expected = BadRequestException.class)
////	public void saveResouceWithNoBuildingId() throws Exception {
////		Resource badResource = new Resource(0, type, campus, building, 0, name, disabled, inactive, retired,
////				availableStartDate, reservableAfter, reservableBefore, availableDays, hasEthernet, hasComputer,
////				numberOfOutlets, hasMicrophone);
////		when(mockResourceRepository.save(badResource)).thenThrow(BadRequestException.class);
////		resourceService.save(badResource);
////	}
////
////	// testing the save method
////	@Test(expected = BadRequestException.class)
////	public void saveResouceWithNoName() throws Exception {
////		Resource badResource = new Resource(0, type, campus, building, buildingId, null, disabled, inactive, retired,
////				availableStartDate, reservableAfter, reservableBefore, availableDays, hasEthernet, hasComputer,
////				numberOfOutlets, hasMicrophone);
////		when(mockResourceRepository.save(badResource)).thenThrow(BadRequestException.class);
////		resourceService.save(badResource);
////	}
////
////	// testing the save method
////	@Test(expected = BadRequestException.class)
////	public void saveResouceWithNoAvailableStartDate() throws Exception {
////		Resource badResource = new Resource(0, type, campus, building, buildingId, name, disabled, inactive, retired,
////				null, reservableAfter, reservableBefore, availableDays, hasEthernet, hasComputer, numberOfOutlets,
////				hasMicrophone);
////		when(mockResourceRepository.save(badResource)).thenThrow(BadRequestException.class);
////		resourceService.save(badResource);
////	}
////
//////	// testing the save method
////	@Test(expected = BadRequestException.class)
////	public void saveResouceWithNoReservableAfter() throws Exception {
////		Resource badResource = new Resource(0, type, campus, building, buildingId, name, disabled, inactive, retired,
////				availableStartDate, null, reservableBefore, availableDays, hasEthernet, hasComputer, numberOfOutlets,
////				hasMicrophone);
////		when(mockResourceRepository.save(badResource)).thenThrow(BadRequestException.class);
////		resourceService.save(badResource);
////	}
////
////	// testing the save method
////	@Test(expected = BadRequestException.class)
////	public void saveResouceWithNoReservableBefore() throws Exception {
////		Resource badResource = new Resource(0, type, campus, building, buildingId, name, disabled, inactive, retired,
////				availableStartDate, reservableAfter, null, availableDays, hasEthernet, hasComputer, numberOfOutlets,
////				hasMicrophone);
////		when(mockResourceRepository.save(badResource)).thenThrow(BadRequestException.class);
////		resourceService.save(badResource);
////	}
//	
//	
//
//	// List<Resource> getResourceByBuildingId
//	@Test(expected = BadRequestException.class)
//	public void getResourcesByBuildingId() throws Exception {
//		when(mockResourceRepository.findAllByBuilding_Id(0)).thenThrow(BadRequestException.class);
//		resourceService.getResourceByBuildingId(0);
//	}
//
//	// List<Resource> getResourcesByCampus
//	//this is for when it is null
//	@Test(expected = NullPointerException.class)
//	public void TestResourcesByNoCampus() throws Exception {
//		Campus nullCampus = new Campus();
//		assertNull(resourceService.getResourcesByCampus(nullCampus));
//	}
//	
//	
//	@Test()
//	public void TestUpdateResourceEqualId() throws Exception {
//		// Create the parameters
//		// Create a resource
//		Resource resource = new Resource();
//		resource.setId(2);
//		// Create a variable for the id parameter, with an id different than resource.id
//		int i = 4;
//		// call updateResource
//		resourceService.updateResource(resource, i);
//		// assert that resource.id is now equal to id
//		assertEquals("updateResource assigns the id passed as the second parameter to the id of the resource passed as the first parameter", resource.getId(), i);
//		
//	}
//	
//	@Test
//	public void TestUpdateResourceBuildingId() throws Exception {
//		//if resource.building is null
//		int buildingId = 599;
//		Resource resource = new Resource();
//		resource.setBuildingId(buildingId);
//		//mock buildingrepo.getOne
//		 Building building = new Building();
//		when(mockBuildingRepository.getOne(buildingId)).thenReturn(building);
//		resourceService.updateResource(resource, buildingId);
//		//create value of building to set mock building repo
//		assertEquals("buidlingId should be equal to building Id if null",resource.getBuilding(), building);
//	}
//	@Test
//	public void TestUpdateResourceBuildingNotNull() throws Exception {
//		int id = 4;	
//		Resource resource = new Resource();
//		resource.setBuildingId(id);
//		Building building = new Building();
//		when(mockBuildingRepository.getOne(id)).thenReturn(building);
//		resourceService.updateResource(resource, id);
//		//confirming the resource building is not equal to the mock resource building 
//		assertNotEquals(resource,building);
//	}
//	
//
//}
