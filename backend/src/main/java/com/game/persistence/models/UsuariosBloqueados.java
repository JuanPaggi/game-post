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
 * Modelo de la tabla usuarios_bloqueados.
 */

@Entity
public class UsuariosBloqueados {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id_usuario_bloqueado;
	
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name="id_usuario_responsable", referencedColumnName = "id_usuario")
	private Usuarios id_usuario_responsable;
	
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name="id_usuario_baneado", referencedColumnName = "id_usuario")
	private Usuarios id_usuario_baneado;
	
	@Column(nullable = false)
	private Date fecha_bloqueo;
	
	@Column(nullable = false)
	private String motivo;

	/*
	 * ------ Getter and Setter ------ 
	 */
	
	public long getId_usuario_bloqueado() {
		return id_usuario_bloqueado;
	}

	public void setId_usuario_bloqueado(long id_usuario_bloqueado) {
		this.id_usuario_bloqueado = id_usuario_bloqueado;
	}

	public Usuarios getId_usuario_responsable() {
		return id_usuario_responsable;
	}

	public void setId_usuario_responsable(Usuarios id_usuario_responsable) {
		this.id_usuario_responsable = id_usuario_responsable;
	}

	public Usuarios getId_usuario_baneado() {
		return id_usuario_baneado;
	}

	public void setId_usuario_baneado(Usuarios id_usuario_baneado) {
		this.id_usuario_baneado = id_usuario_baneado;
	}

	public Date getFecha_bloqueo() {
		return fecha_bloqueo;
	}

	public void setFecha_bloqueo(Date fecha_bloqueo) {
		this.fecha_bloqueo = fecha_bloqueo;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}
	
}
