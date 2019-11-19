package com.game.persistence.models;

import java.security.NoSuchAlgorithmException;
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
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.game.utils.Sha1Hasher;

@Entity
@Table(name = "imagenes")
public class Imagenes {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id_imagen;
	
	@Column(nullable = false)
	private String nombre;
	
	@Column(nullable = false)
	private byte[] imagen;
	
	@Column(nullable = false)
	private byte[] imagen_checksum;
	
	@Column(nullable = false)
	private Date fecha_subida;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="id_usuario_subido", referencedColumnName = "id_usuario")
	private Usuarios id_usuario_subido;
	
	@ManyToMany(fetch = FetchType.LAZY) 
    @JoinTable(name = "noticias_imagenes", 
    joinColumns = 
    @JoinColumn(name = "id_imagen", referencedColumnName = "id_imagen"), 
    inverseJoinColumns = 
    @JoinColumn(name = "id_noticia", referencedColumnName = "id_noticia"))
	private List<Noticias> noticias;
	
	@ManyToMany(fetch = FetchType.LAZY) 
    @JoinTable(name = "juegos_imagenes", 
    joinColumns = 
    @JoinColumn(name = "id_imagen", referencedColumnName = "id_imagen"), 
    inverseJoinColumns = 
    @JoinColumn(name = "id_juego", referencedColumnName = "id_juego"))
	private List<Juegos> juegos;
	
	/*
	 * ------ Getter and Setter ------ 
	 */

	public long getId_imagen() {
		return id_imagen;
	}

	public void setId_imagen(long id_imagen) {
		this.id_imagen = id_imagen;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public byte[] getImagen() {
		return imagen;
	}

	public void setImagen(byte[] imagen) throws NoSuchAlgorithmException {
		this.imagen_checksum = Sha1Hasher.hashBytes(imagen);
		this.imagen = imagen;
	}

	public byte[] getImagen_checksum() {
		return imagen_checksum;
	}

	public void setImagen_checksum(byte[] imagen_checksum) {
		this.imagen_checksum = imagen_checksum;
	}

	public Date getFecha_subida() {
		return fecha_subida;
	}

	public void setFecha_subida(Date fecha_subida) {
		this.fecha_subida = fecha_subida;
	}

	public Usuarios getId_usuario_subido() {
		return id_usuario_subido;
	}

	public void setId_usuario_subido(Usuarios id_usuario_subido) {
		this.id_usuario_subido = id_usuario_subido;
	}

	public List<Noticias> getNoticias() {
		return noticias;
	}

	public void setNoticias(List<Noticias> noticias) {
		this.noticias = noticias;
	}

	public List<Juegos> getJuegos() {
		return juegos;
	}

	public void setJuegos(List<Juegos> juegos) {
		this.juegos = juegos;
	}
	
}
