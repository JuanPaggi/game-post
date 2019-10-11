package com.game.persistence.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * @author pachi
 * Modelo de la tabla analisis.
 */

@Entity
public class Analisis {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id_analisis;
	
	@Column(nullable = false)
	private String analisis;
	
	@Column(nullable = false)
	private boolean valoracion;
	
	@Column(nullable = false)
	private Date fecha_publicacion;
	
	@ManyToOne(optional = false)
	@JoinColumn(name="id_juego", referencedColumnName = "id_juego")
	private Juegos juego;
	
	@ManyToOne(optional = false)
	@JoinColumn(name="id_usuario", referencedColumnName = "id_usuario")
	private Usuarios usuario;

	/*
	 * ------ Getter and Setter ------ 
	 */
	
	public long getId_analisis() {
		return id_analisis;
	}

	public void setId_analisis(long id_analisis) {
		this.id_analisis = id_analisis;
	}

	public String getAnalisis() {
		return analisis;
	}

	public void setAnalisis(String analisis) {
		this.analisis = analisis;
	}

	public boolean isValoracion() {
		return valoracion;
	}

	public void setValoracion(boolean valoracion) {
		this.valoracion = valoracion;
	}

	public Date getFecha_publicacion() {
		return fecha_publicacion;
	}

	public void setFecha_publicacion(Date fecha_publicacion) {
		this.fecha_publicacion = fecha_publicacion;
	}

	public Juegos getJuego() {
		return juego;
	}

	public void setJuego(Juegos juego) {
		this.juego = juego;
	}

	public Usuarios getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuarios usuario) {
		this.usuario = usuario;
	}
	
}
