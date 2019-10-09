package com.game.controllers.usuarios;

import java.text.ParseException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.game.controllers.usuarios.dto.UsuarioItem;
import com.game.services.usuarios.UsuariosService;
import com.game.services.usuarios.exceptions.UsuariosNotFound;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * @author pachi
 *
 */

@RestController
@RequestMapping("${v1API}/usuarios")
public class UsuariosControllers {

	public static Logger logger = LoggerFactory.getLogger(UsuariosControllers.class);
	
	@Autowired
	UsuariosService usuariosService;
	
	@GetMapping(path = "/{idUsuario}")
	@ApiResponses({ 
		@ApiResponse(code = 200, message = "OK"),
		@ApiResponse(code = 404, message = "Not found."),
		@ApiResponse(code = 500, message = "Unexpected error.") })
	public @ResponseBody ResponseEntity<UsuarioItem> getUsuarioByID(
			@PathVariable("idUsuario") String idUsuario){
		try {
			return new ResponseEntity<UsuarioItem>(
					usuariosService.getUsuario(Long.parseLong(idUsuario)),
					HttpStatus.OK);
		} catch (UsuariosNotFound e) {
			return new ResponseEntity<UsuarioItem>(HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			logger.error("Internal server error", e);
			return new ResponseEntity<UsuarioItem>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping(path="")
	@ApiResponses({
		@ApiResponse(code = 200, message = "OK"),
		@ApiResponse(code = 404, message = "Not found."),
		@ApiResponse(code = 500, message = "Unexpected error.")
		})
	public @ResponseBody ResponseEntity<List<UsuarioItem>> getUsuarios(){
		try {
			return new ResponseEntity<List<UsuarioItem>>(usuariosService.getAllUsuarios(), HttpStatus.OK);
		} catch(ParseException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}catch(Exception e) {
			logger.error("Internal server error", e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping(path="")
	@ApiResponses({
		@ApiResponse(code = 200, message = "OK, devuelve el id del usuario insertado"),
		@ApiResponse(code = 500, message = "Unexpected error.")
		})
	public @ResponseBody ResponseEntity<Long> addUsuarios( @RequestBody UsuarioItem body){
		try {
			return new ResponseEntity<Long>(usuariosService.addUsuario(body), HttpStatus.OK);
		} catch(Exception e) {
			logger.error("Internal server error", e);
			return new ResponseEntity<Long>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}
