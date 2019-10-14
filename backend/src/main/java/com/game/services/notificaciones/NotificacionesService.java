package com.game.services.notificaciones;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.game.controllers.notificaciones.dto.NotificacionItem;
import com.game.persistence.models.Notificaciones;
import com.game.persistence.models.Usuarios;
import com.game.persistence.repository.NotificacionesRepository;
import com.game.persistence.repository.UsuariosRepository;
import com.game.services.usuarios.exceptions.UsuariosNotFound;

/**
 * @author pachi
 *
 */

@Service
public class NotificacionesService {

	@Autowired
	UsuariosRepository usuariosrepository;
	
	@Autowired
	NotificacionesRepository notificacionesRepository;
	
	public List<NotificacionItem> getNotificaciones(long id) throws UsuariosNotFound {
		
		List<Notificaciones> notificaciones = notificacionesRepository.findByIdNotificaciones(id);
		if(notificaciones.isEmpty())  throw new UsuariosNotFound();
		List<NotificacionItem> out = new ArrayList<NotificacionItem>();
		for(Notificaciones notificacion: notificaciones) {
			NotificacionItem item = new NotificacionItem();
			item.id_notificacion = notificacion.getId_notificacion();
			item.id_usuario = notificacion.getUsuarios().getId_usuario();
			item.notificacion = notificacion.getNotificacion();
			out.add(item);
		}
		return out;
		
	}
	
	public long addNotificacion(NotificacionItem notificacionIn) {
		
		Optional<Usuarios> usuario = usuariosrepository.findById(notificacionIn.id_usuario);
		
		Notificaciones notificacion = new Notificaciones();
		notificacion.setUsuarios(usuario.get());
		notificacion.setNotificacion(notificacionIn.notificacion);
		notificacionesRepository.save(notificacion);
		return notificacion.getId_notificacion();
		
	}
	
	public void removeNotificacion(String id) throws UsuariosNotFound, NumberFormatException {
		
		Optional<Notificaciones> notificacion = notificacionesRepository.findById(Long.parseLong(id));
		if(notificacion.isEmpty()) throw new UsuariosNotFound();
		notificacionesRepository.delete(notificacion.get());
	
	}
	
}
