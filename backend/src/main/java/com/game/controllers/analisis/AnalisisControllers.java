package com.game.controllers.analisis;

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

import com.game.controllers.analisis.dto.AnalisisItem;
import com.game.services.analisis.AnalisisService;
import com.game.services.analisis.exceptions.AnalisisNotFound;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * @author pachi
 *
 */

@RestController
@RequestMapping("${v1API}/analisis")
public class AnalisisControllers {
	
	public static Logger logger = LoggerFactory.getLogger(AnalisisControllers.class);
	
	@Autowired
	AnalisisService analisisService;
	
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
	
}
