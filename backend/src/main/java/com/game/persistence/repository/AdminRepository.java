package com.game.persistence.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.game.persistence.models.Admin;

/**
 * @author pachi
 * Repositorio de la tabla admin.
 */

@Repository
public interface AdminRepository extends JpaRepository <Admin , Long> {

	// Consulta para traer un usuario por su user y clave
	@Query(value="select * from admin where usuario = ?1 and clave = ?2",nativeQuery=true)
	Optional<Admin> findByUser(String usuario, String clave);
}
