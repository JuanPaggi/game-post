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

@Entity
public class Tag {

	@Id
	// AutoIncremento del Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id_tag;
	
	@Column(nullable = false)
	private String etiqueta;
	
	@ManyToMany(fetch = FetchType.LAZY) 
    @JoinTable(name = "juegos_tag", 
    joinColumns = 
    @JoinColumn(name = "id_tag", referencedColumnName = "id_tag"), 
    inverseJoinColumns = 
    @JoinColumn(name = "id_juego", referencedColumnName = "id_juego"))
	private List<Juegos> juegos;

	public long getId_tag() {
		return id_tag;
	}

	public void setId_tag(long id_tag) {
		this.id_tag = id_tag;
	}

	public String getEtiqueta() {
		return etiqueta;
	}

	public void setEtiqueta(String etiqueta) {
		this.etiqueta = etiqueta;
	}
	
}
