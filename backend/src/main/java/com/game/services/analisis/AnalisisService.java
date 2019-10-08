package com.game.services.analisis;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.game.controllers.analisis.dto.AnalisisItem;
import com.game.persistence.models.Analisis;
import com.game.persistence.models.Juegos;
import com.game.persistence.repository.AnalisisRepository;
import com.game.persistence.repository.JuegosRepository;
import com.game.services.analisis.exceptions.AnalisisNotFound;

/**
 * @author pachi
 *
 */

@Service
public class AnalisisService {
	
	@Autowired
	AnalisisRepository analisisRepository;
	
	@Autowired
	JuegosRepository juegosRepository;
	
	public AnalisisItem getAnalisis(long id) throws AnalisisNotFound {
		
		Optional<Analisis> analisis = analisisRepository.findById(id);
		Optional<Juegos> juego = juegosRepository.findById(id);
		AnalisisItem analisisItem = new AnalisisItem();
		if(analisis.isEmpty() && juego.isEmpty()) throw new AnalisisNotFound();
		analisisItem.id_analisis = analisis.get().getId_analisis();
		analisisItem.analisis = analisis.get().getAnalisis();
		analisisItem.fecha_publicacion = analisis.get().getFecha_publicacion();
		analisisItem.id_juego = analisis.get().getJuego().getId_juego();
		analisisItem.id_usuario = analisis.get().getId_usuario();
		
		return analisisItem;
	}
}
