package com.game.persistence.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * @author negro
 * Modelo de la tabla comentarios.
 */

@Entity
public class Comentarios {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id_comentario;
	
	@Column(nullable = false)
	private String comentario;
	
	@Column(nullable = false)
	private Date fecha_publicacion;
	
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name="id_usuario", referencedColumnName = "id_usuario")
	private Usuarios usuario;
	
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name="id_noticia", referencedColumnName = "id_noticia")
	private Noticias noticia;
	
	/*
	 * ------ Getter and Setter ------ 
	 */

	public long getId_comentario() {
		return id_comentario;
	}

	public void setId_comentario(long id_comentario) {
		this.id_comentario = id_comentario;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public Date getFecha_publicacion() {
		return fecha_publicacion;
	}

	public void setFecha_publicacion(Date fecha_publicacion) {
		this.fecha_publicacion = fecha_publicacion;
	}

	public Usuarios getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuarios usuario) {
		this.usuario = usuario;
	}

	public Noticias getNoticia() {
		return noticia;
	}

	public void setNoticia(Noticias noticia) {
		this.noticia = noticia;
	}
	
	
}
