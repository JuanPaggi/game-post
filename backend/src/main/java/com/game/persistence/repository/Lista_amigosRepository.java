package com.game.persistence.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.game.persistence.models.Lista_amigos;

public interface Lista_amigosRepository extends JpaRepository<Lista_amigos , Long>  {
	@Query(value="select * from lista_amigos where id_usuario = ?1 and id_amigo = ?2",nativeQuery=true)
	Optional<Lista_amigos> findByIdUsuario(long id, long id_2);

}
