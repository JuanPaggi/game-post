package com.game.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.game.persistence.models.Comentarios;

/**
 * @author negro
 * Repositorio de la tabla noticias.
 */

public interface ComentariosRepository  extends JpaRepository <Comentarios , Long> {

}
