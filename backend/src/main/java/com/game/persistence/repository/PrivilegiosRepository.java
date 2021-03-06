package com.game.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.game.persistence.models.Privilegios;

/**
 * @author pachi
 * Repositorio de la tabla privilegios.
 */

@Repository
public interface PrivilegiosRepository extends JpaRepository <Privilegios , Long> {

}
