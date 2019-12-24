package com.game.controllers.comentarios;

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

import com.game.controllers.comentarios.dto.ComentarioItem;
import com.game.exceptions.ApiException;
import com.game.services.comentarios.ComentariosService;
import com.game.utils.ModelApiResponse;

/**
 * @author Juan Paggi. Controlador de Comentarios con get, post, put, y delete.
 */

@RestController
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST })
@RequestMapping("/comentarios")
public class ComentariosControllers {

	public static final Logger logger = LoggerFactory.getLogger(ComentariosControllers.class);

	@Autowired
	ComentariosService comentariosService;

	@GetMapping(path = "/{idComentario}")
	public @ResponseBody ResponseEntity<ComentarioItem> getComentarioByID(
			@PathVariable("idComentario") String idComentario) {
		try {
			return new ResponseEntity<ComentarioItem>(comentariosService.getComentario(Long.parseLong(idComentario)),
					HttpStatus.OK);
		} catch (ApiException e) {
			if (e.getCode() == 404) {
				logger.error(e.getMessage(), e);
				return new ResponseEntity<ComentarioItem>(HttpStatus.NOT_FOUND);
			} else {
				logger.error(e.getMessage(), e);
				return new ResponseEntity<ComentarioItem>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (Exception e) {
			logger.error("El servidor encontró una condición inesperada, no se pudo cumplir la solicitud", e);
			return new ResponseEntity<ComentarioItem>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(path = "")
	public @ResponseBody ResponseEntity<List<ComentarioItem>> getComentarios() {
		try {
			return new ResponseEntity<List<ComentarioItem>>(comentariosService.getAllComentarios(), HttpStatus.OK);
		} catch (Exception e) {
			logger.error("El servidor encontró una condición inesperada, no se pudo cumplir la solicitud", e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(path = "")
	public @ResponseBody ResponseEntity<ModelApiResponse> addComentario(@RequestBody ComentarioItem body) {
		ModelApiResponse respuesta = new ModelApiResponse();
		try {
			comentariosService.addComentario(body);
			respuesta.codigo("OK");
			respuesta.descripcion("Comentario agregado correctamente");
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

	@DeleteMapping(path = "/{idComentario}")
	public @ResponseBody ResponseEntity<ModelApiResponse> removeComentario(
			@PathVariable("idComentario") String idComentario) {
		ModelApiResponse respuesta = new ModelApiResponse();
		try {
			comentariosService.removeComentario(idComentario);
			respuesta.codigo("OK");
			respuesta.descripcion("Comentario borrado correctamente");
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

	@PutMapping(path = "/{idComentario}")
	public @ResponseBody ResponseEntity<ModelApiResponse> editComentario(
			@PathVariable("idComentario") String idComentario, @RequestBody ComentarioItem body) {
		ModelApiResponse respuesta = new ModelApiResponse();
		try {
			comentariosService.editComentario(idComentario, body);
			respuesta.codigo("OK");
			respuesta.descripcion("Comentario editado correctamente");
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
}
