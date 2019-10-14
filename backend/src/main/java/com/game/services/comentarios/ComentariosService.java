package com.game.services.comentarios;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.game.controllers.comentarios.dto.ComentarioItem;
import com.game.persistence.models.Comentarios;
import com.game.persistence.models.Noticias;
import com.game.persistence.models.Usuarios;
import com.game.persistence.repository.ComentariosRepository;
import com.game.persistence.repository.NoticiasRepository;
import com.game.persistence.repository.UsuariosRepository;
import com.game.services.comentarios.exceptions.ComentariosNotFound;
import com.game.services.noticias.exceptions.NoticiasNotFound;
import com.game.services.usuarios.exceptions.UsuariosNotFound;

/**
 * @author negro
 *
 */

@Service
public class ComentariosService {

	
	@Autowired
	ComentariosRepository comentariosRepository;
	
	@Autowired
	UsuariosRepository usuariosRepository;
	
	@Autowired
	NoticiasRepository noticiasRepository;

	public ComentarioItem getComentario(long id) throws ComentariosNotFound {
		
		Optional<Comentarios> comentario = comentariosRepository.findById(id);
		ComentarioItem comentarioItem = new ComentarioItem();
		if(comentario.isEmpty()) throw new ComentariosNotFound();
		comentarioItem.id_comentario = comentario.get().getId_comentario();
		comentarioItem.comentario = comentario.get().getComentario();
		comentarioItem.fecha_publicacion = comentario.get().getFecha_publicacion();
		comentarioItem.id_usuario = comentario.get().getUsuario().getId_usuario();
		comentarioItem.id_noticia = comentario.get().getNoticia().getId_noticia();
		return comentarioItem;
		
	}
	
	public List<ComentarioItem> getAllComentarios() throws ParseException{
			
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
			
		}

	public long addComentario(ComentarioItem comentarioIn) throws UsuariosNotFound,NoticiasNotFound{
		
		
		Optional<Usuarios> usuario = usuariosRepository.findById(comentarioIn.id_usuario);
		Optional<Noticias> noticia = noticiasRepository.findById(comentarioIn.id_noticia);
		Comentarios comentario = new Comentarios();
		if(usuario.isEmpty()) throw new UsuariosNotFound();
		if(noticia.isEmpty()) throw new NoticiasNotFound();
		comentario.setComentario(comentarioIn.comentario);
		comentario.setFecha_publicacion(comentarioIn.fecha_publicacion);
		comentario.setUsuario(usuario.get());
		comentario.setNoticia(noticia.get());
		comentario = comentariosRepository.save(comentario);
		return comentario.getId_comentario();
	
	}

public void removeComentario(String id) throws ComentariosNotFound, NumberFormatException {
		
		Optional<Comentarios> comentario = comentariosRepository.findById(Long.parseLong(id));
		if(comentario.isEmpty()) throw new ComentariosNotFound();
		comentariosRepository.delete(comentario.get());
	
	}
	
	public void editComentario(String id, ComentarioItem comentarioIn) throws ComentariosNotFound, NumberFormatException{
		
		Optional<Comentarios> comentario = comentariosRepository.findById(Long.parseLong(id));
		if(comentario.isEmpty()) throw new ComentariosNotFound();
		Comentarios comentarioObj = comentario.get();
		comentarioObj.setComentario(comentarioIn.comentario);
		comentarioObj.setFecha_publicacion(comentarioIn.fecha_publicacion);
		comentariosRepository.save(comentarioObj);
		
	}
}
