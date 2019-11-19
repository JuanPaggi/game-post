package com.game.controllers.usuarios_bloqueados.dto;

import java.util.Date;

public class UsuarioBloqueadoItem {
	
	public long id_usuario_bloqueado;
	public long id_usuario_responsable;
	public long id_usuario_baneado;
	public Date fecha_bloqueo;
	public String motivo;
	
}
