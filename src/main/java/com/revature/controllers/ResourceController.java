package com.revature.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.models.Resource;
import com.revature.services.ResourceService;

@RestController
@RequestMapping("")
public class ResourceController {
	
	ResourceService resourceService;

	@Autowired
	public ResourceController(ResourceService resourceService) {
		super();
		this.resourceService = resourceService;
	}
	
	@PostMapping("")
	public Resource saveResource(@RequestBody Resource resource) {
		return resourceService.save(resource);
	}
}
