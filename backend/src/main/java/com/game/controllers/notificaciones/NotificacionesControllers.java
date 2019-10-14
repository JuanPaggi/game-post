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
import com.game.services.notificaciones.NotificacionesService;
import com.game.services.usuarios.exceptions.UsuariosNotFound;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * @author pachi
 * Controlador de Donaciones con get, post y delete.
 */

@RestController
@RequestMapping("${v1API}/notificaciones")
public class NotificacionesControllers {
	
	public static Logger logger = LoggerFactory.getLogger(NotificacionesControllers.class);
	
	@Autowired
	NotificacionesService notificacionesService;
	
	@GetMapping(path = "/{idNotificacion}")
	@ApiResponses({ 
		@ApiResponse(code = 200, message = "OK"),
		@ApiResponse(code = 404, message = "Not found."),
		@ApiResponse(code = 500, message = "Unexpected error.") })
	public @ResponseBody ResponseEntity<List<NotificacionItem>> getNotificacionesByID(
			@PathVariable("idNotificacion") String idNotificacion){
		try {
			return new ResponseEntity<List<NotificacionItem>>(
					notificacionesService.getNotificaciones(Long.parseLong(idNotificacion)),
					HttpStatus.OK);
		} catch (UsuariosNotFound e) {
			return new ResponseEntity<List<NotificacionItem>>(HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			logger.error("Internal server error", e);
			return new ResponseEntity<List<NotificacionItem>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping(path="")
	@ApiResponses({
		@ApiResponse(code = 200, message = "OK, devuelve el id de la notificacion insertada"),
		@ApiResponse(code = 500, message = "Unexpected error.")
		})
	public @ResponseBody ResponseEntity<Long> addNotificacion( @RequestBody NotificacionItem body){
		try {
			return new ResponseEntity<Long>(notificacionesService.addNotificacion(body), HttpStatus.OK);
		} catch(Exception e) {
			logger.error("Internal server error", e);
			return new ResponseEntity<Long>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping(path="/{idNotificacion}")
	@ApiResponses({
		@ApiResponse(code = 200, message = "borrado OK"),
		@ApiResponse(code = 404, message = "Notificacion inexistente."),
		@ApiResponse(code = 400, message = "Id no valido."),
		@ApiResponse(code = 500, message = "Unexpected error.")
		})
	public @ResponseBody ResponseEntity<Void> removeNotificacion(@PathVariable("idNotificacion") String idNotificacion) {
		try {
			notificacionesService.removeNotificacion(idNotificacion);
			return new ResponseEntity<Void>(HttpStatus.OK);
		} catch(UsuariosNotFound e) {
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		} catch(NumberFormatException e) {
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		} catch(Exception e) {
			logger.error("Internal server error", e);
			return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}
