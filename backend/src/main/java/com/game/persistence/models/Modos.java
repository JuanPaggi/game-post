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
 * @author negro
 * Modelo de la tabla modos.
 */

@Entity
public class Modos {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id_modo;
	
	@Column(nullable = false)
	private String modo;
	
	@ManyToMany(fetch = FetchType.LAZY) 
    @JoinTable(name = "juegos_modos", 
    joinColumns = 
    @JoinColumn(name = "id_modo", referencedColumnName = "id_modo"), 
    inverseJoinColumns = 
    @JoinColumn(name = "id_juego", referencedColumnName = "id_juego"))
	private List<Juegos> juegos;
	
	/*
	 * ------ Getter and Setter ------ 
	 */

	public long getId_modo() {
		return id_modo;
	}

	public void setId_modo(long id_modo) {
		this.id_modo = id_modo;
	}

	public String getModo() {
		return modo;
	}

	public void setModo(String modo) {
		this.modo = modo;
	}
	
}
