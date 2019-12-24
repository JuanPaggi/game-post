package com.game.controllers.privilegios;

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

import com.game.controllers.privilegios.dto.PrivilegioItem;
import com.game.exceptions.ApiException;
import com.game.services.privilegios.PrivilegiosService;
import com.game.utils.ModelApiResponse;

/**
 * @author Juan Paggi. Controlador de Usuarios con get, post y delete.
 */

@RestController
@RequestMapping("/privilegios")
public class PrivilegiosControllers {

	public static final Logger logger = LoggerFactory.getLogger(PrivilegiosControllers.class);

	@Autowired
	PrivilegiosService privilegiosService;

	@GetMapping(path = "")
	public @ResponseBody ResponseEntity<List<PrivilegioItem>> getPrivilegios() {
		try {
			return new ResponseEntity<List<PrivilegioItem>>(privilegiosService.getAllPrivilegios(), HttpStatus.OK);
		} catch (Exception e) {
			logger.error("El servidor encontró una condición inesperada, no se pudo cumplir la solicitud", e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(path = "")
	public @ResponseBody ResponseEntity<ModelApiResponse> addPrivilegios(@RequestBody PrivilegioItem body) {
		ModelApiResponse respuesta = new ModelApiResponse();
		try {
			privilegiosService.addPrivilegio(body);
			respuesta.codigo("OK");
			respuesta.descripcion("Privilegio agregado correctamente");
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

	@DeleteMapping(path = "/{idPrivilegio}")
	public @ResponseBody ResponseEntity<ModelApiResponse> removePrivilegio(
			@PathVariable("idPrivilegio") String idPrivilegio) {
		ModelApiResponse respuesta = new ModelApiResponse();
		try {
			privilegiosService.removePrivilegio(idPrivilegio);
			respuesta.codigo("OK");
			respuesta.descripcion("Privilegio borrado correctamente");
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
