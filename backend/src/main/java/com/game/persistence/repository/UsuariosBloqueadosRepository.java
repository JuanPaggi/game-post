package com.game.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.game.persistence.models.UsuariosBloqueados;

/**
 * @author pachi
 * Repositorio de la tabla usuarios_bloqueados.
 */

@Repository
public interface UsuariosBloqueadosRepository extends JpaRepository <UsuariosBloqueados , Long> {

}
