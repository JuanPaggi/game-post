package com.game.controllers.noticias;

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

import com.game.controllers.noticias.dto.NoticiaInput;
import com.game.controllers.noticias.dto.NoticiaItem;
import com.game.exceptions.ApiException;
import com.game.services.noticias.NoticiasService;
import com.game.utils.ModelApiResponse;

/**
 * @author Juan Paggi. Controlador de Noticias con get, post, put, y delete.
 */

@RestController
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE })
@RequestMapping("/noticias")
public class NoticiasControllers {

	public static final Logger logger = LoggerFactory.getLogger(NoticiasControllers.class);

	@Autowired
	NoticiasService noticiasService;

	@GetMapping(path = "/{idNoticia}")
	public @ResponseBody ResponseEntity<NoticiaItem> getNoticiaByID(@PathVariable("idNoticia") String idNoticia) {
		try {
			return new ResponseEntity<NoticiaItem>(noticiasService.getNoticias(Long.parseLong(idNoticia)),
					HttpStatus.OK);
		} catch (ApiException e) {
			if (e.getCode() == 404) {
				logger.error(e.getMessage(), e);
				return new ResponseEntity<NoticiaItem>(HttpStatus.NOT_FOUND);
			} else {
				logger.error(e.getMessage(), e);
				return new ResponseEntity<NoticiaItem>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (Exception e) {
			logger.error("El servidor encontró una condición inesperada, no se pudo cumplir la solicitud", e);
			return new ResponseEntity<NoticiaItem>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(path = "")
	public @ResponseBody ResponseEntity<List<NoticiaItem>> getNoticias() {
		try {
			return new ResponseEntity<List<NoticiaItem>>(noticiasService.getAllNoticias(), HttpStatus.OK);
		} catch (Exception e) {
			logger.error("El servidor encontró una condición inesperada, no se pudo cumplir la solicitud", e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(path = "")
	public @ResponseBody ResponseEntity<ModelApiResponse> addNoticia(@RequestBody NoticiaInput body) {
		ModelApiResponse respuesta = new ModelApiResponse();
		try {
			noticiasService.addNoticia(body);
			respuesta.codigo("OK");
			respuesta.descripcion("Noticia agregado correctamente");
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

	@DeleteMapping(path = "/{idNoticia}")
	public @ResponseBody ResponseEntity<ModelApiResponse> removeNoticia(@PathVariable("idNoticia") String idNoticia) {
		ModelApiResponse respuesta = new ModelApiResponse();
		try {
			noticiasService.removeNoticia(idNoticia);
			respuesta.codigo("OK");
			respuesta.descripcion("Noticia borrada correctamente");
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

	@PutMapping(path = "/{idNoticia}")
	public @ResponseBody ResponseEntity<ModelApiResponse> editNoticia(@PathVariable("idNoticia") String idNoticia,
			@RequestBody NoticiaInput body) {
		ModelApiResponse respuesta = new ModelApiResponse();
		try {
			noticiasService.editNoticia(idNoticia, body);
			respuesta.codigo("OK");
			respuesta.descripcion("Noticia editada correctamente");
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
