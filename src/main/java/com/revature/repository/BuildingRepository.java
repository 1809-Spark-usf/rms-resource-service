package com.revature.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.revature.models.Building;

/**
 * The Interface BuildingRepository.
 * Implementation of building database
 * queries.
 * 
 *  @author 1811-Java-Nick | 12/27/2018
 */
public interface BuildingRepository extends JpaRepository<Building, Integer>{

}
