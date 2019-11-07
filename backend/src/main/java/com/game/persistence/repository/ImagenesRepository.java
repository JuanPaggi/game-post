package com.game.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.game.persistence.models.Imagenes;

@Repository
public interface ImagenesRepository extends JpaRepository<Imagenes, Long>{

}
