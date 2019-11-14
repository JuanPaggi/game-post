package com.game.persistence.models;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 * @author pachi
 * Modelo de la tabla juegos.
 */

@Entity
public class Juegos {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id_juego;
	
	@Column(nullable = false)
	private String titulo;
	
	@Column(nullable = false)
	private String descripcion;
	
	@Column(nullable = false)
	private String genero;
	
	@Column(nullable = false)
	private String desarrollador;
	
	@Column(nullable = false)
	private String sistema_operativo;
	
	@Column(nullable = false)
	private String procesador;
	
	@Column(nullable = false)
	private String memoria;
	
	@Column(nullable = false)
	private String grafica;
	
	@Column(nullable = false)
	private String almacenamiento;
	
	@Column(nullable = false)
	private Date fecha_lanzamiento;
	
	@Column(nullable = false)
	private long analisis_positivos;
	
	@Column(nullable = false)
	private long analisis_negativos;
	
	@OneToMany(fetch = FetchType.LAZY, orphanRemoval=true)
	@JoinColumn(name="id_juego", referencedColumnName = "id_juego", nullable = false, insertable = false, updatable = false)
	private List<Analisis> analisis;
	
	@ManyToMany(fetch = FetchType.LAZY) 
    @JoinTable(name = "juegos_tag", 
    joinColumns = 
    @JoinColumn(name = "id_juego", referencedColumnName = "id_juego"), 
    inverseJoinColumns = 
    @JoinColumn(name = "id_tag", referencedColumnName = "id_tag"))
	private List<Tag> Tag;
	
	@ManyToOne(optional = false)
	@JoinColumn(name="id_admin_creado", referencedColumnName = "id_admin")
	private Admin admin;
	
	@ManyToMany(fetch = FetchType.LAZY) 
    @JoinTable(name = "juegos_modos", 
    joinColumns = 
    @JoinColumn(name = "id_juego", referencedColumnName = "id_juego"), 
    inverseJoinColumns = 
    @JoinColumn(name = "id_modo", referencedColumnName = "id_modo"))
	private List<Modos> modos;
	
	@ManyToMany(fetch = FetchType.LAZY) 
    @JoinTable(name = "juegos_imagenes", 
    joinColumns = 
    @JoinColumn(name = "id_juego", referencedColumnName = "id_juego"), 
    inverseJoinColumns = 
    @JoinColumn(name = "id_imagen", referencedColumnName = "id_imagen"))
	private Set<Imagenes> imagenes;
	
	/*
	 * ------ Getter and Setter ------ 
	 */

	public long getId_juego() {
		return id_juego;
	}

	public void setId_juego(long id_noticia) {
		this.id_juego = id_noticia;
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

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public String getDesarrollador() {
		return desarrollador;
	}

	public void setDesarrollador(String desarrollador) {
		this.desarrollador = desarrollador;
	}

	public Date getFecha_lanzamiento() {
		return fecha_lanzamiento;
	}

	public void setFecha_lanzamiento(Date fecha_lanzamiento) {
		this.fecha_lanzamiento = fecha_lanzamiento;
	}

	public long getAnalisis_positivos() {
		return analisis_positivos;
	}

	public void setAnalisis_positivos(long analisis_positivos) {
		this.analisis_positivos = analisis_positivos;
	}

	public long getAnalisis_negativos() {
		return analisis_negativos;
	}

	public void setAnalisis_negativos(long analisis_negativos) {
		this.analisis_negativos = analisis_negativos;
	}

	public List<Analisis> getAnalisis() {
		return analisis;
	}

	public void setAnalisis(List<Analisis> analisis) {
		this.analisis = analisis;
	}

	public List<Tag> getTag() {
		return Tag;
	}

	public void setTag(List<Tag> tag) {
		Tag = tag;
	}

	public Admin getAdmin() {
		return admin;
	}

	public void setAdmin(Admin admin) {
		this.admin = admin;
	}

	public List<Modos> getModos() {
		return modos;
	}

	public void setModos(List<Modos> modos) {
		this.modos = modos;
	}

	public Set<Imagenes> getImagenes() {
		return imagenes;
	}

	public void setImagenes(Set<Imagenes> imagenes) {
		this.imagenes = imagenes;
	}

	public String getSistema_operativo() {
		return sistema_operativo;
	}

	public void setSistema_operativo(String sistema_operativo) {
		this.sistema_operativo = sistema_operativo;
	}

	public String getProcesador() {
		return procesador;
	}

	public void setProcesador(String procesador) {
		this.procesador = procesador;
	}

	public String getMemoria() {
		return memoria;
	}

	public void setMemoria(String memoria) {
		this.memoria = memoria;
	}

	public String getGrafica() {
		return grafica;
	}

	public void setGrafica(String grafica) {
		this.grafica = grafica;
	}

	public String getAlmacenamiento() {
		return almacenamiento;
	}

	public void setAlmacenamiento(String almacenamiento) {
		this.almacenamiento = almacenamiento;
	}
	
}
