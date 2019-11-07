package com.game.persistence.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.game.persistence.models.ListaAmigos;

/**
 * @author pachi
 * Repositorio de la tabla lista_amigos.
 */

@Repository
public interface Lista_amigosRepository extends JpaRepository <ListaAmigos , Long>  {
	
	// Consulta para buscar una amistad con los id de los dos usuarios
	@Query(value="select * from lista_amigos where id_usuario = ?1 and id_amigo = ?2",nativeQuery=true)
	Optional<ListaAmigos> findByIdUsuario(long id, long id_2);

	// Consulta para traer todos los amigos de un usuario segun su id
	@Query(value="select * from lista_amigos where id_usuario = ?1",nativeQuery=true)
	List<ListaAmigos> findAmigosById(long id);

}
