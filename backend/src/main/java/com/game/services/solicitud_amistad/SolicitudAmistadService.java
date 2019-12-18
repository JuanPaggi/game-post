package com.game.services.solicitud_amistad;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.game.controllers.solicitud_amistad.dto.SolicitudAmistadItem;
import com.game.exceptions.ApiException;
import com.game.persistence.models.SolicitudesAmistad;
import com.game.persistence.models.Usuarios;
import com.game.persistence.repository.SolicitudesAmistadRepository;
import com.game.persistence.repository.UsuariosRepository;

/**
 * @author Juan Paggi Logica de servicio para las solicitudes de amistad
 */

@Service
public class SolicitudAmistadService {

	@Autowired
	UsuariosRepository usuariosrepository;

	@Autowired
	SolicitudesAmistadRepository solicitudesAmistadRepository;

	public long addSolicitud(SolicitudAmistadItem solicitudIn) {

		try {
			Optional<SolicitudesAmistad> lista = solicitudesAmistadRepository.findByIdUsuario(solicitudIn.id_usuario,
					solicitudIn.id_amigo);
			if (!lista.isPresent()) {
				Optional<Usuarios> usuario = usuariosrepository.findById(solicitudIn.id_usuario);
				Optional<Usuarios> usuario_amigo = usuariosrepository.findById(solicitudIn.id_amigo);
				if (usuario.isPresent() && usuario_amigo.isPresent()) {
					SolicitudesAmistad lista_amigo = new SolicitudesAmistad();
					lista_amigo.setUsuario(usuario.get());
					lista_amigo.setUsuario_amigo(usuario_amigo.get());
					lista_amigo.setFecha_solicitud(solicitudIn.fecha_solicitud);
					solicitudesAmistadRepository.save(lista_amigo);
					return lista_amigo.getId_solicitud_amistad();
				} else {
					throw new ApiException(404, "No existe el usuario");
				}
			} else {
				throw new ApiException(409, "Ya existe una solicitud de amistad");
			}
		} catch (ApiException e) {
			throw e;
		} catch (Exception exception) {
			throw exception;
		}

	}

	public List<SolicitudAmistadItem> getAllSolicitudes(String id) {

		try {
			List<SolicitudesAmistad> lista_amigos = solicitudesAmistadRepository.findAmigosById(Long.parseLong(id));
			List<SolicitudAmistadItem> out = new ArrayList<SolicitudAmistadItem>();
			for (SolicitudesAmistad lista_amigo : lista_amigos) {
				SolicitudAmistadItem item = new SolicitudAmistadItem();
				item.id_usuario = lista_amigo.getUsuario().getId_usuario();
				item.id_amigo = lista_amigo.getUsuario_amigo().getId_usuario();
				item.fecha_solicitud = lista_amigo.getFecha_solicitud();
				out.add(item);
			}
			return out;
		} catch (ApiException e) {
			throw e;
		} catch (Exception exception) {
			throw exception;
		}

	}

	public void removeSolicitud(long id_usuario, long id_amigo) {

		try {
			Optional<SolicitudesAmistad> lista_amigo = solicitudesAmistadRepository.findByIdUsuario(id_usuario,
					id_amigo);
			if (lista_amigo.isPresent()) {
				solicitudesAmistadRepository.delete(lista_amigo.get());
			} else {
				throw new ApiException(404, "No existe la solicitud de amistad");
			}
		} catch (ApiException e) {
			throw e;
		} catch (Exception exception) {
			throw exception;
		}

	}

}
