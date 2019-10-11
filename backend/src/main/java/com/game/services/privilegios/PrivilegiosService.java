package com.game.services.privilegios;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.game.controllers.privilegios.dto.PrivilegioItem;
import com.game.persistence.models.Privilegios;
import com.game.persistence.repository.PrivilegiosRepository;
import com.game.services.privilegios.exceptions.PrivilegioNotFound;

/**
 * @author pachi
 *
 */

@Service
public class PrivilegiosService {

	@Autowired
	PrivilegiosRepository privilegiosRepository;
	
	public List<PrivilegioItem> getAllPrivilegios() throws ParseException{
		
		List<Privilegios> privilegios = privilegiosRepository.findAll();
		List<PrivilegioItem> out = new ArrayList<PrivilegioItem>();
		for(Privilegios privilegio: privilegios) {
			PrivilegioItem item = new PrivilegioItem();
			item.id_privilegio = privilegio.getId_privilegio();
			item.privilegio = privilegio.getPrivilegio();
			out.add(item);
		}
		return out;
		
	}
	
	public long addPrivilegio(PrivilegioItem privilegioIn) {
		
		Privilegios privilegio = new Privilegios();
		privilegio.setPrivilegio(privilegioIn.privilegio);
		
		privilegio = privilegiosRepository.save(privilegio);
		return privilegio.getId_privilegio();
		
	}
	
	public void removePrivilegio(String id) throws PrivilegioNotFound, NumberFormatException {
		
		Optional<Privilegios> privilegio = privilegiosRepository.findById(Long.parseLong(id));
		if(privilegio.isEmpty()) throw new PrivilegioNotFound();
		privilegiosRepository.delete(privilegio.get());
	
	}
	
}
