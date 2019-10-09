package com.game.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.game.persistence.models.Usuarios;

@Repository
public interface UsuariosRepository extends JpaRepository<Usuarios , Long> {

}
