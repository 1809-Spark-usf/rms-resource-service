package com.revature.controllers;

import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import com.revature.models.Building;
import com.revature.models.Campus;
import com.revature.models.Resource;
import com.revature.models.ResourceObject;
import com.revature.services.CampusService;
import com.revature.services.ResourceService;

/**
 * The Class ResourceController. set of post and get request that talk to the
 * services to insert or retrieve information from the database.
 * 
 * @author 1811-Java-Nick | 12/27/2018
 */
@RestController
@RequestMapping("")
public class ResourceController {

	/** The resource service. */
	ResourceService resourceService;

	/** The campus service. */
	CampusService campusService;

	/**
	 * Instantiates a new resource controller.
	 *
	 * @param resourceService the resource service.
	 * @param campusService   the campus service.
	 */
	@Autowired
	public ResourceController(ResourceService resourceService, CampusService campusService) {
		super();
		this.resourceService = resourceService;
		this.campusService = campusService;
	}

	/**
	 * Save resource object into the database.
	 *
	 * @param resource the object that holds the resource.
	 * @return the resource that was saved in the database.
	 */
	@PostMapping("")
	@ResponseStatus(HttpStatus.CREATED)
	public Resource saveResource(@RequestBody ResourceObject resource) throws Exception {
		if (resource.getBuildingId() == 0) {
			throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Empty body in the request");
		}
		try {
			Building building = campusService.getBuilding(resource.getBuildingId());
			resource.setBuilding(building);
		} catch (EntityNotFoundException e) {
			throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
		}
		return resourceService.save(new Resource(resource));

	}

	/**
	 * Gets the list of buildings (campus) from the database.
	 *
	 * @return the list of campus.
	 */
	@GetMapping("/campuses")
	public List<Campus> getBuildings() {
		return campusService.getCampuses();
	}

	/**
	 * Gets the list of resources by the building id.
	 *
	 * @param id the building ID.
	 * @return the list of resources.
	 */
	@GetMapping("/building/{id}")
	public List<Resource> getByBuildingId(@PathVariable int id) {
		List<Resource> result;
		try {
			result = resourceService.getResourceByBuildingId(id);
		} catch (EntityNotFoundException e) {
			throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "The Resource you are looking for does not exist");
		}
		return result;
	}

	/**
	 * Gets the list of resources by the campus id.
	 *
	 * @param id the campus id
	 * @return the list of resources
	 */
	@GetMapping("/campus/{id}")
	public List<Resource> getByCampus(@PathVariable int id) {
		List<Resource> result;
		try {
			result = resourceService.getResourcesByCampus(campusService.getCampus(id));
		} catch (EntityNotFoundException e) {
			throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "No campus with given id was found");
		}
		return result;
	}

	/**
	 * Takes in a resource and a id, gets the resource from the database and
	 * replaces it with the incoming resource.
	 *
	 * @param resource the resource object
	 * @param id       the id of the object you want to update
	 */
	@PutMapping("/{id}")
	public void updateResource(@RequestBody ResourceObject resource, @PathVariable int id) {
		try {
			resourceService.updateResource(new Resource(resource), id);
		} catch (EntityNotFoundException e) {
			throw new HttpClientErrorException(HttpStatus.NOT_FOUND,
					"The resource you're trying to update doesn't exist");
		} catch (DataAccessException e) {
			throw new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR,
					"Something went wrong while trying to update the resource.");
		}

	}

	/**
	 * Returns all resources paginated.
	 *
	 * @return the list of resource objects
	 */
	@GetMapping("")
	public List<Resource> findResources() {
		return resourceService.getAllResources();
	}

	/**
	 * Gets a specific resource by Id. Returns it or null.
	 *
	 * @param id the id of the resources
	 * @return the list of resources
	 */
	@GetMapping("/{id}")
	public List<Resource> getResources(@PathVariable int[] id) {
		Integer[] fakeId = new Integer[id.length];
		for (int i = 0; i < id.length; i++) {
			fakeId[i] = id[i];
		}
		List<Integer> ids = Arrays.asList(fakeId);
		return resourceService.getResourcesById(ids);
	}

	@ExceptionHandler
	public ResponseEntity<String> handleHttpClientException(HttpClientErrorException e) {
		String message = e.getMessage();
		return ResponseEntity.status(e.getStatusCode()).body(message);

	}
}
