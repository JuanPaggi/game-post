package com.game.controllers.juegos;

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

import com.game.controllers.juegos.dto.JuegoInput;
import com.game.controllers.juegos.dto.JuegoItem;
import com.game.exceptions.ApiException;
import com.game.services.juegos.JuegosService;
import com.game.services.juegos.exceptions.JuegosNotFound;
import com.game.services.tag.exceptions.TagNotFound;
import com.game.utils.ModelApiResponse;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * @author pachi
 * Controlador de Juegos con get, post, put, y delete.
 * Tenemos dos Get, uno para devolver un juego seleccionado
 * por su id y otro get para devolver todos los juegos.
 */

@RestController
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.DELETE,RequestMethod.POST})
@RequestMapping("${v1API}/juegos")
public class JuegosControllers {
	
	public static final Logger logger = LoggerFactory.getLogger(JuegosControllers.class);

	@Autowired
	JuegosService juegosService;

	@GetMapping(path = "/{idJuego}")
	public @ResponseBody ResponseEntity<JuegoItem> getJuegoByID(
			@PathVariable("idJuego") String idJuego){
		try {
			return new ResponseEntity<JuegoItem>(juegosService.getJuego(Long.parseLong(idJuego)),HttpStatus.OK);
		} catch (ApiException e) {
			if(e.getCode() == 404) {
				logger.error(e.getMessage(), e);
				return new ResponseEntity<JuegoItem>(HttpStatus.NOT_FOUND);
			}else{
				logger.error(e.getMessage(), e);
				return new ResponseEntity<JuegoItem>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (Exception e) {
			logger.error("El servidor encontró una condición inesperada, no se pudo cumplir la solicitud", e);
			return new ResponseEntity<JuegoItem>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping(path="")
	public @ResponseBody ResponseEntity<List<JuegoItem>> getJuegos(){
		try {
			return new ResponseEntity<List<JuegoItem>>(juegosService.getAllJuegos(), HttpStatus.OK);
		} catch(Exception e) {
			logger.error("El servidor encontró una condición inesperada, no se pudo cumplir la solicitud", e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping(path="")
	public @ResponseBody ResponseEntity<ModelApiResponse> addJuego( @RequestBody JuegoInput body){
		ModelApiResponse respuesta = new ModelApiResponse();
		try {
			juegosService.addJuego(body);
			respuesta.codigo("OK");
			respuesta.descripcion("Analisis agregado correctamente");
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
	
	@DeleteMapping(path="/{idJuego}")
	@ApiResponses({
		@ApiResponse(code = 200, message = "borrado OK"),
		@ApiResponse(code = 404, message = "Juego inexistente."),
		@ApiResponse(code = 400, message = "Id no valido."),
		@ApiResponse(code = 500, message = "Unexpected error.")
		})
	public @ResponseBody ResponseEntity<Void> removeJuego(@PathVariable("idJuego") String idJuego) {
		try {
			juegosService.removeJuego(idJuego);
			return new ResponseEntity<Void>(HttpStatus.OK);
		} catch(JuegosNotFound e) {
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		} catch(NumberFormatException e) {
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		} catch(Exception e) {
			logger.error("Internal server error", e);
			return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping(path="/{idJuego}")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Editado OK"),
		@ApiResponse(code = 404, message = "Juego inexistente."),
		@ApiResponse(code = 400, message = "Id no valido."),
		@ApiResponse(code = 500, message = "Unexpected error.")
		})
	public @ResponseBody ResponseEntity<Void> editJuego(@PathVariable("idJuego") String idJuego, @RequestBody JuegoInput body) {
		try {
			juegosService.editJuego( idJuego, body);
			return new ResponseEntity<Void>(HttpStatus.OK);
		} catch(JuegosNotFound e) {
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		} catch(TagNotFound e) {
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		} catch(NumberFormatException e) {
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		} catch(Exception e) {
			logger.error("Internal server error", e);
			return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}
