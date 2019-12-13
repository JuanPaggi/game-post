package com.game.services.lista_amigos;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.game.controllers.lista_amigos.dto.ListaAmigosItem;
import com.game.exceptions.ApiException;
import com.game.persistence.models.ListaAmigos;
import com.game.persistence.models.Usuarios;
import com.game.persistence.repository.Lista_amigosRepository;
import com.game.persistence.repository.UsuariosRepository;

/**
 * @author pachi
 * Logica de servicio para la lista de amigos
 */

@Service
public class ListaAmigosService {

	@Autowired
	UsuariosRepository usuariosrepository;
	
	@Autowired
	Lista_amigosRepository lista_amigosRepository;
	
	public long addAmigo(ListaAmigosItem amigoIn){
		
		try {
			Optional<ListaAmigos> lista = lista_amigosRepository.findByIdUsuario(amigoIn.id_usuario, amigoIn.id_amigo);
			if(lista.isEmpty()) {
				Optional<Usuarios> usuario = usuariosrepository.findById(amigoIn.id_usuario);
				Optional<Usuarios> usuario_amigo = usuariosrepository.findById(amigoIn.id_amigo);
				ListaAmigos lista_amigo = new ListaAmigos();
				if (!usuario.isPresent()) {
					throw new ApiException(404, "No existe el usuario");
				}
				if (!usuario_amigo.isPresent()) {
					throw new ApiException(404, "No existe el usuario");
				}
				lista_amigo.setUsuario(usuario.get());
				lista_amigo.setUsuario_amigo(usuario_amigo.get());
				lista_amigosRepository.save(lista_amigo);
				return lista_amigo.getId_lista_amigos();
			} else {
				throw new ApiException(409, "Los usuarios ya son amigos");
			}			
		} catch (ApiException e) {
			throw e;
		} catch (Exception exception) {
			throw exception; 
		}
		
	}
	
	public List<ListaAmigosItem> getAllAmigos(long id) {
				
			List<ListaAmigos> listaAmigos = lista_amigosRepository.findAmigosById(id);
			List<ListaAmigosItem> out = new ArrayList<ListaAmigosItem>();
			for(ListaAmigos lista_amigo: listaAmigos) {
				ListaAmigosItem item = new ListaAmigosItem();
				item.id_usuario = lista_amigo.getUsuario().getId_usuario();
				item.id_amigo = lista_amigo.getUsuario_amigo().getId_usuario();
				out.add(item);
			}
			return out;
		
	}
	
	public void removeAmigo(long id_usuario, long id_amigo) {
		
		try {
			Optional<ListaAmigos> lista_amigo = lista_amigosRepository.findByIdUsuario(id_usuario, id_amigo);			
			if (lista_amigo.isPresent()) {
				lista_amigosRepository.delete(lista_amigo.get());				
			}else {
				throw new ApiException(404, "No existe el usuario");
			}
		} catch (ApiException e) {
			throw e;
		} catch (Exception exception) {
			throw exception; 
		}
	
	}
	
}
