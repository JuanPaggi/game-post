package com.game.controllers.comentarios;

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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.game.controllers.comentarios.dto.ComentarioItem;
import com.game.controllers.noticias.NoticiasControllers;
import com.game.controllers.noticias.dto.NoticiaItem;
import com.game.services.comentarios.ComentariosService;
import com.game.services.comentarios.exceptions.ComentariosNotFound;
import com.game.services.noticias.NoticiasService;
import com.game.services.noticias.exceptions.NoticiasNotFound;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * @author negro
 * Controlador de Comentarios con get, post, put, y delete.
 */

@RestController
@RequestMapping("${v1API}/comentarios")
public class ComentariosControllers {

	public static Logger logger = LoggerFactory.getLogger(ComentariosControllers.class);

	@Autowired
	ComentariosService comentariosService;

	@GetMapping(path="")
	@ApiResponses({
		@ApiResponse(code = 200, message = "OK"),
		@ApiResponse(code = 404, message = "Not found."),
		@ApiResponse(code = 500, message = "Unexpected error.")
		})
	public @ResponseBody ResponseEntity<List<ComentarioItem>> getComentarios(){
		try {
			return new ResponseEntity<List<ComentarioItem>>(comentariosService.getAllComentarios(), HttpStatus.OK);
		} catch(ParseException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}catch(Exception e) {
			logger.error("Internal server error", e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping(path = "/{idComentario}")
	@ApiResponses({ 
		@ApiResponse(code = 200, message = "OK"),
		@ApiResponse(code = 404, message = "Not found."),
		@ApiResponse(code = 500, message = "Unexpected error.") })
	public @ResponseBody ResponseEntity<ComentarioItem> getComentarioByID(
			@PathVariable("idComentario") String idComentario){
		try {
			return new ResponseEntity<ComentarioItem>(
					comentariosService.getComentario(Long.parseLong(idComentario)),
					HttpStatus.OK);
		} catch (ComentariosNotFound e) {
			return new ResponseEntity<ComentarioItem>(HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			logger.error("Internal server error", e);
			return new ResponseEntity<ComentarioItem>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping(path="")
	@ApiResponses({
		@ApiResponse(code = 200, message = "OK, devuelve el id del comentario insertado"),
		@ApiResponse(code = 500, message = "Unexpected error.")
		})
	public @ResponseBody ResponseEntity<Long> addComentario( @RequestBody ComentarioItem body){
		try {
			return new ResponseEntity<Long>(comentariosService.addComentario(body), HttpStatus.OK);
		} catch(Exception e) {
			logger.error("Internal server error", e);
			return new ResponseEntity<Long>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping(path="/{idComentario}")
	@ApiResponses({
		@ApiResponse(code = 200, message = "borrado OK"),
		@ApiResponse(code = 404, message = "Comentario inexistente."),
		@ApiResponse(code = 400, message = "Id no valido."),
		@ApiResponse(code = 500, message = "Unexpected error.")
		})
	public @ResponseBody ResponseEntity<Void> removeComentario(@PathVariable("idComentario") String idComentario) {
		try {
			comentariosService.removeComentario(idComentario);
			return new ResponseEntity<Void>(HttpStatus.OK);
		} catch(ComentariosNotFound e) {
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		} catch(NumberFormatException e) {
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		} catch(Exception e) {
			logger.error("Internal server error", e);
			return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping(path="/{idComentario}")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Editado OK"),
		@ApiResponse(code = 404, message = "Comentario inexistente."),
		@ApiResponse(code = 400, message = "Id no valido."),
		@ApiResponse(code = 500, message = "Unexpected error.")
		})
	public @ResponseBody ResponseEntity<Void> editComentario(@PathVariable("idComentario") String idComentario, @RequestBody ComentarioItem body) {
		try {
			comentariosService.editComentario( idComentario, body);
			return new ResponseEntity<Void>(HttpStatus.OK);
		} catch(ComentariosNotFound e) {
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		} catch(NumberFormatException e) {
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		} catch(Exception e) {
			logger.error("Internal server error", e);
			return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
