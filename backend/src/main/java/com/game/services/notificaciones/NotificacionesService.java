package com.game.services.notificaciones;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.game.controllers.notificaciones.dto.NotificacionItem;
import com.game.exceptions.ApiException;
import com.game.persistence.models.Notificaciones;
import com.game.persistence.models.Usuarios;
import com.game.persistence.repository.NotificacionesRepository;
import com.game.persistence.repository.UsuariosRepository;

/**
 * @author Juan Paggi Logica de servicio para las notificaciones
 */

@Service
public class NotificacionesService {

	@Autowired
	UsuariosRepository usuariosrepository;

	@Autowired
	NotificacionesRepository notificacionesRepository;

	public List<NotificacionItem> getNotificaciones(long id) {

		try {
			List<Notificaciones> notificaciones = notificacionesRepository.findByIdNotificaciones(id);
			List<NotificacionItem> out = new ArrayList<NotificacionItem>();
			for (Notificaciones notificacion : notificaciones) {
				NotificacionItem item = new NotificacionItem();
				item.id_notificacion = notificacion.getId_notificacion();
				item.id_usuario = notificacion.getUsuarios().getId_usuario();
				item.notificacion = notificacion.getNotificacion();
				out.add(item);
			}
			return out;
		} catch (ApiException e) {
			throw e;
		} catch (Exception exception) {
			throw exception;
		}

	}

	public long addNotificacion(NotificacionItem notificacionIn) {

		try {
			Optional<Usuarios> usuario = usuariosrepository.findById(notificacionIn.id_usuario);
			if (usuario.isPresent()) {
				Notificaciones notificacion = new Notificaciones();
				notificacion.setUsuarios(usuario.get());
				notificacion.setNotificacion(notificacionIn.notificacion);
				notificacionesRepository.save(notificacion);
				return notificacion.getId_notificacion();
			} else {
				throw new ApiException(404, "No existe el usuario");
			}
		} catch (ApiException e) {
			throw e;
		} catch (Exception exception) {
			throw exception;
		}

	}

	public void removeNotificacion(String id) {

		try {
			Optional<Notificaciones> notificacion = notificacionesRepository.findById(Long.parseLong(id));
			if (notificacion.isPresent()) {
				notificacionesRepository.delete(notificacion.get());
			} else {
				throw new ApiException(404, "No existe el analisis");
			}
		} catch (ApiException e) {
			throw e;
		} catch (Exception exception) {
			throw exception;
		}

	}

}
