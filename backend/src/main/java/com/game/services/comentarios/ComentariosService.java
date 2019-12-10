package com.game.services.comentarios;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.game.controllers.comentarios.dto.ComentarioItem;
import com.game.exceptions.ApiException;
import com.game.persistence.models.Comentarios;
import com.game.persistence.models.Noticias;
import com.game.persistence.models.Usuarios;
import com.game.persistence.repository.ComentariosRepository;
import com.game.persistence.repository.NoticiasRepository;
import com.game.persistence.repository.UsuariosRepository;

/**
 * @author Juan Paggi
 * Logica de servicio para los comentarios
 */

@Service
public class ComentariosService {

	@Autowired
	ComentariosRepository comentariosRepository;
	
	@Autowired
	UsuariosRepository usuariosRepository;
	
	@Autowired
	NoticiasRepository noticiasRepository;

	public ComentarioItem getComentario(long id) {
		
		try {
			Optional<Comentarios> comentario = comentariosRepository.findById(id);
			ComentarioItem comentarioItem = new ComentarioItem();
			if (comentario.isPresent()){
				comentarioItem.id_comentario = comentario.get().getId_comentario();
				comentarioItem.comentario = comentario.get().getComentario();
				comentarioItem.fecha_publicacion = comentario.get().getFecha_publicacion();
				comentarioItem.id_usuario = comentario.get().getUsuario().getId_usuario();
				comentarioItem.id_noticia = comentario.get().getNoticia().getId_noticia();
			} else {
				throw new ApiException(404, "No existe el comentario");
			}
			return comentarioItem;
		} catch (ApiException e) {
			throw e;
		} catch (Exception exception) {
			throw exception; 
		}
		
	}
	
	public List<ComentarioItem> getAllComentarios() {
			
		try {
			List<Comentarios> comentarios = comentariosRepository.findAll();
			List<ComentarioItem> out = new ArrayList<ComentarioItem>();
			for(Comentarios comentario: comentarios) {
				ComentarioItem item = new ComentarioItem();
				item.id_comentario = comentario.getId_comentario();
				item.comentario = comentario.getComentario();
				item.fecha_publicacion = comentario.getFecha_publicacion();
				item.id_usuario = comentario.getUsuario().getId_usuario();
				item.id_noticia = comentario.getNoticia().getId_noticia();
				out.add(item);
			}
			return out;
		} catch (ApiException e) {
			throw e;
		} catch (Exception exception) {
			throw exception; 
		}
		
	}

	public long addComentario(ComentarioItem comentarioIn) {
		
		try {
			Optional<Usuarios> usuario = usuariosRepository.findById(comentarioIn.id_usuario);
			Optional<Noticias> noticia = noticiasRepository.findById(comentarioIn.id_noticia);
			Comentarios comentario = new Comentarios();
			if(noticia.isPresent()) {
				comentario.setNoticia(noticia.get());
			}else{
				throw new ApiException(404, "No existe la noticia");
			}
			if(usuario.isPresent()) {
				comentario.setUsuario(usuario.get());
			}else{
				throw new ApiException(404, "No existe el usuario");
			}
			comentario.setComentario(comentarioIn.comentario);
			comentario.setFecha_publicacion(comentarioIn.fecha_publicacion);
			comentario = comentariosRepository.save(comentario);
			return comentario.getId_comentario();			
		} catch (ApiException e) {
			throw e;
		} catch (Exception exception) {
			throw exception; 
		}
	
	}

	public void removeComentario(String id) {
		
		try {
			Optional<Comentarios> comentario = comentariosRepository.findById(Long.parseLong(id));
			if (comentario.isPresent()) {
				comentariosRepository.delete(comentario.get());
			}else {
				throw new ApiException(404, "No existe el comentario");
			}		
		} catch (ApiException e) {
			throw e;
		} catch (Exception exception) {
			throw exception; 
		}
	
	}
	
	public void editComentario(String id, ComentarioItem comentarioIn){
		
		try {
			Optional<Comentarios> comentario = comentariosRepository.findById(Long.parseLong(id));
			if (comentario.isPresent()) {
				Comentarios comentarioObj = comentario.get();
				comentarioObj.setComentario(comentarioIn.comentario);
				comentarioObj.setFecha_publicacion(comentarioIn.fecha_publicacion);
				comentariosRepository.save(comentarioObj);							
			}else {
				throw new ApiException(404, "No existe el comentario");
			}
		} catch (ApiException e) {
			throw e;
		} catch (Exception exception) {
			throw exception; 
		}

	}
	
}
