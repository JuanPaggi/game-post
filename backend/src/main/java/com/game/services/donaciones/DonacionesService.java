package com.game.services.donaciones;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.game.controllers.donaciones.dto.DonacionesItem;
import com.game.persistence.models.Donaciones;
import com.game.persistence.models.Usuarios;
import com.game.persistence.repository.DonacionesRepository;
import com.game.persistence.repository.UsuariosRepository;
import com.game.services.usuarios.exceptions.UsuariosNotFound;

/**
 * @author pachi
 *
 */

@Service
public class DonacionesService {
	
	@Autowired
	UsuariosRepository usuariosrepository;
	
	@Autowired
	DonacionesRepository donacionesRepository;
	
	public List<DonacionesItem> getDonaciones(long id) throws UsuariosNotFound {
		
		List<Donaciones> donaciones = donacionesRepository.findByIdDonaciones(id);
		if(donaciones.isEmpty())  throw new UsuariosNotFound();
		List<DonacionesItem> out = new ArrayList<DonacionesItem>();
		for(Donaciones donacion: donaciones) {
			DonacionesItem item = new DonacionesItem();
			item.id_donacion = donacion.getId_donacion();
			item.id_usuario = donacion.getUsuarios().getId_usuario();
			item.monto = donacion.getMonto();
			item.fecha_donacion = donacion.getFecha_donacion();
			item.comentario = donacion.getComentario();
			out.add(item);
		}
		return out;
		
	}
	
	public long addDonacion(DonacionesItem donacionIn) {
		
		Optional<Usuarios> usuario = usuariosrepository.findById(donacionIn.id_usuario);
		
		Donaciones donacion = new Donaciones();
		donacion.setUsuarios(usuario.get());
		donacion.setMonto(donacionIn.monto);
		donacion.setFecha_donacion(donacionIn.fecha_donacion);
		donacion.setComentario(donacionIn.comentario);
		donacion = donacionesRepository.save(donacion);
		return donacion.getId_donacion();
		
	}
	
	public void removeDonacion(String id) throws UsuariosNotFound, NumberFormatException {
		
		Optional<Donaciones> donacion = donacionesRepository.findById(Long.parseLong(id));
		if(donacion.isEmpty()) throw new UsuariosNotFound();
		donacionesRepository.delete(donacion.get());
	
	}
	
}
