package com.game.persistence.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author pachi
 * Modelo de la tabla requisitos.
 */

@Entity
public class Requisitos {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id_requisito;
	
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

	/*
	 * ------ Getter and Setter ------ 
	 */
	
	public long getId_requisitos() {
		return id_requisito;
	}

	public void setId_requisitos(long id_requisitos) {
		this.id_requisito = id_requisitos;
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
		return almacenamiento;
	}

	public void setAlamcenamiento(String alamcenamiento) {
		this.almacenamiento = alamcenamiento;
	}
	
}
