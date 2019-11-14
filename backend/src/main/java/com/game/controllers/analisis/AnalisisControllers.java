package com.game.controllers.analisis;

import java.text.ParseException;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.game.controllers.analisis.dto.AnalisisItem;
import com.game.services.analisis.AnalisisService;
import com.game.services.analisis.exceptions.AnalisisNotFound;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * @author pachi
 * Controlador de Analisis con get, post, put, y delete.
 * Tenemos dos Get, uno para devolver un analisis seleccionado
 * por su id y otro get para devolver todos los analisis.
 */

@RestController
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST})
@RequestMapping("${v1API}/analisis")
public class AnalisisControllers {
	
	public static Logger logger = LoggerFactory.getLogger(AnalisisControllers.class);
	
	@Autowired
	AnalisisService analisisService;
	
	@GetMapping(path="")
	@ApiResponses({
		@ApiResponse(code = 200, message = "OK"),
		@ApiResponse(code = 404, message = "Not found."),
		@ApiResponse(code = 500, message = "Unexpected error.")
		})
	public @ResponseBody ResponseEntity<List<AnalisisItem>> getAnalisis(){
		try {
			return new ResponseEntity<List<AnalisisItem>>(analisisService.getAllAnalisis(), HttpStatus.OK);
		} catch(ParseException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}catch(Exception e) {
			logger.error("Internal server error", e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping(path = "/{idAnalisis}")
	@ApiResponses({ 
		@ApiResponse(code = 200, message = "OK"),
		@ApiResponse(code = 404, message = "Not found."),
		@ApiResponse(code = 500, message = "Unexpected error.") })
	public @ResponseBody ResponseEntity<AnalisisItem> getAnalisisByID(
			@PathVariable("idAnalisis") String idAnalisis){
		try {
			return new ResponseEntity<AnalisisItem>(
					analisisService.getAnalisis(Long.parseLong(idAnalisis)),
					HttpStatus.OK);
		} catch (AnalisisNotFound e) {
			return new ResponseEntity<AnalisisItem>(HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			logger.error("Internal server error", e);
			return new ResponseEntity<AnalisisItem>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping(path="")
	@ApiResponses({
		@ApiResponse(code = 200, message = "OK, devuelve el id del analisis insertado"),
		@ApiResponse(code = 500, message = "Unexpected error.")
		})
	public @ResponseBody ResponseEntity<Long> addAnalisis( @RequestBody AnalisisItem body){
		try {
			return new ResponseEntity<Long>(analisisService.addAnalisis(body), HttpStatus.OK);
		} catch(Exception e) {
			logger.error("Internal server error", e);
			return new ResponseEntity<Long>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping(path="/{idAnalisis}")
	@ApiResponses({
		@ApiResponse(code = 200, message = "borrado OK"),
		@ApiResponse(code = 404, message = "Analisis inexistente."),
		@ApiResponse(code = 400, message = "Id no valido."),
		@ApiResponse(code = 500, message = "Unexpected error.")
		})
	public @ResponseBody ResponseEntity<Void> removeAnalisis(@PathVariable("idAnalisis") String idAnalisis) {
		try {
			analisisService.removeAnalisis(idAnalisis);
			return new ResponseEntity<Void>(HttpStatus.OK);
		} catch(AnalisisNotFound e) {
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		} catch(NumberFormatException e) {
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		} catch(Exception e) {
			logger.error("Internal server error", e);
			return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping(path="/{idAnalisis}")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Editado OK"),
		@ApiResponse(code = 404, message = "Analisis inexistente."),
		@ApiResponse(code = 400, message = "Id no valido."),
		@ApiResponse(code = 500, message = "Unexpected error.")
		})
	public @ResponseBody ResponseEntity<Void> editAnalisis(@PathVariable("idAnalisis") String idAnalisis, @RequestBody AnalisisItem body) {
		try {
			analisisService.editAnalisis( idAnalisis, body);
			return new ResponseEntity<Void>(HttpStatus.OK);
		} catch(AnalisisNotFound e) {
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		} catch(NumberFormatException e) {
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		} catch(Exception e) {
			logger.error("Internal server error", e);
			return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}
