package com.game.controllers.lista_amigos;

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

import com.game.controllers.lista_amigos.dto.ListaAmigosItem;
import com.game.services.lista_amigos.ListaAmigosService;
import com.game.services.usuarios.exceptions.UsuariosNotFound;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * @author pachi
 *
 */

@RestController
@RequestMapping("${v1API}/amigos")
public class ListaAmigosControllers {
	
	@Autowired
	ListaAmigosService listaAmigosService;
	
	public static Logger logger = LoggerFactory.getLogger(ListaAmigosControllers.class);
	
	@GetMapping(path="{idUsuario}")
	@ApiResponses({
		@ApiResponse(code = 200, message = "OK"),
		@ApiResponse(code = 400, message = "Id no valido."),
		@ApiResponse(code = 404, message = "Not found."),
		@ApiResponse(code = 500, message = "Unexpected error.")
		})
	public @ResponseBody ResponseEntity<List<ListaAmigosItem>> getUsuarios(@PathVariable("idUsuario") String idUsuario){
		try {
			return new ResponseEntity<List<ListaAmigosItem>>(listaAmigosService.getAllAmigos(idUsuario), HttpStatus.OK);
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
		@ApiResponse(code = 200, message = "OK, devuelve el id del usuario insertado"),
		@ApiResponse(code = 404, message = "Usuario inexistente."),
		@ApiResponse(code = 500, message = "Unexpected error.")
		})
	public @ResponseBody ResponseEntity<Void> addAmigos( @RequestBody ListaAmigosItem body){
		try {
			listaAmigosService.addAmigo(body);
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
	public @ResponseBody ResponseEntity<Void> removeUsuario(@RequestBody ListaAmigosItem body) {
		try {
			listaAmigosService.removeAmigo(body.id_usuario, body.id_amigo);
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
