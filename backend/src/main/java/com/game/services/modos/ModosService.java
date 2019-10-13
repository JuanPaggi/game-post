package com.game.services.modos;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.game.controllers.modos.dto.ModoItem;
import com.game.persistence.models.Modos;
import com.game.persistence.repository.ModosRepository;
import com.game.services.modos.exceptions.ModosNotFound;

/**
 * @author negro
 *
 */

@Service
public class ModosService {
	
	@Autowired
	ModosRepository modosRepository;
	
public List<ModoItem> getAllModos() throws ParseException{
		
		List<Modos> modos = modosRepository.findAll();
		List<ModoItem> out = new ArrayList<ModoItem>();
		for(Modos modo: modos) {
			ModoItem item = new ModoItem();
			item.id_modo = modo.getId_modo();
			item.modo = modo.getModo();
			out.add(item);
		}
		return out;
		
	}

public long addModo(ModoItem modoIn){
	
	Modos modo = new Modos();
	modo.setModo(modoIn.modo);
	modo = modosRepository.save(modo);
	return modo.getId_modo();

}

public void removeModo(String id) throws ModosNotFound, NumberFormatException {
	
	Optional<Modos> modo = modosRepository.findById(Long.parseLong(id));
	if(modo.isEmpty()) throw new ModosNotFound();
	modosRepository.delete(modo.get());

}

}
