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
 * Modelo de la tabla mensajes.
 */

@Entity
public class Mensajes {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id_mensaje;
	
	@Column(nullable = false)
	private String cuerpo;
	
	@Column(nullable = false)
	private Date fecha;
	
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name="id_escritor", referencedColumnName = "id_usuario")
	private Usuarios usuario;
	
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name="id_lector", referencedColumnName = "id_usuario")
	private Usuarios usuario_amigo;

	/*
	 * ------ Getter and Setter ------ 
	 */
	
	public long getId_mensaje() {
		return id_mensaje;
	}

	public void setId_mensaje(long id_mensaje) {
		this.id_mensaje = id_mensaje;
	}

	public String getCuerpo() {
		return cuerpo;
	}

	public void setCuerpo(String cuerpo) {
		this.cuerpo = cuerpo;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
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
	
}
