package com.game.controllers.noticias.dto;

import java.util.ArrayList;
import java.util.Date;

public class NoticiaInput {
	
	public String titulo;
	
	public String descripcion;
	
	public String cuerpo;
	
	public Date fecha_publicacion;
	
	public String nombreImagen;
	
	public String urlImagen;
	
	public long id_admin_creado;
	
	public ArrayList<Long> tags;
	
	public ArrayList<Long> comentarios;

	public ArrayList<byte[]> archivoImagen;
}
