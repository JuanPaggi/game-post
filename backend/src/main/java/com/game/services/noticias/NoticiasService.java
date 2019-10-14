package com.game.services.noticias;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.game.controllers.noticias.dto.NoticiaItem;
import com.game.persistence.models.Admin;
import com.game.persistence.models.Noticias;
import com.game.persistence.models.Tag;
import com.game.persistence.repository.AdminRepository;
import com.game.persistence.repository.NoticiasRepository;
import com.game.persistence.repository.TagRepository;
import com.game.services.admin.exceptions.AdminNotFound;
import com.game.services.noticias.exceptions.NoticiasNotFound;
import com.game.services.tag.exceptions.TagNotFound;

/**
 * @author negro
 *
 */

@Service
public class NoticiasService {

	@Autowired
	NoticiasRepository noticiasRepository;
	
	@Autowired
	AdminRepository adminRepository;
	
	@Autowired
	TagRepository tagRepository;

	public NoticiaItem getNoticias(long id) throws NoticiasNotFound {
		
		Optional<Noticias> noticia = noticiasRepository.findById(id);
		NoticiaItem noticiaItem = new NoticiaItem();
		if(noticia.isEmpty()) throw new NoticiasNotFound();
		noticiaItem.id_noticia = noticia.get().getId_noticia();
		noticiaItem.titulo = noticia.get().getTitulo();
		noticiaItem.descripcion = noticia.get().getDescripcion();
		noticiaItem.cuerpo = noticia.get().getCuerpo();
		noticiaItem.fecha_publicacion = noticia.get().getFecha_publicacion();
		noticiaItem.id_admin_creado = noticia.get().getAdmin().getId_admin();
		List<Tag> tag_noticia = noticia.get().getTags();
		ArrayList<Long> tag_id = new ArrayList<>();
		for (Tag tag : tag_noticia) {
			tag_id.add(tag.getId_tag());
		}
		noticiaItem.tags = tag_id;
		return noticiaItem;
		
	}
	
	public List<NoticiaItem> getAllNoticias() throws ParseException{
		
		List<Noticias> noticias = noticiasRepository.findAll();
		List<NoticiaItem> out = new ArrayList<NoticiaItem>();
		for(Noticias noticia: noticias) {
			NoticiaItem item = new NoticiaItem();
			item.id_noticia = noticia.getId_noticia();
			item.titulo = noticia.getTitulo();
			item.descripcion = noticia.getDescripcion();
			item.cuerpo = noticia.getCuerpo();
			item.fecha_publicacion = noticia.getFecha_publicacion();
			item.id_admin_creado = noticia.getAdmin().getId_admin();
			List<Tag> tag_noticia = noticia.getTags();
			ArrayList<Long> tag_id = new ArrayList<>();
			for (Tag tag : tag_noticia) {
				tag_id.add(tag.getId_tag());
			}
			item.tags = tag_id;
			out.add(item);
		}
		return out;
		
	}
	
	public long addNoticia(NoticiaItem noticiaIn) throws AdminNotFound,TagNotFound{
		
		List<Tag> lista_tag= tagRepository.findAllById(noticiaIn.tags);
		Optional<Admin> admin = adminRepository.findById(noticiaIn.id_admin_creado);	
		Noticias noticia = new Noticias();
		if(admin.isEmpty()) throw new AdminNotFound();
		if(lista_tag.size() != noticiaIn.tags.size()) throw new TagNotFound();
		noticia.setTitulo(noticiaIn.titulo);
		noticia.setDescripcion(noticiaIn.descripcion);
		noticia.setCuerpo(noticiaIn.cuerpo);
		noticia.setFecha_publicacion(noticiaIn.fecha_publicacion);
		noticia.setAdmin(admin.get());
		noticia.setTags(lista_tag);
		noticia = noticiasRepository.save(noticia);
		return noticia.getId_noticia();
	
	}
	
	public void removeNoticia(String id) throws NoticiasNotFound, NumberFormatException {
		
		Optional<Noticias> noticia = noticiasRepository.findById(Long.parseLong(id));
		if(noticia.isEmpty()) throw new NoticiasNotFound();
		noticiasRepository.delete(noticia.get());
	
	}
	
	public void editNoticia(String id, NoticiaItem noticiaIn) throws NoticiasNotFound, NumberFormatException, TagNotFound{
		
		Optional<Noticias> noticia = noticiasRepository.findById(Long.parseLong(id));
		if(noticia.isEmpty()) throw new NoticiasNotFound();
		Noticias noticiaObj = noticia.get();
		noticiaObj.setTitulo(noticiaIn.titulo);
		noticiaObj.setDescripcion(noticiaIn.descripcion);
		noticiaObj.setCuerpo(noticiaIn.cuerpo);
		noticiaObj.setFecha_publicacion(noticiaIn.fecha_publicacion);
		List<Tag> lista_tag= tagRepository.findAllById(noticiaIn.tags);
		if(lista_tag.size() != noticiaIn.tags.size()) throw new TagNotFound();
		noticiaObj.setTags(lista_tag);
		noticiasRepository.save(noticiaObj);
	}
}
