package com.game.services.analisis;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.game.controllers.analisis.dto.AnalisisItem;
import com.game.exceptions.ApiException;
import com.game.persistence.models.Analisis;
import com.game.persistence.models.Juegos;
import com.game.persistence.models.Usuarios;
import com.game.persistence.repository.AnalisisRepository;
import com.game.persistence.repository.JuegosRepository;
import com.game.persistence.repository.UsuariosRepository;
import com.game.services.juegos.JuegosService;

/**
 * @author Juan Paggi
 * Logica de servicio para los analisis
 */

@Service
public class AnalisisService {
	
	@Autowired
	AnalisisRepository analisisRepository;
	
	@Autowired
	JuegosRepository juegosRepository;
	
	@Autowired
	UsuariosRepository usuariosRepository;
	
	@Autowired
	JuegosService juegosService;
	
	public AnalisisItem getAnalisis(long id) {
		
		try {
			Optional<Analisis> analisis = analisisRepository.findById(id);
			AnalisisItem analisisItem = new AnalisisItem();
			if (analisis.isPresent()){
				analisisItem.id_analisis = analisis.get().getId_analisis();
				analisisItem.analisis = analisis.get().getAnalisis();
				analisisItem.valoracion = analisis.get().isValoracion();
				analisisItem.fecha_publicacion = analisis.get().getFecha_publicacion();
				analisisItem.id_juego = analisis.get().getJuego().getId_juego();
				analisisItem.id_usuario = analisis.get().getUsuario().getId_usuario();
			} else {
				throw new ApiException(404, "No existe el analisis");
			}
			return analisisItem;
		} catch (ApiException e) {
			throw e;
		} catch (Exception exception) {
			throw exception; 
		}
		
	}
	
	public List<AnalisisItem> getAllAnalisis(){
		
		try {
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
		} catch (ApiException e) {
			throw e;
		} catch (Exception exception) {
			throw exception; 
		}
		
	}
	
	public long addAnalisis(AnalisisItem analisisIn) {
		
		try {
			Analisis analisis = new Analisis();
			Optional<Juegos> juego = juegosRepository.findById(analisisIn.id_juego);
			Optional<Usuarios> usuario = usuariosRepository.findById(analisisIn.id_usuario);
			if(juego.isPresent()) {
				analisis.setJuego(juego.get());
			}else{
				throw new ApiException(404, "No existe el juego");
			}
			if (usuario.isPresent()) {
				analisis.setUsuario(usuario.get());
			}else {
				throw new ApiException(404, "No existe el usuario");
			}
			analisis.setAnalisis(analisisIn.analisis);
			analisis.setFecha_publicacion(analisisIn.fecha_publicacion);
			analisis.setValoracion(analisisIn.valoracion);
			analisis = analisisRepository.save(analisis);
			juegosService.agregarValocacion(analisisIn.id_juego, analisisIn.valoracion);
			return analisis.getId_analisis();
		} catch (ApiException e) {
			throw e;
		} catch (Exception exception) {
			throw exception; 
		}
	
	}
	
	public void removeAnalisis(String id) {
		
		try {
			Optional<Analisis> analisis = analisisRepository.findById(Long.parseLong(id));
			if (analisis.isPresent()) {
				analisisRepository.delete(analisis.get());
			}else {
				throw new ApiException(404, "No existe el analisis");
			}
		} catch (ApiException e) {
			throw e;
		} catch (Exception exception) {
			throw exception; 
		}
	
	}
	
	public void editAnalisis(String id, AnalisisItem analisisIn) {
		
		try {
			Optional<Analisis> analisis = analisisRepository.findById(Long.parseLong(id));
			Optional<Juegos> juego = juegosRepository.findById(analisisIn.id_juego);
			Optional<Usuarios> usuario = usuariosRepository.findById(analisisIn.id_usuario);
			if (analisis.isPresent()) {
				Analisis analisisObj = analisis.get();
				if (juego.isPresent()) {
					analisisObj.setJuego(juego.get());
				}else {
					throw new ApiException(404, "No existe el juego");
				}
				if (usuario.isPresent()) {
					analisisObj.setUsuario(usuario.get());
				}else {
					throw new ApiException(404, "No existe el usuario");
				}
				analisisObj.setAnalisis(analisisIn.analisis);
				analisisObj.setValoracion(analisisIn.valoracion);
				analisisObj.setFecha_publicacion(analisisIn.fecha_publicacion);
				analisisRepository.save(analisisObj);
			}else {
				throw new ApiException(404, "No existe el analisis");
			}
		} catch (ApiException e) {
			throw e;
		} catch (Exception exception) {
			throw exception; 
		}
		
	}
	
}
