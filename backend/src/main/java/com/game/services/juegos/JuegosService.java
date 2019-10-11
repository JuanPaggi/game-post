package com.game.services.juegos;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.game.controllers.juegos.dto.JuegoItem;
import com.game.persistence.models.Admin;
import com.game.persistence.models.Juegos;
import com.game.persistence.models.Requisitos;
import com.game.persistence.models.Tag;
import com.game.persistence.repository.AdminRepository;
import com.game.persistence.repository.AnalisisRepository;
import com.game.persistence.repository.JuegosRepository;
import com.game.persistence.repository.RequisitosRepository;
import com.game.persistence.repository.TagRepository;
import com.game.services.juegos.exceptions.JuegosNotFound;

/**
 * @author pachi
 *
 */

@Service
public class JuegosService {

	@Autowired
	JuegosRepository juegosRepository;
	
	@Autowired
	RequisitosRepository requisitosRepository;
	
	@Autowired
	AnalisisRepository analisisRepository;
	
	@Autowired
	TagRepository tagRepository;
	
	@Autowired
	AdminRepository adminRepository;

	public JuegoItem getJuego(long id) throws JuegosNotFound {
		
		Optional<Juegos> juego = juegosRepository.findById(id);
		JuegoItem juegoItem = new JuegoItem();
		if(juego.isEmpty()) throw new JuegosNotFound();
		juegoItem.id_juego = juego.get().getId_juego();
		juegoItem.titulo = juego.get().getTitulo();
		juegoItem.descripcion = juego.get().getDescripcion();
		juegoItem.genero = juego.get().getGenero();
		juegoItem.tipo = juego.get().getTipo();
		juegoItem.desarrollador = juego.get().getDesarrollador();
		juegoItem.fecha_lanzamiento = juego.get().getFecha_lanzamiento();
		juegoItem.analisis_positivos = juego.get().getAnalisis_positivos();
		juegoItem.analisis_negativos = juego.get().getAnalisis_negativos();
		juegoItem.id_requisitos = juego.get().getRequisitos().getId_requisitos();
		juegoItem.id_admin_creado = juego.get().getAdmin().getId_admin();
		List<Tag> tag_juego = juego.get().getTag();
		ArrayList<Long> tag_id = new ArrayList<>();
		for (Tag tag : tag_juego) {
			tag_id.add(tag.getId_tag());
		}
		juegoItem.tags = tag_id;
		return juegoItem;
		
	}
	
	public long addJuego(JuegoItem juegoIn) throws JuegosNotFound{
		
		Juegos juego = new Juegos();
		Optional<Requisitos> requisitos = requisitosRepository.findById(juegoIn.id_requisitos);
		List<Tag> lista_tag= tagRepository.findAllById(juegoIn.tags);
		Optional<Admin> admin = adminRepository.findById(juegoIn.id_admin_creado);
		if(requisitos.isEmpty() || lista_tag.size() != juegoIn.tags.size()) throw new JuegosNotFound();
		juego.setTitulo(juegoIn.titulo);
		juego.setDescripcion(juegoIn.descripcion);
		juego.setGenero(juegoIn.genero);
		juego.setTipo(juegoIn.tipo);
		juego.setDesarrollador(juegoIn.desarrollador);
		juego.setFecha_lanzamiento(juegoIn.fecha_lanzamiento);
		juego.setAnalisis_positivos(juegoIn.analisis_positivos);
		juego.setAnalisis_negativos(juegoIn.analisis_negativos);
		juego.setRequisitos(requisitos.get());
		juego.setAdmin(admin.get());
		juego.setTag(lista_tag);
		juego = juegosRepository.save(juego);
		return juego.getId_juego();
	
	}
	
	public List<JuegoItem> getAllJuegos() throws ParseException{
		
		List<Juegos> juegos = juegosRepository.findAll();
		List<JuegoItem> out = new ArrayList<JuegoItem>();
		for(Juegos juego: juegos) {
			JuegoItem item = new JuegoItem();
			item.id_juego = juego.getId_juego();
			item.titulo = juego.getTitulo();
			item.descripcion = juego.getDescripcion();
			item.genero = juego.getGenero();
			item.tipo = juego.getTipo();
			item.desarrollador = juego.getDesarrollador();
			item.fecha_lanzamiento = juego.getFecha_lanzamiento();
			item.analisis_positivos = juego.getAnalisis_positivos();
			item.analisis_negativos = juego.getAnalisis_negativos();
			item.id_requisitos = juego.getRequisitos().getId_requisitos();
			item.id_admin_creado = juego.getAdmin().getId_admin();
			List<Tag> tag_juego = juego.getTag();
			ArrayList<Long> tag_id = new ArrayList<>();
			for (Tag tag : tag_juego) {
				tag_id.add(tag.getId_tag());
			}
			item.tags = tag_id;
			out.add(item);
		}
		return out;
		
	}
	
	public void removeJuego(String id) throws JuegosNotFound, NumberFormatException {
		
		Optional<Juegos> juego = juegosRepository.findById(Long.parseLong(id));
		if(juego.isEmpty()) throw new JuegosNotFound();
		juegosRepository.delete(juego.get());
	
	}
	
	public void editJuego(String id, JuegoItem juegoIn) throws JuegosNotFound, NumberFormatException{
		
		Optional<Juegos> juego = juegosRepository.findById(Long.parseLong(id));
		Optional<Requisitos> requisitos = requisitosRepository.findById(juegoIn.id_requisitos);
		if(juego.isEmpty() && requisitos.isEmpty()) throw new JuegosNotFound();
		Juegos juegoObj = juego.get();
		juegoObj.setTitulo(juegoIn.titulo);
		juegoObj.setDescripcion(juegoIn.descripcion);
		juegoObj.setGenero(juegoIn.genero);
		juegoObj.setTipo(juegoIn.tipo);
		juegoObj.setDesarrollador(juegoIn.desarrollador);
		juegoObj.setFecha_lanzamiento(juegoIn.fecha_lanzamiento);
		juegoObj.setAnalisis_positivos(juegoIn.analisis_positivos);
		juegoObj.setAnalisis_negativos(juegoIn.analisis_negativos);
		juegoObj.setRequisitos(requisitos.get());
		List<Tag> lista_tag= tagRepository.findAllById(juegoIn.tags);
		if(lista_tag.size() != juegoIn.tags.size()) throw new JuegosNotFound();
		juegoObj.setTag(lista_tag);
		juegosRepository.save(juegoObj);
		
	}
	
}
