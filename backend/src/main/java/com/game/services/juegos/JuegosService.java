package com.game.services.juegos;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.game.controllers.juegos.dto.JuegoItem;
import com.game.persistence.models.Juegos;
import com.game.persistence.repository.JuegosRepository;
import com.game.services.juegos.exceptions.JuegosNotFound;

/**
 * @author pachi
 *
 */

@Service
public class JuegosService {

	@Autowired
	JuegosRepository juegosRepository;
	
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
		
		return juegoItem;
		
	}
	
	public long addJuego(JuegoItem juegoIn){
		
		Juegos juego = new Juegos();
		juego.setTitulo(juegoIn.titulo);
		juego.setDescripcion(juegoIn.descripcion);
		juego.setGenero(juegoIn.genero);
		juego.setTipo(juegoIn.tipo);
		juego.setDesarrollador(juegoIn.desarrollador);
		juego.setFecha_lanzamiento(juegoIn.fecha_lanzamiento);
		juego.setAnalisis_positivos(juegoIn.analisis_positivos);
		juego.setAnalisis_negativos(juegoIn.analisis_negativos);

		juego = juegosRepository.save(juego);
		
		return juego.getId_juego();
	
	}
	
	public List<JuegoItem> getAllJuegos() throws ParseException{
		
		List<Juegos> juegos = juegosRepository.findAllNews();
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
			out.add(item);
		}
		return out;
		
	}
	
	public void removeJuego(String id) throws JuegosNotFound, NumberFormatException {
		
		Optional<Juegos> juego = juegosRepository.findById(Long.parseLong(id));
		if(juego.isEmpty()) throw new JuegosNotFound();
		juegosRepository.delete(juego.get()); // this can be deleteById but I used this for previous validation exception.
	
	}
	
}
