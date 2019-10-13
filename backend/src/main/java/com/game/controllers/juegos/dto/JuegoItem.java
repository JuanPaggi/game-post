package com.game.controllers.juegos.dto;

import java.util.ArrayList;
import java.util.Date;

public class JuegoItem {

	public long id_juego;
	public String titulo;
	public String descripcion;
	public String genero;
	public String tipo;
	public String desarrollador;
	public Date fecha_lanzamiento;
	public long analisis_positivos;
	public long analisis_negativos;
	public long id_requisitos;
	public long id_admin_creado;
	public ArrayList<Long> tags;
	public ArrayList<Long> modos;
	
}
