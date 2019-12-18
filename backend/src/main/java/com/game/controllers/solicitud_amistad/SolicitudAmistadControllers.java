package com.game.controllers.solicitud_amistad;

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

import com.game.controllers.solicitud_amistad.dto.SolicitudAmistadItem;
import com.game.exceptions.ApiException;
import com.game.services.solicitud_amistad.SolicitudAmistadService;
import com.game.utils.ModelApiResponse;

/**
 * @author Juan Paggi. Controlador de SolicitudAmistad con get, post y delete.
 */

@RestController
@RequestMapping("${v1API}/solicitudes")
public class SolicitudAmistadControllers {

	@Autowired
	SolicitudAmistadService solicitudAmistadService;

	public static final Logger logger = LoggerFactory.getLogger(SolicitudAmistadControllers.class);

	@GetMapping(path = "{idSolicitud}")
	public @ResponseBody ResponseEntity<List<SolicitudAmistadItem>> getSolicitudes(
			@PathVariable("idSolicitud") String idSolicitud) {
		try {
			return new ResponseEntity<List<SolicitudAmistadItem>>(
					solicitudAmistadService.getAllSolicitudes(idSolicitud), HttpStatus.OK);
		} catch (Exception e) {
			logger.error("El servidor encontró una condición inesperada, no se pudo cumplir la solicitud", e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(path = "")
	public @ResponseBody ResponseEntity<ModelApiResponse> addSolicitud(@RequestBody SolicitudAmistadItem body) {
		ModelApiResponse respuesta = new ModelApiResponse();
		try {
			solicitudAmistadService.addSolicitud(body);
			respuesta.codigo("OK");
			respuesta.descripcion("Solicitud agregada correctamente");
			return new ResponseEntity<ModelApiResponse>(respuesta, HttpStatus.OK);
		} catch (ApiException e) {
			if (e.getCode() == 404) {
				logger.error(e.getMessage(), e);
				respuesta.codigo("ERROR");
				respuesta.descripcion(e.getMessage());
				return new ResponseEntity<ModelApiResponse>(respuesta, HttpStatus.NOT_FOUND);
			} else if (e.getCode() == 409) {
				logger.error(e.getMessage(), e);
				respuesta.codigo("ERROR");
				respuesta.descripcion(e.getMessage());
				return new ResponseEntity<ModelApiResponse>(respuesta, HttpStatus.CONFLICT);
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

	@DeleteMapping(path = "")
	public @ResponseBody ResponseEntity<ModelApiResponse> removeSolicitud(@RequestBody SolicitudAmistadItem body) {
		ModelApiResponse respuesta = new ModelApiResponse();
		try {
			solicitudAmistadService.removeSolicitud(body.id_usuario, body.id_amigo);
			respuesta.codigo("OK");
			respuesta.descripcion("Solicitud borrada correctamente");
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
