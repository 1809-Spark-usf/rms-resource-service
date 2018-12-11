package com.revature.controllers;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.revature.models.Campus;
import com.revature.models.Resource;
import com.revature.models.ResourceObject;
import com.revature.services.CampusService;
import com.revature.services.ResourceService;

@RestController
@RequestMapping("")
public class ResourceController {

	ResourceService resourceService;
	CampusService campusService;

	@Autowired
	public ResourceController(ResourceService resourceService, CampusService campusService) {
		super();
		this.resourceService = resourceService;
		this.campusService = campusService;
	}

	@PostMapping("")
	public Resource saveResource(@RequestBody ResourceObject resource) {
		resource.setBuilding(campusService.getBuilding(resource.getBuildingId()));
		return resourceService.save(new Resource(resource));
	}

	@GetMapping("/campuses")
	public List<Campus> getBuildings() {
		return campusService.getCampuses();
	}

	@GetMapping("/building/{id}")
	public List<Resource> getByBuildingId(@PathVariable int id) {
		return resourceService.getResourceByBuildingId(id);
	}

	@GetMapping("/campus/{id}")
	public List<Resource> getByCampus(@PathVariable int id) {
		return resourceService.getResourcesByCampus(campusService.getCampus(id));
	}

	/**
	 * Takes in a resource and a id Gets the resource from the database and replaces
	 * it with the incoming resource.
	 * 
	 * @param resource
	 * @param id
	 */
	@PutMapping("/{id}")
	public void updateResource(@RequestBody ResourceObject resource, @PathVariable int id) {
		resourceService.updateResource(new Resource(resource), id);
	}

	/**
	 * Returns all resources paginated.
	 * 
	 * @return
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonParseException
	 */
	@GetMapping("")
	public List<Resource> findResources() {
		return resourceService.getAllResources();
	}

	/**
	 * Gets a specific resource by Id. Returns it or null.
	 * 
	 * @param id
	 * @return
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
}
