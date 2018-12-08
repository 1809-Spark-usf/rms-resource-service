package com.revature.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.revature.models.Building;

public interface BuildingRepository extends JpaRepository<Building, Integer>{

}
