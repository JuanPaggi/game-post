package com.game.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.game.persistence.models.Analisis;

@Repository
public interface AnalisisRepository extends JpaRepository<Analisis , Long>{

	@Query(value="select * from analisis", nativeQuery=true) 
	List<Analisis> findAllAnalisis();
	
}