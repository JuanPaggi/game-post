package com.game.controllers.modos;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.game.controllers.modos.dto.ModoItem;
import com.game.exceptions.ApiException;
import com.game.services.modos.ModosService;
import com.game.utils.ModelApiResponse;

/**
 * @author Juan Paggi. Controlador de Modos con get, post y delete.
 */

@RestController
@CrossOrigin(origins = "*", methods = { RequestMethod.GET })
@RequestMapping("/modos")
public class ModosControllers {

	public static final Logger logger = LoggerFactory.getLogger(ModosControllers.class);

	@Autowired
	ModosService modosService;

	@GetMapping(path = "")
	public @ResponseBody ResponseEntity<List<ModoItem>> getModos() {
		try {
			return new ResponseEntity<List<ModoItem>>(modosService.getAllModos(), HttpStatus.OK);
		} catch (Exception e) {
			logger.error("El servidor encontró una condición inesperada, no se pudo cumplir la solicitud", e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(path = "")
	public @ResponseBody ResponseEntity<ModelApiResponse> addModo(@RequestBody ModoItem body) {
		ModelApiResponse respuesta = new ModelApiResponse();
		try {
			modosService.addModo(body);
			respuesta.codigo("OK");
			respuesta.descripcion("Modo agregado correctamente");
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

	@DeleteMapping(path = "/{idModo}")
	public @ResponseBody ResponseEntity<ModelApiResponse> removeModo(@PathVariable("idModo") String idModo) {
		ModelApiResponse respuesta = new ModelApiResponse();
		try {
			modosService.removeModo(idModo);
			respuesta.codigo("OK");
			respuesta.descripcion("Modo borrado correctamente");
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
