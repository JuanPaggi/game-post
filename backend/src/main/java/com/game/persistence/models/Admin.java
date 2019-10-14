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
import javax.persistence.OneToMany;

/**
 * @author pachi
 * Modelo de la tabla admin.
 */

@Entity
public class Admin {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id_admin;
	
	@Column(nullable = false)
	private String usuario;
	
	@Column(nullable = false)
	private String clave;
	
	@OneToMany(fetch = FetchType.LAZY, orphanRemoval=true)
	@JoinColumn(name="id_admin_creado", referencedColumnName = "id_admin", nullable = false, insertable = false, updatable = false)
	private List<Juegos> juegos;
	
	@OneToMany(fetch = FetchType.LAZY, orphanRemoval=true)
	@JoinColumn(name="id_admin_creado", referencedColumnName = "id_admin", nullable = false, insertable = false, updatable = false)
	private List<Noticias> noticias;

	@ManyToMany(fetch = FetchType.LAZY) 
    @JoinTable(name = "privilegios_admin", 
    joinColumns = 
    @JoinColumn(name = "id_admin", referencedColumnName = "id_admin"), 
    inverseJoinColumns = 
    @JoinColumn(name = "id_privilegio", referencedColumnName = "id_privilegio"))
	private List<Privilegios> privilegios;
	
	@OneToMany(fetch = FetchType.LAZY, orphanRemoval=true)
	@JoinColumn(name="id_admin", referencedColumnName = "id_admin", nullable = false, insertable = false, updatable = false)
	private List<UsuariosBloqueados> usuarios_bloqueados;
	
	/*
	 * ------ Getter and Setter ------ 
	 */
	
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

	public List<Juegos> getJuegos() {
		return juegos;
	}

	public void setJuegos(List<Juegos> juegos) {
		this.juegos = juegos;
	}

	public List<Privilegios> getPrivilegios() {
		return privilegios;
	}

	public void setPrivilegios(List<Privilegios> privilegios) {
		this.privilegios = privilegios;
	}

	public List<UsuariosBloqueados> getUsuarios_bloqueados() {
		return usuarios_bloqueados;
	}

	public void setUsuarios_bloqueados(List<UsuariosBloqueados> usuarios_bloqueados) {
		this.usuarios_bloqueados = usuarios_bloqueados;
	}

	public List<Noticias> getNoticias() {
		return noticias;
	}

	public void setNoticias(List<Noticias> noticias) {
		this.noticias = noticias;
	}
	
	
	
}
