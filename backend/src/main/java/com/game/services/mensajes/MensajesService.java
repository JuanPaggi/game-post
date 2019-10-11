package com.game.services.mensajes;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.game.controllers.mensajes.dto.MensajeItem;
import com.game.persistence.models.Mensajes;
import com.game.persistence.models.Usuarios;
import com.game.persistence.repository.MensajesRepository;
import com.game.persistence.repository.UsuariosRepository;
import com.game.services.usuarios.exceptions.UsuariosNotFound;

/**
 * @author pachi
 *
 */

@Service
public class MensajesService {

	@Autowired
	UsuariosRepository usuariosrepository;
	
	@Autowired
	MensajesRepository mensajesRepository;
	
	public long addMensaje(MensajeItem mensajeIn) throws UsuariosNotFound{
		
		Optional<Usuarios> usuario = usuariosrepository.findById(mensajeIn.id_escritor);
		Optional<Usuarios> usuario_amigo = usuariosrepository.findById(mensajeIn.id_lector);
		Mensajes mensaje = new Mensajes();
		mensaje.setUsuario(usuario.get());
		mensaje.setUsuario_amigo(usuario_amigo.get());
		mensaje.setFecha(mensajeIn.fecha);
		mensaje.setCuerpo(mensajeIn.cuerpo);
		mensajesRepository.save(mensaje);
		return mensaje.getId_mensaje();
		
	}
	
	public List<MensajeItem> getAllMensajes(String id_usuario, String id_amigo) throws ParseException, UsuariosNotFound{
		
		List<Mensajes> mensajes = mensajesRepository.findByIdUsuario(Long.parseLong(id_usuario),Long.parseLong(id_amigo));
		List<MensajeItem> out = new ArrayList<MensajeItem>();
		if(mensajes.isEmpty()) { throw new UsuariosNotFound(); }
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
	
	public void removeMensaje(long id_usuario, long id_amigo, String mensajeIn) throws UsuariosNotFound, NumberFormatException {
		
		Optional<Mensajes> mensaje = mensajesRepository.findMensaje(id_usuario, id_amigo, mensajeIn);
		if(mensaje.isEmpty()) throw new UsuariosNotFound();
		mensajesRepository.delete(mensaje.get());
	
	}
}
