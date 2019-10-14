package com.game.persistence.models;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 * @author negro
 * Modelo de la tabla noticias.
 */

@Entity
public class Noticias {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id_noticia;
	
	@Column(nullable = false)
	private String titulo;
	
	@Column(nullable = false)
	private String descripcion;
	
	@Column(nullable = false)
	private String cuerpo;
	
	@Column(nullable = false)
	private Date fecha_publicacion;
	
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name="id_admin_creado", referencedColumnName = "id_admin")
	private Admin admin;
	
	@OneToMany(fetch = FetchType.LAZY, orphanRemoval=true)
	@JoinColumn(name="id_noticia", referencedColumnName = "id_noticia", nullable = false, insertable = false, updatable = false)
	private List<Comentarios> comentarios;
	
	/*
	 * ------ Getter and Setter ------ 
	 */
	
	public long getId_noticia() {
		return id_noticia;
	}

	public void setId_noticia(long id_noticia) {
		this.id_noticia = id_noticia;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getCuerpo() {
		return cuerpo;
	}

	public void setCuerpo(String cuerpo) {
		this.cuerpo = cuerpo;
	}

	public Date getFecha_publicacion() {
		return fecha_publicacion;
	}

	public void setFecha_publicacion(Date fecha_publicacion) {
		this.fecha_publicacion = fecha_publicacion;
	}

	public Admin getAdmin() {
		return admin;
	}

	public void setAdmin(Admin admin) {
		this.admin = admin;
	}

	public List<Comentarios> getComentarios() {
		return comentarios;
	}

	public void setComentarios(List<Comentarios> comentarios) {
		this.comentarios = comentarios;
	}
	
}
