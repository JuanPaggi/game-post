package com.game.services.modos;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.game.controllers.modos.dto.ModoItem;
import com.game.exceptions.ApiException;
import com.game.persistence.models.Modos;
import com.game.persistence.repository.ModosRepository;

/**
 * @author Juan Paggi. Logica de servicio para los modos.
 */

@Service
public class ModosService {

	@Autowired
	ModosRepository modosRepository;

	public List<ModoItem> getAllModos() {

		List<Modos> modos = modosRepository.findAll();
		List<ModoItem> out = new ArrayList<ModoItem>();
		for (Modos modo : modos) {
			ModoItem item = new ModoItem();
			item.id_modo = modo.getId_modo();
			item.modo = modo.getModo();
			out.add(item);
		}
		return out;

	}

	public long addModo(ModoItem modoIn) {

		try {
			Modos modo = new Modos();
			modo.setModo(modoIn.modo);
			modo = modosRepository.save(modo);
			return modo.getId_modo();
		} catch (ApiException e) {
			throw e;
		} catch (Exception exception) {
			throw exception;
		}

	}

	public void removeModo(String id) {

		try {
			Optional<Modos> modo = modosRepository.findById(Long.parseLong(id));
			if (modo.isPresent()) {
				modosRepository.delete(modo.get());
			} else {
				throw new ApiException(404, "No existe el modo");
			}
		} catch (ApiException e) {
			throw e;
		} catch (Exception exception) {
			throw exception;
		}

	}

}
