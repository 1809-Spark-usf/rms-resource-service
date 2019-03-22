package com.revature.services;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import com.revature.models.Building;
import com.revature.models.Campus;
import com.revature.repository.BuildingRepository;
import com.revature.repository.CampusRepository;

/**
 * The Class CampusService.
 * Service that talks to the repository
 * to execute any actions involving
 * the database.
 * 
 * It either gets building objects 
 * or campus objects from the database.
 * 
 *  @author 1811-Java-Nick | 12/27/2018
 */
@Service
public class CampusService {

	/** The campus repo. */
	CampusRepository campusRepo;
	
	/** The building repo. */
	BuildingRepository buildingRepo;
	
	/**
	 * Instantiates a new campus service.
	 *
	 * @param campusRepo the campus repo
	 * @param buildingRepo the building repo
	 */
	@Autowired
	public CampusService(CampusRepository campusRepo, BuildingRepository buildingRepo) {
		super();
		this.campusRepo = campusRepo;
		this.buildingRepo = buildingRepo;
	}

	/**
	 * Gets the list of campuses.
	 *
	 * @return the campuses
	 */
	public List<Campus> getCampuses() {
		return campusRepo.findAll();
	}
	
	/**
	 * Gets the building by the building id.
	 *
	 * @param id the building id
	 * @return the building
	 */
	public Building getBuilding(int id) throws EntityNotFoundException, DataAccessException {
		Building result;
		try {
			result = buildingRepo.getOne(id);
		} catch(EntityNotFoundException e) {
			throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
		}
		return result;
	}




	/**
	 * Gets the campus by the campus id.
	 *
	 * @param id the campus id
	 * @return the campus
	 */
	public Campus getCampus(int id) throws EntityNotFoundException {
		return campusRepo.getOne(id);
	}

}
