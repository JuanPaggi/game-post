package com.game.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.game.persistence.models.Requisitos;

/**
 * @author pachi
 * Repositorio de la tabla requisitos.
 */

@Repository
public interface RequisitosRepository extends JpaRepository <Requisitos , Long> {

}
