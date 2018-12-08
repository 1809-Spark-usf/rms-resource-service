package com.revature.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.models.Building;
import com.revature.models.Campus;
import com.revature.repository.BuildingRepository;
import com.revature.repository.CampusRepository;

@Service
public class CampusService {

	CampusRepository campusRepo;
	BuildingRepository buildingRepo;
	
	@Autowired
	public CampusService(CampusRepository campusRepo, BuildingRepository buildingRepo) {
		super();
		this.campusRepo = campusRepo;
		this.buildingRepo = buildingRepo;
	}
	
	


//	public List<Building> getBuildings(int campus) {
////		return campusRepo.findByCampusId(campus); 
//	}


	public List<Campus> getCampuses() {
		return campusRepo.findAll();
	}
	
	public Building getBuilding(int id) {
		return buildingRepo.getOne(id);
	}




	public Campus getCampus(int id) {
		return campusRepo.getOne(id);
	}

}
