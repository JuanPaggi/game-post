package com.game.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.game.persistence.models.Modos;

/**
 * @author negro
 * Repositorio de la tabla modos.
 */

@Repository
public interface ModosRepository extends JpaRepository <Modos , Long> {
	
}
