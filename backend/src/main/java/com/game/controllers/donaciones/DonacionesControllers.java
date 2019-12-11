package com.game.controllers.donaciones;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.game.controllers.donaciones.dto.DonacionesItem;
import com.game.exceptions.ApiException;
import com.game.services.donaciones.DonacionesService;
import com.game.utils.ModelApiResponse;

/**
 * @author Juan Paggi
 * Controlador de Donaciones con get, post y delete.
 */

@RestController
@RequestMapping("${v1API}/donaciones")
public class DonacionesControllers {
	
	public static final Logger logger = LoggerFactory.getLogger(DonacionesControllers.class);
	
	@Autowired
	DonacionesService donacionesService;
	
	@GetMapping(path = "/{idDonacion}")
	public @ResponseBody ResponseEntity<List<DonacionesItem>> getDonacionesByID(@PathVariable("idDonacion") String idDonacion){
		try {
			return new ResponseEntity<List<DonacionesItem>>(donacionesService.getDonaciones(Long.parseLong(idDonacion)),HttpStatus.OK);
		} catch (ApiException e) {
			if(e.getCode() == 404) {
				logger.error(e.getMessage(), e);
				return new ResponseEntity<List<DonacionesItem>>(HttpStatus.NOT_FOUND);
			}else{
				logger.error(e.getMessage(), e);
				return new ResponseEntity<List<DonacionesItem>>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (Exception e) {
			logger.error("El servidor encontró una condición inesperada, no se pudo cumplir la solicitud", e);
			return new ResponseEntity<List<DonacionesItem>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping(path="")
	public @ResponseBody ResponseEntity<ModelApiResponse> addDonacion( @RequestBody DonacionesItem body){
		ModelApiResponse respuesta = new ModelApiResponse();
		try {
			donacionesService.addDonacion(body);
			respuesta.codigo("OK");
			respuesta.descripcion("Donacion agregada correctamente");
			return new ResponseEntity<ModelApiResponse>(respuesta , HttpStatus.OK);
		} catch (ApiException e) {
			if (e.getCode() == 404) {
				logger.error(e.getMessage(), e);
				respuesta.codigo("ERROR");
				respuesta.descripcion(e.getMessage());
				return new ResponseEntity<ModelApiResponse>(respuesta, HttpStatus.NOT_FOUND);
			} else {
				logger.error(e.getMessage(), e);
				respuesta.codigo("ERROR");
				respuesta.descripcion(e.getMessage());
				return new ResponseEntity<ModelApiResponse>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (Exception e) {
			logger.error("El servidor encontró una condición inesperada, no se pudo cumplir la solicitud", e);
			respuesta.codigo("ERROR");
			respuesta.descripcion(e.getMessage());
			return new ResponseEntity<ModelApiResponse>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping(path="/{idDonacion}")
	public @ResponseBody ResponseEntity<ModelApiResponse> removeDoancion(@PathVariable("idDonacion") String idDonacion) {
		ModelApiResponse respuesta = new ModelApiResponse();
		try {
			donacionesService.removeDonacion(idDonacion);
			respuesta.codigo("OK");
			respuesta.descripcion("Donacion borrada correctamente");
			return new ResponseEntity<ModelApiResponse>(respuesta, HttpStatus.OK);
		} catch (ApiException e) {
			if (e.getCode() == 404) {
				logger.error(e.getMessage(), e);
				respuesta.codigo("ERROR");
				respuesta.descripcion(e.getMessage());
				return new ResponseEntity<ModelApiResponse>(respuesta, HttpStatus.NOT_FOUND);
			} else {
				logger.error(e.getMessage(), e);
				respuesta.codigo("ERROR");
				respuesta.descripcion(e.getMessage());
				return new ResponseEntity<ModelApiResponse>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (Exception e) {
			logger.error("El servidor encontró una condición inesperada, no se pudo cumplir la solicitud", e);
			respuesta.codigo("ERROR");
			respuesta.descripcion(e.getMessage());
			return new ResponseEntity<ModelApiResponse>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}
