package com.game.services.requisitos;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.game.controllers.requisitos.dto.RequisitoItem;
import com.game.persistence.models.Requisitos;
import com.game.persistence.repository.RequisitosRepository;
import com.game.services.requisitos.exceptions.RequisitosNotFound;

/**
 * @author pachi
 *
 */

@Service
public class RequisitosService {
	
	@Autowired
	RequisitosRepository requisitosRepository;

	public RequisitoItem getRequisito(long id) throws RequisitosNotFound {
		
		Optional<Requisitos> requisito = requisitosRepository.findById(id);
		RequisitoItem requisitoItem = new RequisitoItem();
		if(requisito.isEmpty()) throw new RequisitosNotFound();
		requisitoItem.id_requisito = requisito.get().getId_requisitos();
		requisitoItem.sistema_operativo = requisito.get().getSistema_operativo();
		requisitoItem.procesador = requisito.get().getProcesador();
		requisitoItem.memoria = requisito.get().getMemoria();
		requisitoItem.grafica = requisito.get().getGrafica();
		requisitoItem.almacenamiento = requisito.get().getAlmacenamiento();
		return requisitoItem;
		
	}
	
	public List<RequisitoItem> getAllRequisitos() throws ParseException{
		
		List<Requisitos> requisitos = requisitosRepository.findAll();
		List<RequisitoItem> out = new ArrayList<RequisitoItem>();
		for(Requisitos requisito: requisitos) {
			RequisitoItem item = new RequisitoItem();
			item.id_requisito = requisito.getId_requisitos();
			item.sistema_operativo = requisito.getSistema_operativo();
			item.procesador = requisito.getProcesador();
			item.memoria = requisito.getMemoria();
			item.grafica = requisito.getMemoria();
			item.almacenamiento = requisito.getAlmacenamiento();
			out.add(item);
		}
		return out;
		
	}
	
	public long addRequisito(RequisitoItem requisitoIn){
		
		Requisitos requisito = new Requisitos();
		requisito.setSistema_operativo(requisitoIn.sistema_operativo);
		requisito.setProcesador(requisitoIn.procesador);
		requisito.setMemoria(requisitoIn.memoria);
		requisito.setGrafica(requisitoIn.grafica);
		requisito.setAlmacenamiento(requisitoIn.almacenamiento);
		requisito = requisitosRepository.save(requisito);
		return requisito.getId_requisitos();
	
	}
	
	public void removeRequisito(String id) throws RequisitosNotFound, NumberFormatException {
		
		Optional<Requisitos> requisito = requisitosRepository.findById(Long.parseLong(id));
		if(requisito.isEmpty()) throw new RequisitosNotFound();
		requisitosRepository.delete(requisito.get());
	
	}
	
	public void editRequisito(String id, RequisitoItem requisitoIn) throws RequisitosNotFound, NumberFormatException{
		
		Optional<Requisitos> requisito = requisitosRepository.findById(Long.parseLong(id));
		if(requisito.isEmpty()) throw new RequisitosNotFound();
		Requisitos requisitoObj = requisito.get();
		requisitoObj.setSistema_operativo(requisitoIn.sistema_operativo);
		requisitoObj.setProcesador(requisitoIn.procesador);
		requisitoObj.setMemoria(requisitoIn.memoria);
		requisitoObj.setGrafica(requisitoIn.grafica);
		requisitoObj.setAlmacenamiento(requisitoIn.almacenamiento);
		requisitosRepository.save(requisitoObj);
		
	}
	
}
