package com.game.controllers.juegos;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.game.controllers.juegos.dto.JuegoItem;
import com.game.services.juegos.JuegosService;
import com.game.services.juegos.exceptions.JuegosNotFound;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * @author pachi
 *
 */

@RestController
@RequestMapping("${v1API}/juegos")
public class Juegos {
	
	public static Logger logger = LoggerFactory.getLogger(Juegos.class);

	@Autowired
	JuegosService juegosService;

	@GetMapping(path = "/{idJuego}")
	@ApiResponses({ 
		@ApiResponse(code = 200, message = "OK"),
		@ApiResponse(code = 404, message = "Not found."),
		@ApiResponse(code = 500, message = "Unexpected error.") })
	public @ResponseBody ResponseEntity<JuegoItem> getJuegoByID(
			@PathVariable("idJuego") String idJuego){
		try {
			return new ResponseEntity<JuegoItem>(
					juegosService.getJuego(Long.parseLong(idJuego)),
					HttpStatus.OK);
		} catch (JuegosNotFound e) {
			return new ResponseEntity<JuegoItem>(HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			logger.error("Internal server error", e);
			return new ResponseEntity<JuegoItem>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
