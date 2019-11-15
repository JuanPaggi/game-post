package com.game.controllers.noticias.dto;

import java.util.ArrayList;
import java.util.Date;

public class NoticiaItem {
	public long id_noticia;
	public String titulo;
	public String descripcion;
	public String cuerpo;
	public Date fecha_publicacion;
	public long id_usuario_noticia;
	public ArrayList<Long> tags;
	public ArrayList<Long> comentarios;
	public ArrayList<String> imagenes;

}
