package com.game.controllers.requisitos;

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

import com.game.controllers.requisitos.dto.RequisitoItem;
import com.game.services.requisitos.RequisitosService;
import com.game.services.requisitos.exceptions.RequisitosNotFound;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * @author pachi
 * Controlador de Requisitos con get, post, put, y delete.
 * Tenemos dos Get, uno para devolver un requisito seleccionado
 * por su id y otro get para devolver todos los requisitos.
 */

@RestController
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST})
@RequestMapping("${v1API}/requisitos")
public class RequisitosControllers {

	public static Logger logger = LoggerFactory.getLogger(RequisitosControllers.class);

	@Autowired
	RequisitosService requisitosService;
	
	@GetMapping(path="")
	@ApiResponses({
		@ApiResponse(code = 200, message = "OK"),
		@ApiResponse(code = 404, message = "Not found."),
		@ApiResponse(code = 500, message = "Unexpected error.")
		})
	public @ResponseBody ResponseEntity<List<RequisitoItem>> getRequisitos(){
		try {
			return new ResponseEntity<List<RequisitoItem>>(requisitosService.getAllRequisitos(), HttpStatus.OK);
		} catch(ParseException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}catch(Exception e) {
			logger.error("Internal server error", e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping(path = "/{idRequisito}")
	@ApiResponses({ 
		@ApiResponse(code = 200, message = "OK"),
		@ApiResponse(code = 404, message = "Not found."),
		@ApiResponse(code = 500, message = "Unexpected error.") })
	public @ResponseBody ResponseEntity<RequisitoItem> getRequisitoByID(
			@PathVariable("idRequisito") String idRequisito){
		try {
			return new ResponseEntity<RequisitoItem>(
					requisitosService.getRequisito(Long.parseLong(idRequisito)),
					HttpStatus.OK);
		} catch (RequisitosNotFound e) {
			return new ResponseEntity<RequisitoItem>(HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			logger.error("Internal server error", e);
			return new ResponseEntity<RequisitoItem>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping(path="")
	@ApiResponses({
		@ApiResponse(code = 200, message = "OK, devuelve el id del requisito insertado"),
		@ApiResponse(code = 500, message = "Unexpected error.")
		})
	public @ResponseBody ResponseEntity<Long> addRequisito( @RequestBody RequisitoItem body){
		try {
			return new ResponseEntity<Long>(requisitosService.addRequisito(body), HttpStatus.OK);
		} catch(Exception e) {
			logger.error("Internal server error", e);
			return new ResponseEntity<Long>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping(path="/{idRequisito}")
	@ApiResponses({
		@ApiResponse(code = 200, message = "borrado OK"),
		@ApiResponse(code = 404, message = "Requisito inexistente."),
		@ApiResponse(code = 400, message = "Id no valido."),
		@ApiResponse(code = 500, message = "Unexpected error.")
		})
	public @ResponseBody ResponseEntity<Void> removeRequisito(@PathVariable("idRequisito") String idRequisito) {
		try {
			requisitosService.removeRequisito(idRequisito);
			return new ResponseEntity<Void>(HttpStatus.OK);
		} catch(RequisitosNotFound e) {
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		} catch(NumberFormatException e) {
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		} catch(Exception e) {
			logger.error("Internal server error", e);
			return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping(path="/{idRequisito}")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Editado OK"),
		@ApiResponse(code = 404, message = "Requisito inexistente."),
		@ApiResponse(code = 400, message = "Id no valido."),
		@ApiResponse(code = 500, message = "Unexpected error.")
		})
	public @ResponseBody ResponseEntity<Void> editRequisito(@PathVariable("idRequisito") String idRequisito, @RequestBody RequisitoItem body) {
		try {
			requisitosService.editRequisito( idRequisito, body);
			return new ResponseEntity<Void>(HttpStatus.OK);
		} catch(RequisitosNotFound e) {
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		} catch(NumberFormatException e) {
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		} catch(Exception e) {
			logger.error("Internal server error", e);
			return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}
