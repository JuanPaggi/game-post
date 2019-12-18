package com.game.controllers.notificaciones;

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

import com.game.controllers.notificaciones.dto.NotificacionItem;
import com.game.exceptions.ApiException;
import com.game.services.notificaciones.NotificacionesService;
import com.game.utils.ModelApiResponse;

/**
 * @author Juan Paggi. Controlador de Donaciones con get, post y delete.
 */

@RestController
@RequestMapping("${v1API}/notificaciones")
public class NotificacionesControllers {

	public static final Logger logger = LoggerFactory.getLogger(NotificacionesControllers.class);

	@Autowired
	NotificacionesService notificacionesService;

	@GetMapping(path = "/{idNotificacion}")
	public @ResponseBody ResponseEntity<List<NotificacionItem>> getNotificacionesByID(
			@PathVariable("idNotificacion") String idNotificacion) {
		try {
			return new ResponseEntity<List<NotificacionItem>>(
					notificacionesService.getNotificaciones(Long.parseLong(idNotificacion)), HttpStatus.OK);
		} catch (Exception e) {
			logger.error("El servidor encontró una condición inesperada, no se pudo cumplir la solicitud", e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(path = "")
	public @ResponseBody ResponseEntity<ModelApiResponse> addNotificacion(@RequestBody NotificacionItem body) {
		ModelApiResponse respuesta = new ModelApiResponse();
		try {
			notificacionesService.addNotificacion(body);
			respuesta.codigo("OK");
			respuesta.descripcion("Notificacion agregada correctamente");
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

	@DeleteMapping(path = "/{idNotificacion}")
	public @ResponseBody ResponseEntity<ModelApiResponse> removeNotificacion(
			@PathVariable("idNotificacion") String idNotificacion) {
		ModelApiResponse respuesta = new ModelApiResponse();
		try {
			notificacionesService.removeNotificacion(idNotificacion);
			respuesta.codigo("OK");
			respuesta.descripcion("Notificacion borrado correctamente");
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
