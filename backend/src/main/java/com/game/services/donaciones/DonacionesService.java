package com.game.services.donaciones;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.game.controllers.donaciones.dto.DonacionesItem;
import com.game.exceptions.ApiException;
import com.game.persistence.models.Donaciones;
import com.game.persistence.models.Usuarios;
import com.game.persistence.repository.DonacionesRepository;
import com.game.persistence.repository.UsuariosRepository;

/**
 * @author Juan Paggi
 * Logica de servicio para las donaciones
 */

@Service
public class DonacionesService {
	
	@Autowired
	UsuariosRepository usuariosrepository;
	
	@Autowired
	DonacionesRepository donacionesRepository;
	
	public List<DonacionesItem> getDonaciones(long id) {
		
		try {
			List<Donaciones> donaciones = donacionesRepository.findByIdDonaciones(id);
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
		} catch (ApiException e) {
			throw e;
		} catch (Exception exception) {
			throw exception; 
		}
		
	}
	
	public long addDonacion(DonacionesItem donacionIn) {
		
		try {			
			Optional<Usuarios> usuario = usuariosrepository.findById(donacionIn.id_usuario);
			Donaciones donacion = new Donaciones();
			if(usuario.isPresent()) {
				donacion.setUsuarios(usuario.get());
			}else{
				throw new ApiException(404, "No existe el usuario");
			}
			donacion.setMonto(donacionIn.monto);
			donacion.setFecha_donacion(donacionIn.fecha_donacion);
			donacion.setComentario(donacionIn.comentario);
			donacion = donacionesRepository.save(donacion);
			return donacion.getId_donacion();
		} catch (ApiException e) {
			throw e;
		} catch (Exception exception) {
			throw exception; 
		}
		
	}
	
	public void removeDonacion(String id) {
		
		try {
			Optional<Donaciones> donacion = donacionesRepository.findById(Long.parseLong(id));
			if (donacion.isPresent()) {
				donacionesRepository.delete(donacion.get());	
			}else {
				throw new ApiException(404, "No existe la donacion");
			}	
		} catch (ApiException e) {
			throw e;
		} catch (Exception exception) {
			throw exception; 
		}
	
	}
	
}
