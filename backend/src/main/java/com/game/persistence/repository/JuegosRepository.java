package com.game.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.game.persistence.models.Juegos;

@Repository
public interface JuegosRepository extends JpaRepository<Juegos , Long> {

}
