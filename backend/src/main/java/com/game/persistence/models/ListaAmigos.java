package com.game.persistence.models;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * @author pachi
 * Modelo de la tabla lista_amigos.
 */

@Entity
public class ListaAmigos {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id_lista_amigos;
	
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name="id_usuario", referencedColumnName = "id_usuario")
	private Usuarios usuario;
	
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name="id_amigo", referencedColumnName = "id_usuario")
	private Usuarios usuario_amigo;

	/*
	 * ------ Getter and Setter ------ 
	 */
	
	public long getId_lista_amigos() {
		return id_lista_amigos;
	}

	public void setId_lista_amigos(long id_lista_amigos) {
		this.id_lista_amigos = id_lista_amigos;
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
