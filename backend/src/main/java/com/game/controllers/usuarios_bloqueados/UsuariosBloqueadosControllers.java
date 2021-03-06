package com.game.controllers.usuarios_bloqueados;

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

import com.game.controllers.juegos.JuegosControllers;
import com.game.controllers.usuarios_bloqueados.dto.UsuarioBloqueadoItem;
import com.game.services.usuarios.exceptions.UsuariosNotFound;
import com.game.services.usuarios_bloqueados.UsuariosBloqueadosService;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * @author pachi
 * Controlador de Usuarios_bloqueados con get, post y delete.
 * Tenemos dos Get, uno para devolver un juego seleccionado
 * por su id y otro get para devolver todos los juegos.
 */

@RestController
@RequestMapping("${v1API}/usuariosBloqueados")
public class UsuariosBloqueadosControllers {

	public static Logger logger = LoggerFactory.getLogger(JuegosControllers.class);

	@Autowired
	UsuariosBloqueadosService usuariosBloqueadosService;
	
	@GetMapping(path="")
	@ApiResponses({
		@ApiResponse(code = 200, message = "OK"),
		@ApiResponse(code = 404, message = "Not found."),
		@ApiResponse(code = 500, message = "Unexpected error.")
		})
	public @ResponseBody ResponseEntity<List<UsuarioBloqueadoItem>> getUsuariosBloqueados(){
		try {
			return new ResponseEntity<List<UsuarioBloqueadoItem>>(usuariosBloqueadosService.getAllUsuarios(), HttpStatus.OK);
		} catch(ParseException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}catch(Exception e) {
			logger.error("Internal server error", e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping(path="")
	@ApiResponses({
		@ApiResponse(code = 200, message = "OK, devuelve el id del usuario_bloqueado insertado"),
		@ApiResponse(code = 500, message = "Unexpected error.")
		})
	public @ResponseBody ResponseEntity<Long> addUsuarioBloqueado( @RequestBody UsuarioBloqueadoItem body){
		try {
			return new ResponseEntity<Long>(usuariosBloqueadosService.addUsuarioBloqueado(body), HttpStatus.OK);
		} catch(Exception e) {
			logger.error("Internal server error", e);
			return new ResponseEntity<Long>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping(path="/{idUsuarioBloqueado}")
	@ApiResponses({
		@ApiResponse(code = 200, message = "borrado OK"),
		@ApiResponse(code = 404, message = "Usuario inexistente."),
		@ApiResponse(code = 400, message = "Id no valido."),
		@ApiResponse(code = 500, message = "Unexpected error.")
		})
	public @ResponseBody ResponseEntity<Void> removeUsuarioBloqueado(@PathVariable("idUsuarioBloqueado") String idUsuarioBloqueado) {
		try {
			usuariosBloqueadosService.removeUsuarioBloqueado(idUsuarioBloqueado);
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
