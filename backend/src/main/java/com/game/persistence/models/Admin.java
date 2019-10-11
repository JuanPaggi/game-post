package com.game.persistence.models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@Entity
public class Admin {

	@Id
	// AutoIncremento del Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id_admin;
	
	@Column(nullable = false)
	private String usuario;
	
	@Column(nullable = false)
	private String clave;
	
	@OneToMany(fetch = FetchType.LAZY, orphanRemoval=true)
	@JoinColumn(name="id_admin_creado", referencedColumnName = "id_admin", nullable = false, insertable = false, updatable = false)
	private List<Juegos> juegos;

	public long getId_admin() {
		return id_admin;
	}

	public void setId_admin(long id_admin) {
		this.id_admin = id_admin;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}
	
}
