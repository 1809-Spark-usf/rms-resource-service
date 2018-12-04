package com.revature.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.repository.ResourceRepository;

@Service
public class ResourceService {
	
	ResourceRepository resourceRepo;

	@Autowired
	public ResourceService(ResourceRepository resourceRepo) {
		super();
		this.resourceRepo = resourceRepo;
	}
	
}
