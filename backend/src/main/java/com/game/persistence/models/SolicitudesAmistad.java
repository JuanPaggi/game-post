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
 * @author pachi
 * Modelo de la tabla requisitos.
 */

@Entity
public class SolicitudesAmistad{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id_solicitud_amistad;
	
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name="id_usuario", referencedColumnName = "id_usuario")
	private Usuarios usuario;
	
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name="id_amigo", referencedColumnName = "id_usuario")
	private Usuarios usuario_amigo;
	
	@Column(nullable = false)
	private Date fecha_solicitud;

	/*
	 * ------ Getter and Setter ------ 
	 */
	
	public long getId_solicitud_amistad() {
		return id_solicitud_amistad;
	}

	public void setId_solicitud_amistad(long id_solicitud_amistad) {
		this.id_solicitud_amistad = id_solicitud_amistad;
	}

	public Usuarios getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuarios usuario) {
		this.usuario = usuario;
	}

	public Usuarios getUsuario_amigo() {
		return usuario_amigo;
	}

	public void setUsuario_amigo(Usuarios usuario_amigo) {
		this.usuario_amigo = usuario_amigo;
	}

	public Date getFecha_solicitud() {
		return fecha_solicitud;
	}

	public void setFecha_solicitud(Date fecha_solicitud2) {
		this.fecha_solicitud = fecha_solicitud2;
	}
	
}
