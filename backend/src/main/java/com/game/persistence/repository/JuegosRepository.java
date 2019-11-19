package com.game.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.game.persistence.models.Juegos;

/**
 * @author pachi
 * Repositorio de la tabla juegos.
 */

@Repository
public interface JuegosRepository extends JpaRepository <Juegos , Long> {
	
	// agrega una valoracion positiva
	@Modifying
	@Query(value="UPDATE juegos SET analisis_positivos = analisis_positivos + 1 WHERE id_juego = ?1",nativeQuery=true)
	int agregarValoracionPositiva(long id);
	
	// agrega una valoracion negativa
	@Modifying
	@Query(value="UPDATE juegos SET analisis_negativos = analisis_negativos + 1 WHERE id_juego = ?1",nativeQuery=true)
	int agregarValoracionNegativa(long id);
}
