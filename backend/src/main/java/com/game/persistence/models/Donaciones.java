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
 * Modelo de la tabla donaciones.
 */

@Entity
public class Donaciones {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id_donacion;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name="id_usuario", referencedColumnName = "id_usuario")
	private Usuarios usuarios;
	
	@Column(nullable = false)
	private int monto;
	
	@Column(nullable = false)
	private Date fecha_donacion;
	
	@Column(nullable = false)
	private String comentario;

	/*
	 * ------ Getter and Setter ------ 
	 */
	
	public long getId_donacion() {
		return id_donacion;
	}

	public void setId_donacion(long id_donacion) {
		this.id_donacion = id_donacion;
	}

	public Usuarios getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(Usuarios usuarios) {
		this.usuarios = usuarios;
	}

	public int getMonto() {
		return monto;
	}

	public void setMonto(int monto) {
		this.monto = monto;
	}

	public Date getFecha_donacion() {
		return fecha_donacion;
	}

	public void setFecha_donacion(Date fecha_donacion) {
		this.fecha_donacion = fecha_donacion;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}
	
}
