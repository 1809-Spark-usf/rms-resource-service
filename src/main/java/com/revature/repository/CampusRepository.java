package com.revature.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.models.Campus;

/**
 * The Interface CampusRepository.
 * Implementation of campus database
 * queries.
 * 
 *  @author 1811-Java-Nick | 12/27/2018
 */
@Repository
public interface CampusRepository extends JpaRepository<Campus, Integer> {
}
