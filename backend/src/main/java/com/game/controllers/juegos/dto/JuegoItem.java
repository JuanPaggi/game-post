package com.game.controllers.juegos.dto;

import java.util.ArrayList;
import java.util.Date;

public class JuegoItem {

	public long id_juego;
	public String titulo;
	public String descripcion;
	public String genero;
	public String desarrollador;
	public Date fecha_lanzamiento;
	public long analisis_positivos;
	public long analisis_negativos;
	public long id_usuario_juego;
	public ArrayList<Long> tags;
	public ArrayList<Long> modos;
	public ArrayList<String> archivoImagen;
	public ArrayList<Long> analisis;
	public String sistema_operativo;
	public String procesador;
	public String memoria;
	public String grafica;
	public String almacenamiento;
	
}
