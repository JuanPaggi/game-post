package com.game.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.game.persistence.models.Tag;

/**
 * @author pachi
 * Repositorio de la tabla tag.
 */

@Repository
public interface TagRepository extends JpaRepository <Tag , Long> {

}
