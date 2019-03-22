package com.revature.services;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import com.revature.models.Building;
import com.revature.models.Campus;
import com.revature.models.Resource;
import com.revature.repository.BuildingRepository;
import com.revature.repository.ResourceRepository;

/**
 * The Class ResourceService.
 * Service that talks to the repository
 * to execute any actions involving
 * the database.
 * 
 * It gets, saves, and update any resource 
 * the controllers pass to this, and sends
 * it to the repository to get/save/update
 * to the database.
 * 
 *  @author 1811-Java-Nick | 12/27/2018
 */
@Service
public class ResourceService {
	
	/** The resource repo. */
	ResourceRepository resourceRepo;
	
	/** The building repo. */
	BuildingRepository buildingRepo;

	/**
	 * Instantiates a new resource service.
	 *
	 * @param resourceRepo the resource repo
	 * @param buildingRepo the building repo
	 */
	@Autowired
	public ResourceService(ResourceRepository resourceRepo, BuildingRepository buildingRepo) {
		super();
		this.resourceRepo = resourceRepo;
		this.buildingRepo = buildingRepo;
	}

	/**
	 * Find resources.
	 *
	 * @param resource the resource
	 * @return the list
	 */
	public List<Resource> findResources(Resource resource) {
		if(resource.getBuildingId() > 0) {
			resource.setBuilding(buildingRepo.getOne(resource.getBuildingId()));
			resource.setBuildingId(0);
		}
		return resourceRepo.findAll(Example.of(resource, ExampleMatcher.matchingAll().withIgnoreNullValues()));
	}


	/**
	 * Gets the resource by id.
	 *
	 * @param id the id
	 * @return the resource by id
	 */
	//tested
	public Resource getResourceById(int id) {
		return resourceRepo.findById(id).orElseThrow(
				() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
	}

	

	/**
	 * Gets the resources by id.
	 *
	 * @param ids the ids
	 * @return the resources by id
	 */
	public List<Resource> getResourcesById(Iterable<Integer> ids) {
		return resourceRepo.findAllById(ids);
	}
	
/**
 * Save.
 *
 * @param resource the resource
 * @return the resource
 */
//tested
	public Resource save(Resource resource) {
		return resourceRepo.save(resource);
	}
	
	//1. After test is run, resource.id value is equal to parameter id
	//2. When resource.building is null, value of building will be set to mockBuildingRepo.getOne return value
	//3. When resource.building is not null, value of building will not be set to mockBuildingRepo.getOne return value
	//4. resourceRepo.save is called with resource as argument and updateResource returns value returned by mockResourceRepo.save
 
	/**
	 * Update resource.
	 *
	 * @param resource the resource
	 * @param id the id
	 */
	public void updateResource(Resource resource, int id) throws EntityNotFoundException, DataAccessException {
		resource.setId(id);
		if(resource.getBuilding() == null) {
			try {
				resource.setBuilding(buildingRepo.getOne(resource.getBuildingId()));
			} catch(EntityNotFoundException e) {
				throw e;
			}
		}
		resourceRepo.save(resource);
	}
	
/**
 * Gets the resource by building id.
 *
 * @param id the id
 * @return the resource by building id
 */
//tested
	public List<Resource> getResourceByBuildingId(int id) {
		return resourceRepo.findAllByBuilding_Id(id);
	}

/**
 * Gets the resources by campus.
 *
 * @param campus the campus
 * @return the resources by campus
 */
//tested
	public List<Resource> getResourcesByCampus(Campus campus) {
		List<Resource> resources = new ArrayList<>();
		if(campus != null) {
			List<Building> buildings = campus.getBuildings();
			for(int i = 0; i < buildings.size(); i++) {
				List<Resource> tmp = resourceRepo.findAllByBuilding_Id(buildings.get(i).getId());
				resources.addAll(tmp);
			}
		} else {
			throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Campus is null");
		}
		return resources;
	}

	/**
	 * Gets the all resources.
	 *
	 * @return the all resources
	 */
	public List<Resource> getAllResources() throws DataAccessException{
		return resourceRepo.findAll();
	}
}
