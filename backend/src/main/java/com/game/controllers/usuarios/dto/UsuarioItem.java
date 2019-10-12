package com.game.controllers.usuarios.dto;

import java.util.ArrayList;
import java.util.Date;

public class UsuarioItem {

	public long id_usuario;
	public String nombre;
	public String apellido;
	public String email;
	public String usuario;
	public String clave;
	public String pais;
	public Date fecha_inscripcion;
	public int nivel;
	public int puntos;
	public boolean email_verificado;
	
	public ArrayList<Long> privilegios;
	
}