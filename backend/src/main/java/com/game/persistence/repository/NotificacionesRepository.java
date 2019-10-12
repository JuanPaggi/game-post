package com.game.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.game.persistence.models.Notificaciones;

/**
 * @author pachi
 * Repositorio de la tabla notificaciones.
 */

@Repository
public interface NotificacionesRepository extends JpaRepository <Notificaciones , Long>  {

	@Query(value="select * from notificaciones where id_usuario = ?1",nativeQuery=true)
	List<Notificaciones> findByIdNotificaciones(long id);
}
