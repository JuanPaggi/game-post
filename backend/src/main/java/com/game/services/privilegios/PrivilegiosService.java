package com.game.services.privilegios;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.game.controllers.privilegios.dto.PrivilegioItem;
import com.game.exceptions.ApiException;
import com.game.persistence.models.Privilegios;
import com.game.persistence.repository.PrivilegiosRepository;

/**
 * @author Juan Paggi Logica de servicio para los privilegios
 */

@Service
public class PrivilegiosService {

	@Autowired
	PrivilegiosRepository privilegiosRepository;

	public List<PrivilegioItem> getAllPrivilegios() {

		try {
			List<Privilegios> privilegios = privilegiosRepository.findAll();
			List<PrivilegioItem> out = new ArrayList<PrivilegioItem>();
			for (Privilegios privilegio : privilegios) {
				PrivilegioItem item = new PrivilegioItem();
				item.id_privilegio = privilegio.getId_privilegio();
				item.privilegio = privilegio.getPrivilegio();
				out.add(item);
			}
			return out;
		} catch (ApiException e) {
			throw e;
		} catch (Exception exception) {
			throw exception;
		}

	}

	public long addPrivilegio(PrivilegioItem privilegioIn) {

		try {
			Privilegios privilegio = new Privilegios();
			privilegio.setPrivilegio(privilegioIn.privilegio);
			privilegio = privilegiosRepository.save(privilegio);
			return privilegio.getId_privilegio();
		} catch (ApiException e) {
			throw e;
		} catch (Exception exception) {
			throw exception;
		}

	}

	public void removePrivilegio(String id) {

		try {
			Optional<Privilegios> privilegio = privilegiosRepository.findById(Long.parseLong(id));
			if (privilegio.isPresent()) {
				privilegiosRepository.delete(privilegio.get());
			} else {
				throw new ApiException(404, "No existe el privilegio");
			}
		} catch (ApiException e) {
			throw e;
		} catch (Exception exception) {
			throw exception;
		}

	}

}
