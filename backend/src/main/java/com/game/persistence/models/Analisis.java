package com.game.persistence.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.search.annotations.Indexed;

@Entity
@Indexed
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
	
	@Column(nullable = false)
	private long id_usuario;

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

	public long getId_usuario() {
		return id_usuario;
	}

	public void setId_usuario(long id_usuario) {
		this.id_usuario = id_usuario;
	}
	
}
