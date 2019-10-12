package com.game.persistence.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.game.persistence.models.Mensajes;

/**
 * @author pachi
 * Repositorio de la tabla mensajes.
 */

@Repository
public interface MensajesRepository extends JpaRepository <Mensajes , Long>{
	
	// Consulta para buscar todos los mensajes con los id de los dos usuarios
	@Query(value="select * from mensajes where id_escritor = ?1 and id_lector = ?2",nativeQuery=true)
	List<Mensajes> findByIdUsuario(long id, long id_2);
	
	// Consulta para buscar un mensaje
	@Query(value="select * from mensajes where id_escritor = ?1 and id_lector = ?2 and cuerpo = ?3",nativeQuery=true)
	Optional<Mensajes> findMensaje(long id, long id_2, String mensaje);
	
}
