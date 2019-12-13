package com.game.services.mensajes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.game.controllers.mensajes.dto.MensajeItem;
import com.game.exceptions.ApiException;
import com.game.persistence.models.Mensajes;
import com.game.persistence.models.Usuarios;
import com.game.persistence.repository.MensajesRepository;
import com.game.persistence.repository.UsuariosRepository;

/**
 * @author pachi
 * Logica de servicio para los mensajes
 */

@Service
public class MensajesService {

	@Autowired
	UsuariosRepository usuariosrepository;
	
	@Autowired
	MensajesRepository mensajesRepository;
	
	public long addMensaje(MensajeItem mensajeIn) {
		
		try {		
			Optional<Usuarios> usuario = usuariosrepository.findById(mensajeIn.id_escritor);
			Optional<Usuarios> usuario_amigo = usuariosrepository.findById(mensajeIn.id_lector);
			if(!usuario.isPresent()) {
				throw new ApiException(404, "No existe el usuario que envio el mensaje");
			}
			if(!usuario_amigo.isPresent()) {
				throw new ApiException(404, "No existe el usuario al que se le envio el mensaje");
			}
			Mensajes mensaje = new Mensajes();
			mensaje.setUsuario(usuario.get());
			mensaje.setUsuario_amigo(usuario_amigo.get());
			mensaje.setFecha(mensajeIn.fecha);
			mensaje.setCuerpo(mensajeIn.cuerpo);
			mensajesRepository.save(mensaje);
			return mensaje.getId_mensaje();
		} catch (ApiException e) {
			throw e;
		} catch (Exception exception) {
			throw exception; 
		}
		
	}
	
	public List<MensajeItem> getAllMensajes(String id_usuario, String id_amigo){
		
		List<Mensajes> mensajes = mensajesRepository.findByIdUsuario(Long.parseLong(id_usuario),Long.parseLong(id_amigo));
		List<MensajeItem> out = new ArrayList<MensajeItem>();
		for(Mensajes mensaje: mensajes) {
			MensajeItem item = new MensajeItem();
			item.id_mensaje = mensaje.getId_mensaje();
			item.id_escritor = mensaje.getUsuario().getId_usuario();
			item.id_lector = mensaje.getUsuario_amigo().getId_usuario();
			item.fecha = mensaje.getFecha();
			item.cuerpo = mensaje.getCuerpo();
			out.add(item);
		}
		return out;
		
	}
	
	public void removeMensaje(long id_usuario, long id_amigo, String mensajeIn) {
		
		try {
			Optional<Mensajes> mensaje = mensajesRepository.findMensaje(id_usuario, id_amigo, mensajeIn);
			if (mensaje.isPresent()) {
				mensajesRepository.delete(mensaje.get());
			}else {
				throw new ApiException(404, "No existe el mensaje");
			}
		} catch (ApiException e) {
			throw e;
		} catch (Exception exception) {
			throw exception; 
		}
	
	}
}
