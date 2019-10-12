package com.game.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.game.persistence.models.Donaciones;

/**
 * @author pachi
 * Repositorio de la tabla donaciones.
 */

@Repository
public interface DonacionesRepository extends JpaRepository <Donaciones , Long>  {

	@Query(value="select * from donaciones where id_usuario = ?1",nativeQuery=true)
	List<Donaciones> findByIdDonaciones(long id);

}
