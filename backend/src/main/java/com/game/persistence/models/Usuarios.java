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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

/**
 * @author pachi
 * Modelo de la tabla usuarios.
 */

@Entity
public class Usuarios {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id_usuario;
	
	@Column(nullable = false)
	private String nombre;
	
	@Column(nullable = false)
	private String apellido;
	
	@Column(nullable = false)
	private String email;
	
	@Column(nullable = false)
	private String usuario;
	
	@Column(nullable = false)
	private String clave;
	
	@Column(nullable = false)
	private String pais;
	
	@Column(nullable = false)
	private int nivel;
	
	@Column(nullable = false)
	private int puntos;
	
	@Column(nullable = false)
	private Date fecha_inscripcion;
	
	@Column(nullable = false)
	private boolean email_verificado;
	
	@OneToMany(fetch = FetchType.LAZY, orphanRemoval=true)
	@JoinColumn(name="id_usuario", referencedColumnName = "id_usuario", nullable = false, insertable = false, updatable = false)
	private List<Analisis> analisis;
	
	@OneToMany(fetch = FetchType.LAZY, orphanRemoval=true)
	@JoinColumn(name="id_usuario", referencedColumnName = "id_usuario", nullable = false, insertable = false, updatable = false)
	private List<Comentarios> comentarios;
	
	@OneToMany(fetch = FetchType.LAZY, orphanRemoval=true)
	@JoinColumn(name="id_usuario", referencedColumnName = "id_usuario", nullable = false, insertable = false, updatable = false)
	private List<Lista_amigos> usuarios;
	
	@OneToMany(fetch = FetchType.LAZY, orphanRemoval=true)
	@JoinColumn(name="id_amigo", referencedColumnName = "id_usuario", nullable = false, insertable = false, updatable = false)
	private List<Lista_amigos> usuarios_amigo;
	
	@OneToMany(fetch = FetchType.LAZY, orphanRemoval=true)
	@JoinColumn(name="id_usuario", referencedColumnName = "id_usuario", nullable = false, insertable = false, updatable = false)
	private List<SolicitudesAmistad> usuarios_solicitud;
	
	@OneToMany(fetch = FetchType.LAZY, orphanRemoval=true)
	@JoinColumn(name="id_amigo", referencedColumnName = "id_usuario", nullable = false, insertable = false, updatable = false)
	private List<SolicitudesAmistad> usuarios_amigo_solicitud;
	
	@OneToMany(fetch = FetchType.LAZY, orphanRemoval=true)
	@JoinColumn(name="id_escritor", referencedColumnName = "id_usuario", nullable = false, insertable = false, updatable = false)
	private List<Mensajes> usuarios_mensajes;
	
	@OneToMany(fetch = FetchType.LAZY, orphanRemoval=true)
	@JoinColumn(name="id_lector", referencedColumnName = "id_usuario", nullable = false, insertable = false, updatable = false)
	private List<Mensajes> usuarios_amigo_mensajes;
	
	@OneToMany(fetch = FetchType.LAZY, orphanRemoval=true)
	@JoinColumn(name="id_usuario", referencedColumnName = "id_usuario", nullable = false, insertable = false, updatable = false)
	private List<Donaciones> donaciones;
	
	@OneToMany(fetch = FetchType.LAZY, orphanRemoval=true)
	@JoinColumn(name="id_usuario", referencedColumnName = "id_usuario", nullable = false, insertable = false, updatable = false)
	private List<Notificaciones> notificaciones;
	
	@ManyToMany(fetch = FetchType.LAZY) 
    @JoinTable(name = "usuarios_privilegios", 
    joinColumns = 
    @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario"), 
    inverseJoinColumns = 
    @JoinColumn(name = "id_privilegio", referencedColumnName = "id_privilegio"))
	private List<Privilegios> privilegios;
	
	@OneToMany(fetch = FetchType.LAZY, orphanRemoval=true)
	@JoinColumn(name="id_usuario", referencedColumnName = "id_usuario", nullable = false, insertable = false, updatable = false)
	private List<UsuariosBloqueados> usuarios_bloqueados;

	/*
	 * ------ Getter and Setter ------ 
	 */
	
	public long getId_usuario() {
		return id_usuario;
	}

	public void setId_usuario(long id_usuario) {
		this.id_usuario = id_usuario;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public int getNivel() {
		return nivel;
	}

	public void setNivel(int nivel) {
		this.nivel = nivel;
	}

	public int getPuntos() {
		return puntos;
	}

	public void setPuntos(int puntos) {
		this.puntos = puntos;
	}

	public Date getFecha_inscripcion() {
		return fecha_inscripcion;
	}

	public void setFecha_inscripcion(Date fecha_inscripcion) {
		this.fecha_inscripcion = fecha_inscripcion;
	}

	public boolean isEmail_verificado() {
		return email_verificado;
	}

	public void setEmail_verificado(boolean email_verificado) {
		this.email_verificado = email_verificado;
	}

	public List<Analisis> getAnalisis() {
		return analisis;
	}

	public void setAnalisis(List<Analisis> analisis) {
		this.analisis = analisis;
	}

	public List<Lista_amigos> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Lista_amigos> usuarios) {
		this.usuarios = usuarios;
	}

	public List<Lista_amigos> getUsuarios_amigo() {
		return usuarios_amigo;
	}

	public void setUsuarios_amigo(List<Lista_amigos> usuarios_amigo) {
		this.usuarios_amigo = usuarios_amigo;
	}

	public List<SolicitudesAmistad> getUsuarios_solicitud() {
		return usuarios_solicitud;
	}

	public void setUsuarios_solicitud(List<SolicitudesAmistad> usuarios_solicitud) {
		this.usuarios_solicitud = usuarios_solicitud;
	}

	public List<SolicitudesAmistad> getUsuarios_amigo_solicitud() {
		return usuarios_amigo_solicitud;
	}

	public void setUsuarios_amigo_solicitud(List<SolicitudesAmistad> usuarios_amigo_solicitud) {
		this.usuarios_amigo_solicitud = usuarios_amigo_solicitud;
	}

	public List<Mensajes> getUsuarios_mensajes() {
		return usuarios_mensajes;
	}

	public void setUsuarios_mensajes(List<Mensajes> usuarios_mensajes) {
		this.usuarios_mensajes = usuarios_mensajes;
	}

	public List<Mensajes> getUsuarios_amigo_mensajes() {
		return usuarios_amigo_mensajes;
	}

	public void setUsuarios_amigo_mensajes(List<Mensajes> usuarios_amigo_mensajes) {
		this.usuarios_amigo_mensajes = usuarios_amigo_mensajes;
	}

	public List<Donaciones> getDonaciones() {
		return donaciones;
	}

	public void setDonaciones(List<Donaciones> donaciones) {
		this.donaciones = donaciones;
	}

	public List<Notificaciones> getNotificaciones() {
		return notificaciones;
	}

	public void setNotificaciones(List<Notificaciones> notificaciones) {
		this.notificaciones = notificaciones;
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

	public List<Comentarios> getComentarios() {
		return comentarios;
	}

	public void setComentarios(List<Comentarios> comentarios) {
		this.comentarios = comentarios;
	}
	
}
