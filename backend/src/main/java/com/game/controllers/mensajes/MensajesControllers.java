package com.game.controllers.mensajes;

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

import com.game.controllers.mensajes.dto.MensajeItem;
import com.game.exceptions.ApiException;
import com.game.services.mensajes.MensajesService;
import com.game.utils.ModelApiResponse;

/**
 * @author Juan Paggi
 * Controlador de Mensajes con get, post y delete.
 */

@RestController
@RequestMapping("/mensajes")
public class MensajesControllers {

	public static final Logger logger = LoggerFactory.getLogger(MensajesControllers.class);
	
	@Autowired
	MensajesService mensajesService;
	
	@GetMapping(path="{idEscritor}/{idLector}")
	public @ResponseBody ResponseEntity<List<MensajeItem>> getSolicitudes(@PathVariable("idEscritor") String idSolicitud,@PathVariable("idLector") String idLector){
		try {
			return new ResponseEntity<List<MensajeItem>>(mensajesService.getAllMensajes(idSolicitud, idLector), HttpStatus.OK);
		} catch(Exception e) {
			logger.error("El servidor encontró una condición inesperada, no se pudo cumplir la solicitud", e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping(path="")
	public @ResponseBody ResponseEntity<ModelApiResponse> addMensaje( @RequestBody MensajeItem body){
		ModelApiResponse respuesta = new ModelApiResponse();
		try {
			mensajesService.addMensaje(body);
			respuesta.codigo("OK");
			respuesta.descripcion("Mensaje agregado correctamente");
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
	
	@DeleteMapping(path="")
	public @ResponseBody ResponseEntity<ModelApiResponse> removeMensaje(@RequestBody MensajeItem body) {
		ModelApiResponse respuesta = new ModelApiResponse();
		try {
			mensajesService.removeMensaje(body.id_escritor, body.id_lector, body.cuerpo);
			respuesta.codigo("OK");
			respuesta.descripcion("Mensaje borrado correctamente");
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
