package com.game.controllers.analisis;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.game.controllers.analisis.dto.AnalisisItem;
import com.game.exceptions.ApiException;
import com.game.services.analisis.AnalisisService;
import com.game.utils.ModelApiResponse;

/**
 * @author Juan Paggi Controlador de Analisis con get, post, put, y delete.
 *         Tenemos dos Get, uno para devolver un analisis seleccionado por su id
 *         y otro get para devolver todos los analisis.
 */

@RestController
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST })
@RequestMapping("/analisis")
public class AnalisisControllers {

	public static final Logger logger = LoggerFactory.getLogger(AnalisisControllers.class);

	@Autowired
	AnalisisService analisisService;

	@GetMapping(path = "/{idAnalisis}")
	public @ResponseBody ResponseEntity<AnalisisItem> getAnalisisByID(@PathVariable("idAnalisis") String idAnalisis) {
		try {
			return new ResponseEntity<AnalisisItem>(analisisService.getAnalisis(Long.parseLong(idAnalisis)),
					HttpStatus.OK);
		} catch (ApiException e) {
			if (e.getCode() == 404) {
				logger.error(e.getMessage(), e);
				return new ResponseEntity<AnalisisItem>(HttpStatus.NOT_FOUND);
			} else {
				logger.error(e.getMessage(), e);
				return new ResponseEntity<AnalisisItem>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (Exception e) {
			logger.error("El servidor encontró una condición inesperada, no se pudo cumplir la solicitud", e);
			return new ResponseEntity<AnalisisItem>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(path = "")
	public @ResponseBody ResponseEntity<List<AnalisisItem>> getAnalisis() {
		try {
			return new ResponseEntity<List<AnalisisItem>>(analisisService.getAllAnalisis(), HttpStatus.OK);
		} catch (Exception e) {
			logger.error("El servidor encontró una condición inesperada, no se pudo cumplir la solicitud", e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(path = "")
	public @ResponseBody ResponseEntity<ModelApiResponse> addAnalisis(@RequestBody AnalisisItem body) {
		ModelApiResponse respuesta = new ModelApiResponse();
		try {
			analisisService.addAnalisis(body);
			respuesta.codigo("OK");
			respuesta.descripcion("Analisis agregado correctamente");
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

	@DeleteMapping(path = "/{idAnalisis}")
	public @ResponseBody ResponseEntity<ModelApiResponse> removeAnalisis(
			@PathVariable("idAnalisis") String idAnalisis) {
		ModelApiResponse respuesta = new ModelApiResponse();
		try {
			analisisService.removeAnalisis(idAnalisis);
			respuesta.codigo("OK");
			respuesta.descripcion("Analisis borrado correctamente");
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

	@PutMapping(path = "/{idAnalisis}")
	public @ResponseBody ResponseEntity<ModelApiResponse> editAnalisis(@PathVariable("idAnalisis") String idAnalisis,
			@RequestBody AnalisisItem body) {
		ModelApiResponse respuesta = new ModelApiResponse();
		try {
			analisisService.editAnalisis(idAnalisis, body);
			respuesta.codigo("OK");
			respuesta.descripcion("Analisis editado correctamente");
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
