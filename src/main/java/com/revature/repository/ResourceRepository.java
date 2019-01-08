package com.revature.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.stereotype.Repository;


import com.revature.models.Resource;

/**
 * The Interface ResourceRepository.
 * Implementation of resources database
 * queries.
 * 
 *  @author 1811-Java-Nick | 12/27/2018
 */
@Repository
public interface ResourceRepository extends JpaRepository<Resource, Integer>, QueryByExampleExecutor<Resource> {

	/**
	 * Find all the resources by the building ID.
	 *
	 * @param id the building id
	 * @return the list of resources
	 */
	List<Resource> findAllByBuilding_Id(int id);
	
}
