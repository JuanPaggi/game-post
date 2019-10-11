package com.game.persistence.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.game.persistence.models.SolicitudesAmistad;

/**
 * @author pachi
 * Repositorio de la tabla solicitudes_amistad.
 */

@Repository
public interface SolicitudesAmistadRepository extends JpaRepository <SolicitudesAmistad , Long>{

	// Consulta para buscar una solicitud con los id de los dos usuarios
	@Query(value="select * from solicitudes_amistad where id_usuario = ?1 and id_amigo = ?2",nativeQuery=true)
	Optional<SolicitudesAmistad> findByIdUsuario(long id, long id_2);
	
	// Consulta para traer todos los amigos de un usuario segun su id
	@Query(value="select * from solicitudes_amistad where id_usuario = ?1",nativeQuery=true)
	List<SolicitudesAmistad> findAmigosById(long id);
}
