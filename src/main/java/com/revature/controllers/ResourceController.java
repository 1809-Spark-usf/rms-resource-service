package com.revature.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.models.Campus;
import com.revature.models.Resource;
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
	public Resource saveResource(@RequestBody Resource resource) {
		resource.setBuilding(campusService.getBuilding(resource.getBuildingId()));
		System.out.println(resource);
		return resourceService.save(resource);
	}
	
	@GetMapping("/campuses")
	public List<Campus> getBuildings() {
		return campusService.getCampuses();
	}
	
	/**
	 * Takes in a resource and a id
	 * Gets the resource from the database and replaces it with the incoming resource.
	 * @param resource
	 * @param id
	 */
	@PutMapping("/{id}")
	public void updateResource(@RequestBody Resource resource, @PathVariable int id) {
		
	}
	
	/**
	 * Returns all resources paginated.
	 * @return
	 */
//	@GetMapping("")
//	public List<Resource> getResources(Pageable pageable) {
//		return resourceService.getAllResources(pageable);
//	}
	
	/**
	 * Gets a specific resource by Id.
	 * Returns it or null.
	 * @param id
	 * @return
	 */
	@GetMapping("/{id}")
	public Resource getResources(@PathVariable int id) {
		return resourceService.getResourceById(id);
	}
	
	/**
	 * Takes in an array of ints (hopefully) and returns all other resources.
	 * @param ids
	 * @return
	 */
	@GetMapping("availabe/{id}")
	public List<Resource> getResources(@PathVariable int[] id) {
		return resourceService.getResourcesById(id);
	}
}
