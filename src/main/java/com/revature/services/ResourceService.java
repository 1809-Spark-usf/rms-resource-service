package com.revature.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

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
		return resourceRepo.findAll(Example.of(resource, ExampleMatcher.matching().withIgnoreNullValues()));
	}


	//tested
	public Resource getResourceById(int id) {
		return resourceRepo.findById(id).orElseThrow(
				() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
	}

	
	//tested
	public List<Resource> getResourcesById(Iterable<Integer> ids) {
//		List<Resource> list = resourceRepo.findAll();
//		for(int i = 0; i < list.size(); i++) {
//			for(int j = 0; j < ids.length; j++) {
//				if(list.get(i).getId() == ids[j])
//					list.remove(i);
//			}
//		}
//		return list;
		return resourceRepo.findAllById(ids);
	}
	
//tested

	public Resource save(Resource resource) {
		resource.setBuilding(buildingRepo.getOne(resource.getBuildingId()));
		return resourceRepo.save(resource);
	}

	//before testing find how it is changing 
	public void updateResource(Resource resource, int id) {
		resource.setId(id);
		if(resource.getBuilding() == null)
			resource.setBuilding(buildingRepo.getOne(resource.getBuildingId()));
		resourceRepo.save(resource);
	}
}
