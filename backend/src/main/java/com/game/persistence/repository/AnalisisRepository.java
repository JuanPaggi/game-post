package com.game.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.game.persistence.models.Analisis;

@Repository
public interface AnalisisRepository extends JpaRepository<Analisis , Long>{

}
