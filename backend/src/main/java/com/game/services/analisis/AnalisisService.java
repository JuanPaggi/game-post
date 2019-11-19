package com.game.services.analisis;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.game.controllers.analisis.dto.AnalisisItem;
import com.game.persistence.models.Analisis;
import com.game.persistence.models.Juegos;
import com.game.persistence.models.Usuarios;
import com.game.persistence.repository.AnalisisRepository;
import com.game.persistence.repository.JuegosRepository;
import com.game.persistence.repository.UsuariosRepository;
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
	
	@Autowired
	UsuariosRepository usuariosRepository;
	
	public AnalisisItem getAnalisis(long id) throws AnalisisNotFound {
		
		Optional<Analisis> analisis = analisisRepository.findById(id);
		Optional<Juegos> juego = juegosRepository.findById(id);
		AnalisisItem analisisItem = new AnalisisItem();
		if(analisis.isEmpty() && juego.isEmpty()) throw new AnalisisNotFound();
		analisisItem.id_analisis = analisis.get().getId_analisis();
		analisisItem.analisis = analisis.get().getAnalisis();
		analisisItem.valoracion = analisis.get().isValoracion();
		analisisItem.fecha_publicacion = analisis.get().getFecha_publicacion();
		analisisItem.id_juego = analisis.get().getJuego().getId_juego();
		analisisItem.id_usuario = analisis.get().getUsuario().getId_usuario();
		return analisisItem;
		
	}
	
	public long addAnalisis(AnalisisItem analisisIn) throws AnalisisNotFound{
		
		Analisis analisis = new Analisis();
		Optional<Juegos> juego = juegosRepository.findById(analisisIn.id_juego);
		Optional<Usuarios> usuario = usuariosRepository.findById(analisisIn.id_usuario);
		if(juego.isEmpty()) throw new AnalisisNotFound();
		analisis.setAnalisis(analisisIn.analisis);
		analisis.setFecha_publicacion(analisisIn.fecha_publicacion);
		analisis.setValoracion(analisisIn.valoracion);
		analisis.setJuego(juego.get());
		analisis.setUsuario(usuario.get());
		analisis = analisisRepository.save(analisis);
		return analisis.getId_analisis();
	
	}
	
	public List<AnalisisItem> getAllAnalisis() throws ParseException{
		
		List<Analisis> analisis = analisisRepository.findAll();
		List<AnalisisItem> out = new ArrayList<AnalisisItem>();
		for(Analisis analisisAux: analisis) {
			AnalisisItem item = new AnalisisItem();
			item.id_analisis = analisisAux.getId_analisis();
			item.id_juego = analisisAux.getId_analisis();
			item.analisis = analisisAux.getAnalisis();
			item.fecha_publicacion = analisisAux.getFecha_publicacion();
			item.valoracion = analisisAux.isValoracion();
			item.id_juego = analisisAux.getJuego().getId_juego();
			item.id_usuario = analisisAux.getUsuario().getId_usuario();
			out.add(item);
		}
		return out;
		
	}
	
	public void removeAnalisis(String id) throws AnalisisNotFound, NumberFormatException {
		
		Optional<Analisis> analisis = analisisRepository.findById(Long.parseLong(id));
		if(analisis.isEmpty()) throw new AnalisisNotFound();
		analisisRepository.delete(analisis.get());
	
	}
	
	public void editAnalisis(String id, AnalisisItem analisisIn) throws AnalisisNotFound, NumberFormatException{
		
		Optional<Analisis> analisis = analisisRepository.findById(Long.parseLong(id));
		Optional<Juegos> juego = juegosRepository.findById(analisisIn.id_juego);
		Optional<Usuarios> usuario = usuariosRepository.findById(analisisIn.id_usuario);
		if(analisis.isEmpty() && juego.isEmpty()) throw new AnalisisNotFound();
		Analisis analisisObj = analisis.get();
		analisisObj.setAnalisis(analisisIn.analisis);
		analisisObj.setValoracion(analisisIn.valoracion);
		analisisObj.setFecha_publicacion(analisisIn.fecha_publicacion);
		analisisObj.setJuego(juego.get());
		analisisObj.setUsuario(usuario.get());
		analisisRepository.save(analisisObj);
		
	}
	
}
