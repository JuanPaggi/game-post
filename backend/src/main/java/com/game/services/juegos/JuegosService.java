package com.game.services.juegos;

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
	JuegosRepository noticiasRepository;
	
	public JuegoItem getJuego(long id) throws JuegosNotFound {
		
		Optional<Juegos> juego = noticiasRepository.findById(id);
		JuegoItem juegoItem = new JuegoItem();
		if(juego.isEmpty()) throw new JuegosNotFound();
		juegoItem.id_juego = juego.get().getId_juego();
		juegoItem.titulo = juego.get().getTitulo();
		juegoItem.descripcion = juego.get().getDescripcion();
		juegoItem.genero = juego.get().getGenero();
		juegoItem.tipo = juego.get().getTipo();
		juegoItem.desarrollador = juego.get().getDesarrollador();
		juegoItem.analisis_positivos = juego.get().getAnalisis_positivos();
		juegoItem.analisis_negativos = juego.get().getAnalisis_negativos();
		
		return juegoItem;
	}
}
