package com.revature.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.stereotype.Repository;

import com.revature.models.Resource;

@Repository
public interface ResourceRepository extends JpaRepository<Resource, Integer>, QueryByExampleExecutor<Resource> {
	
}
