package com.game.persistence.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.search.annotations.Indexed;


@Entity
@Indexed
public class Requisitos {
	
	@Id
	// AutoIncremento del Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id_requisitos;
	
	@Column(nullable = false)
	private String sistema_operativo;
	
	@Column(nullable = false)
	private String procesador;
	
	@Column(nullable = false)
	private String memoria;
	
	@Column(nullable = false)
	private String grafica;
	
	@Column(nullable = false)
	private String alamcenamiento;

	/*
	 * ------ Getter and Setter ------ 
	 */
	
	public long getId_requisitos() {
		return id_requisitos;
	}

	public void setId_requisitos(long id_requisitos) {
		this.id_requisitos = id_requisitos;
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

	public String getAlamcenamiento() {
		return alamcenamiento;
	}

	public void setAlamcenamiento(String alamcenamiento) {
		this.alamcenamiento = alamcenamiento;
	}
	
}
