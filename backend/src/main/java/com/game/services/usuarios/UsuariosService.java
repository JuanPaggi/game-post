package com.game.services.usuarios;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.game.controllers.usuarios.dto.UsuarioItem;
import com.game.persistence.models.Privilegios;
import com.game.persistence.models.Usuarios;
import com.game.persistence.repository.Lista_amigosRepository;
import com.game.persistence.repository.PrivilegiosRepository;
import com.game.persistence.repository.UsuariosRepository;
import com.game.services.privilegios.exceptions.PrivilegioNotFound;
import com.game.services.usuarios.exceptions.UsuariosNotFound;

/**
 * @author pachi
 *
 */

@Service
public class UsuariosService {

	@Autowired
	UsuariosRepository usuariosrepository;
	
	@Autowired
	Lista_amigosRepository lista_amigosRepository;
	
	@Autowired
	PrivilegiosRepository privilegiosRepository;
	
	public UsuarioItem getUsuario(long id) throws UsuariosNotFound {
		
		Optional<Usuarios> usuario = usuariosrepository.findById(id);
		UsuarioItem usuarioItem = new UsuarioItem();
		if(usuario.isEmpty()) throw new UsuariosNotFound();
		usuarioItem.id_usuario = usuario.get().getId_usuario();
		usuarioItem.nombre = usuario.get().getNombre();
		usuarioItem.apellido = usuario.get().getApellido();
		usuarioItem.email = usuario.get().getEmail();
		usuarioItem.usuario = usuario.get().getUsuario();
		usuarioItem.clave = usuario.get().getClave();
		usuarioItem.pais = usuario.get().getPais();
		usuarioItem.nivel = usuario.get().getNivel();
		usuarioItem.puntos = usuario.get().getPuntos();
		usuarioItem.fecha_inscripcion = usuario.get().getFecha_inscripcion();
		usuarioItem.email_verificado = usuario.get().isEmail_verificado();
		
		List<Privilegios> privilegio_usuario = usuario.get().getPrivilegios();
		ArrayList<Long> privilegio_id = new ArrayList<>();
		for (Privilegios privilegio : privilegio_usuario) {
			privilegio_id.add(privilegio.getId_privilegio());
		}
		usuarioItem.privilegios = privilegio_id;
		
		return usuarioItem;
		
	}
	
	public List<UsuarioItem> getAllUsuarios() throws ParseException{
		
		List<Usuarios> usuarios = usuariosrepository.findAll();
		List<UsuarioItem> out = new ArrayList<UsuarioItem>();
		for(Usuarios usuario: usuarios) {
			UsuarioItem item = new UsuarioItem();
			item.id_usuario = usuario.getId_usuario();
			item.nombre = usuario.getNombre();
			item.apellido = usuario.getApellido();
			item.email = usuario.getEmail();
			item.usuario = usuario.getUsuario();
			item.clave = usuario.getClave();
			item.pais = usuario.getPais();
			item.nivel = usuario.getNivel();
			item.puntos = usuario.getPuntos();
			item.fecha_inscripcion = usuario.getFecha_inscripcion();
			item.email_verificado = usuario.isEmail_verificado();
			
			List<Privilegios> privilegio_usuario = usuario.getPrivilegios();
			ArrayList<Long> privilegio_id = new ArrayList<>();
			for (Privilegios privilegio : privilegio_usuario) {
				privilegio_id.add(privilegio.getId_privilegio());
			}
			item.privilegios = privilegio_id;
			
			out.add(item);
		}
		return out;
		
	}
	
	public long addUsuario(UsuarioItem usuarioIn) throws PrivilegioNotFound{
		
		Usuarios usuario = new Usuarios();
		List<Privilegios> lista_privilegios= privilegiosRepository.findAllById(usuarioIn.privilegios);
		if(lista_privilegios.size() != usuarioIn.privilegios.size()) throw new PrivilegioNotFound();
		usuario.setNombre(usuarioIn.nombre);
		usuario.setApellido(usuarioIn.apellido);
		usuario.setEmail(usuarioIn.email);
		usuario.setUsuario(usuarioIn.usuario);
		usuario.setClave(usuarioIn.clave);
		usuario.setPais(usuarioIn.pais);
		usuario.setNivel(usuarioIn.nivel);
		usuario.setPuntos(usuarioIn.puntos);
		usuario.setFecha_inscripcion(usuarioIn.fecha_inscripcion);
		usuario.setEmail_verificado(usuarioIn.email_verificado);
		usuario.setPrivilegios(lista_privilegios);
		usuario = usuariosrepository.save(usuario);
		return usuario.getId_usuario();
		
	}
	
	public void removeUsuario(String id) throws UsuariosNotFound, NumberFormatException {
		
		Optional<Usuarios> usuario = usuariosrepository.findById(Long.parseLong(id));
		if(usuario.isEmpty()) throw new UsuariosNotFound();
		usuariosrepository.delete(usuario.get());
	
	}
	
	public void editUsuario(String id, UsuarioItem usuarioIn) throws UsuariosNotFound,PrivilegioNotFound, NumberFormatException{
		
		Optional<Usuarios> usuario = usuariosrepository.findById(Long.parseLong(id));
		if(usuario.isEmpty()) throw new UsuariosNotFound();
		Usuarios usuarioObj = usuario.get();
		usuarioObj.setNombre(usuarioIn.nombre);
		usuarioObj.setApellido(usuarioIn.apellido);
		usuarioObj.setEmail(usuarioIn.email);
		usuarioObj.setUsuario(usuarioIn.usuario);
		usuarioObj.setClave(usuarioIn.clave);
		usuarioObj.setPais(usuarioIn.pais);
		usuarioObj.setNivel(usuarioIn.nivel);
		usuarioObj.setPuntos(usuarioIn.puntos);
		usuarioObj.setEmail_verificado(usuarioIn.email_verificado);
		List<Privilegios> lista_privilegios= privilegiosRepository.findAllById(usuarioIn.privilegios);
		if(lista_privilegios.size() != usuarioIn.privilegios.size()) throw new PrivilegioNotFound();
		usuarioObj.setPrivilegios(lista_privilegios);
		usuariosrepository.save(usuarioObj);
		
	}
	
}
