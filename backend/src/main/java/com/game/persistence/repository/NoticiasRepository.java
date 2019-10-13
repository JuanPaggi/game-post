package com.game.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.game.persistence.models.Noticias;

/**
 * @author negro
 * Repositorio de la tabla noticias.
 */

@Repository
public interface NoticiasRepository extends JpaRepository <Noticias , Long> {

}
