package com.game.persistence.models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

/**
 * @author pachi
 * Modelo de la tabla privilegios.
 */

@Entity
public class Privilegios {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id_privilegio;
	
	@Column(nullable = false)
	private String privilegio;
	
	@ManyToMany(fetch = FetchType.LAZY) 
    @JoinTable(name = "usuarios_privilegios", 
    joinColumns = 
    @JoinColumn(name = "id_privilegio", referencedColumnName = "id_privilegio"), 
    inverseJoinColumns = 
    @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario"))
	private List<Usuarios> usuarios;
	
	@ManyToMany(fetch = FetchType.LAZY) 
    @JoinTable(name = "privilegios_admin", 
    joinColumns = 
    @JoinColumn(name = "id_privilegio", referencedColumnName = "id_privilegio"), 
    inverseJoinColumns = 
    @JoinColumn(name = "id_admin", referencedColumnName = "id_admin"))
	private List<Admin> admin;

	/*
	 * ------ Getter and Setter ------ 
	 */
	
	public long getId_privilegio() {
		return id_privilegio;
	}

	public void setId_privilegio(long id_privilegio) {
		this.id_privilegio = id_privilegio;
	}

	public String getPrivilegio() {
		return privilegio;
	}

	public void setPrivilegio(String privilegio) {
		this.privilegio = privilegio;
	}

	public List<Usuarios> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuarios> usuarios) {
		this.usuarios = usuarios;
	}

	public List<Admin> getAdmin() {
		return admin;
	}

	public void setAdmin(List<Admin> admin) {
		this.admin = admin;
	}
	
}
