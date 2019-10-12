package com.game.controllers.mensajes;

import java.text.ParseException;
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
import com.game.services.mensajes.MensajesService;
import com.game.services.usuarios.exceptions.UsuariosNotFound;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * @author pachi
 * Controlador de Mensajes con get, post y delete.
 */

@RestController
@RequestMapping("${v1API}/mensajes")
public class MensajesControllers {

	public static Logger logger = LoggerFactory.getLogger(MensajesControllers.class);
	
	@Autowired
	MensajesService mensajesService;
	
	@GetMapping(path="{idEscritor}/{idLector}")
	@ApiResponses({
		@ApiResponse(code = 200, message = "OK"),
		@ApiResponse(code = 400, message = "Id no valido."),
		@ApiResponse(code = 404, message = "Not found."),
		@ApiResponse(code = 500, message = "Unexpected error.")
		})
	public @ResponseBody ResponseEntity<List<MensajeItem>> getSolicitudes(@PathVariable("idEscritor") String idSolicitud,@PathVariable("idLector") String idLector){
		try {
			return new ResponseEntity<List<MensajeItem>>(mensajesService.getAllMensajes(idSolicitud, idLector), HttpStatus.OK);
		} catch(ParseException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch(UsuariosNotFound e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}catch(Exception e) {
			logger.error("Internal server error", e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping(path="")
	@ApiResponses({
		@ApiResponse(code = 200, message = "OK, devuelve el id del mensaje insertado"),
		@ApiResponse(code = 404, message = "Usuario inexistente."),
		@ApiResponse(code = 500, message = "Unexpected error.")
		})
	public @ResponseBody ResponseEntity<Void> addMensaje( @RequestBody MensajeItem body){
		try {
			mensajesService.addMensaje(body);
			return new ResponseEntity<Void>(HttpStatus.OK);
		} catch(UsuariosNotFound e) {
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		} catch(Exception e) {
			logger.error("Internal server error", e);
			return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping(path="")
	@ApiResponses({
		@ApiResponse(code = 200, message = "borrado OK"),
		@ApiResponse(code = 404, message = "Usuario inexistente."),
		@ApiResponse(code = 400, message = "Id no valido."),
		@ApiResponse(code = 500, message = "Unexpected error.")
		})
	public @ResponseBody ResponseEntity<Void> removeMensaje(@RequestBody MensajeItem body) {
		try {
			mensajesService.removeMensaje(body.id_escritor, body.id_lector, body.cuerpo);
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
