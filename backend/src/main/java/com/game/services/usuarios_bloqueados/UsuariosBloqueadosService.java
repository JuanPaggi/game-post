package com.game.services.usuarios_bloqueados;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.game.controllers.usuarios_bloqueados.dto.UsuarioBloqueadoItem;
import com.game.persistence.models.Admin;
import com.game.persistence.models.Usuarios;
import com.game.persistence.models.UsuariosBloqueados;
import com.game.persistence.repository.AdminRepository;
import com.game.persistence.repository.UsuariosBloqueadosRepository;
import com.game.persistence.repository.UsuariosRepository;
import com.game.services.usuarios.exceptions.UsuariosNotFound;

/**
 * @author pachi
 *
 */

@Service
public class UsuariosBloqueadosService {

	@Autowired
	UsuariosBloqueadosRepository usuariosBloqueadosRepository;
	
	@Autowired
	UsuariosRepository usuariosRepository;
	
	@Autowired
	AdminRepository adminRepository;
	
	public List<UsuarioBloqueadoItem> getAllUsuarios() throws ParseException{
		
		List<UsuariosBloqueados> usuarios_bloqueados = usuariosBloqueadosRepository.findAll();
		List<UsuarioBloqueadoItem> out = new ArrayList<UsuarioBloqueadoItem>();
		for(UsuariosBloqueados usuario: usuarios_bloqueados) {
			UsuarioBloqueadoItem item = new UsuarioBloqueadoItem();
			item.id_usuario_bloqueado = usuario.getId_usuario_bloqueado();
			item.id_usuario = usuario.getUsuarios().getId_usuario();
			item.id_admin = usuario.getAdmin().getId_admin();
			item.fecha_bloqueo = usuario.getFecha_bloqueo();
			item.motivo = usuario.getMotivo();
			out.add(item);
		}
		return out;
		
	}
	
	public long addUsuarioBloqueado(UsuarioBloqueadoItem usuarioIn) throws UsuariosNotFound{
		
		UsuariosBloqueados usuario_bloqueado = new UsuariosBloqueados();
		Optional<Admin> admin = adminRepository.findById(usuarioIn.id_admin);
		Optional<Usuarios> usuario = usuariosRepository.findById(usuarioIn.id_usuario);
		if(admin.isEmpty() && usuario.isEmpty() ) throw new UsuariosNotFound();
		usuario_bloqueado.setId_usuario_bloqueado(usuarioIn.id_usuario_bloqueado);
		usuario_bloqueado.setUsuarios(usuario.get());
		usuario_bloqueado.setAdmin(admin.get());
		usuario_bloqueado.setFecha_bloqueo(usuarioIn.fecha_bloqueo);
		usuario_bloqueado.setMotivo(usuarioIn.motivo);
		usuario_bloqueado = usuariosBloqueadosRepository.save(usuario_bloqueado);
		return usuario_bloqueado.getId_usuario_bloqueado();
	}
	
	public void removeUsuarioBloqueado(String id) throws UsuariosNotFound, NumberFormatException {
		
		Optional<UsuariosBloqueados> usuario_bloqueado = usuariosBloqueadosRepository.findById(Long.parseLong(id));
		if(usuario_bloqueado.isEmpty()) throw new UsuariosNotFound();
		usuariosBloqueadosRepository.delete(usuario_bloqueado.get());
	
	}
	
}