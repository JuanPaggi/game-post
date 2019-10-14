package com.game.persistence.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * @author pachi
 * Modelo de la tabla notificaciones.
 */

@Entity
public class Notificaciones {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id_notificacion;
	
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name="id_usuario", referencedColumnName = "id_usuario")
	private Usuarios usuarios;
	
	@Column(nullable = false)
	private String notificacion;

	/*
	 * ------ Getter and Setter ------ 
	 */
	
	public long getId_notificacion() {
		return id_notificacion;
	}

	public void setId_notificacion(long id_notificacion) {
		this.id_notificacion = id_notificacion;
	}

	public Usuarios getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(Usuarios usuarios) {
		this.usuarios = usuarios;
	}

	public String getNotificacion() {
		return notificacion;
	}

	public void setNotificacion(String notificacion) {
		this.notificacion = notificacion;
	}	
	
}
