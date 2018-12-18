package com.revature.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.models.Campus;

@Repository
public interface CampusRepository extends JpaRepository<Campus, Integer> {
}
