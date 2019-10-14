package com.game.controllers.modos;

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

import com.game.controllers.modos.dto.ModoItem;
import com.game.services.modos.ModosService;
import com.game.services.modos.exceptions.ModosNotFound;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * @author negro
 * Controlador de Modos con get, post y delete.
 */

@RestController
@RequestMapping("${v1API}/modos")
public class ModosControllers {
	
	public static Logger logger = LoggerFactory.getLogger(ModosControllers.class);

	@Autowired
	ModosService modosService;
	
	@GetMapping(path="")
	@ApiResponses({
		@ApiResponse(code = 200, message = "OK"),
		@ApiResponse(code = 404, message = "Not found."),
		@ApiResponse(code = 500, message = "Unexpected error.")
		})
	public @ResponseBody ResponseEntity<List<ModoItem>> getModos(){
		try {
			return new ResponseEntity<List<ModoItem>>(modosService.getAllModos(), HttpStatus.OK);
		} catch(ParseException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}catch(Exception e) {
			logger.error("Internal server error", e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping(path="")
	@ApiResponses({
		@ApiResponse(code = 200, message = "OK, devuelve el id del modo insertado"),
		@ApiResponse(code = 500, message = "Unexpected error.")
		})
	public @ResponseBody ResponseEntity<Long> addModo( @RequestBody ModoItem body){
		try {
			return new ResponseEntity<Long>(modosService.addModo(body), HttpStatus.OK);
		} catch(Exception e) {
			logger.error("Internal server error", e);
			return new ResponseEntity<Long>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping(path="/{idModo}")
	@ApiResponses({
		@ApiResponse(code = 200, message = "borrado OK"),
		@ApiResponse(code = 404, message = "Modo inexistente."),
		@ApiResponse(code = 400, message = "Id no valido."),
		@ApiResponse(code = 500, message = "Unexpected error.")
		})
	public @ResponseBody ResponseEntity<Void> removeModo(@PathVariable("idModo") String idModo) {
		try {
			modosService.removeModo(idModo);
			return new ResponseEntity<Void>(HttpStatus.OK);
		} catch(ModosNotFound e) {
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		} catch(NumberFormatException e) {
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		} catch(Exception e) {
			logger.error("Internal server error", e);
			return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
