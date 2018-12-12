package com.revature.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

@Service
public class ResourceService {
	
	ResourceRepository resourceRepo;
	BuildingRepository buildingRepo;

	@Autowired
	public ResourceService(ResourceRepository resourceRepo, BuildingRepository buildingRepo) {
		super();
		this.resourceRepo = resourceRepo;
		this.buildingRepo = buildingRepo;
	}

	public List<Resource> findResources(Resource resource) {
		if(resource.getBuildingId() > 0) {
			resource.setBuilding(buildingRepo.getOne(resource.getBuildingId()));
			resource.setBuildingId(0);
		}
		return resourceRepo.findAll(Example.of(resource, ExampleMatcher.matchingAll().withIgnoreNullValues()));
	}


	//tested
	public Resource getResourceById(int id) {
		return resourceRepo.findById(id).orElseThrow(
				() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
	}

	
	//tested
	public List<Resource> getResourcesById(Iterable<Integer> ids) {
		return resourceRepo.findAllById(ids);
	}
	
//tested
	public Resource save(Resource resource) {
		return resourceRepo.save(resource);
	}
	
	
 
	public void updateResource(Resource resource, int id) {
		resource.setId(id);
		if(resource.getBuilding() == null)
			resource.setBuilding(buildingRepo.getOne(resource.getBuildingId()));
		resourceRepo.save(resource);
	}
//tested
	public List<Resource> getResourceByBuildingId(int id) {
		return resourceRepo.findAllByBuilding_Id(id);
	}
//tested
	public List<Resource> getResourcesByCampus(Campus campus) {
		List<Resource> resources = new ArrayList<>();
		if(campus != null) {
			List<Building> buildings = campus.getBuildings();
			for(int i = 0; i < buildings.size(); i++) {
				List<Resource> tmp = resourceRepo.findAllByBuilding_Id(buildings.get(i).getId());
				resources.addAll(tmp);
			}
		}
		return resources;
	}

	public List<Resource> getAllResources() {
		return resourceRepo.findAll();
	}
}
