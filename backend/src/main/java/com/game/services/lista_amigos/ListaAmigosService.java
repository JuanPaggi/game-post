package com.game.services.lista_amigos;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.game.controllers.lista_amigos.dto.ListaAmigosItem;
import com.game.persistence.models.ListaAmigos;
import com.game.persistence.models.Usuarios;
import com.game.persistence.repository.Lista_amigosRepository;
import com.game.persistence.repository.UsuariosRepository;
import com.game.services.usuarios.exceptions.UsuariosNotFound;

/**
 * @author pachi
 *
 */

@Service
public class ListaAmigosService {

	@Autowired
	UsuariosRepository usuariosrepository;
	
	@Autowired
	Lista_amigosRepository lista_amigosRepository;
	
	public long addAmigo(ListaAmigosItem amigoIn) throws UsuariosNotFound{
		
		Optional<ListaAmigos> lista = lista_amigosRepository.findByIdUsuario(amigoIn.id_usuario, amigoIn.id_amigo);
		if(lista.isEmpty()) {
			Optional<Usuarios> usuario = usuariosrepository.findById(amigoIn.id_usuario);
			Optional<Usuarios> usuario_amigo = usuariosrepository.findById(amigoIn.id_amigo);
			ListaAmigos lista_amigo = new ListaAmigos();
			lista_amigo.setUsuario(usuario.get());
			lista_amigo.setUsuario_amigo(usuario_amigo.get());
			lista_amigosRepository.save(lista_amigo);
			return lista_amigo.getId_lista_amigos();
		} else {
			throw new UsuariosNotFound();
		}
		
	}
	
	public List<ListaAmigosItem> getAllAmigos(String id) throws ParseException, UsuariosNotFound{
		
		List<ListaAmigos> listaAmigos = lista_amigosRepository.findAmigosById(Long.parseLong(id));
		List<ListaAmigosItem> out = new ArrayList<ListaAmigosItem>();
		if(listaAmigos.isEmpty()) { throw new UsuariosNotFound(); }
		for(ListaAmigos lista_amigo: listaAmigos) {
			ListaAmigosItem item = new ListaAmigosItem();
			item.id_usuario = lista_amigo.getUsuario().getId_usuario();
			item.id_amigo = lista_amigo.getUsuario_amigo().getId_usuario();
			out.add(item);
		}
		return out;
		
	}
	
	public void removeAmigo(long id_usuario, long id_amigo) throws UsuariosNotFound, NumberFormatException {
		
		Optional<ListaAmigos> lista_amigo = lista_amigosRepository.findByIdUsuario(id_usuario, id_amigo);
		if(lista_amigo.isEmpty()) throw new UsuariosNotFound();
		lista_amigosRepository.delete(lista_amigo.get());
	
	}
	
}
