package com.game.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.game.persistence.models.Juegos;

@Repository
public interface JuegosRepository extends JpaRepository<Juegos , Long> {

	@Query(value="select * from juegos", nativeQuery=true) 
	List<Juegos> findAllJuegos();
	
}
