package com.game.controllers.tags;

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

import com.game.controllers.tags.dto.TagItem;
import com.game.services.tag.TagService;
import com.game.services.tag.exceptions.TagNotFound;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * @author pachi
 *
 */

@RestController
@RequestMapping("${v1API}/tags")
public class TagControllers {

	public static Logger logger = LoggerFactory.getLogger(TagControllers.class);

	@Autowired
	TagService tagService;
	
	@GetMapping(path="")
	@ApiResponses({
		@ApiResponse(code = 200, message = "OK"),
		@ApiResponse(code = 404, message = "Not found."),
		@ApiResponse(code = 500, message = "Unexpected error.")
		})
	public @ResponseBody ResponseEntity<List<TagItem>> getTags(){
		try {
			return new ResponseEntity<List<TagItem>>(tagService.getAllTags(), HttpStatus.OK);
		} catch(ParseException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}catch(Exception e) {
			logger.error("Internal server error", e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping(path = "/{idTag}")
	@ApiResponses({ 
		@ApiResponse(code = 200, message = "OK"),
		@ApiResponse(code = 404, message = "Not found."),
		@ApiResponse(code = 500, message = "Unexpected error.") })
	public @ResponseBody ResponseEntity<TagItem> getTagByID(
			@PathVariable("idTag") String idTag){
		try {
			return new ResponseEntity<TagItem>(
					tagService.getTag(Long.parseLong(idTag)),
					HttpStatus.OK);
		} catch (TagNotFound e) {
			return new ResponseEntity<TagItem>(HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			logger.error("Internal server error", e);
			return new ResponseEntity<TagItem>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping(path="")
	@ApiResponses({
		@ApiResponse(code = 200, message = "OK, devuelve el id del tag insertado"),
		@ApiResponse(code = 500, message = "Unexpected error.")
		})
	public @ResponseBody ResponseEntity<Long> addTag( @RequestBody TagItem body){
		try {
			return new ResponseEntity<Long>(tagService.addTag(body), HttpStatus.OK);
		} catch(Exception e) {
			logger.error("Internal server error", e);
			return new ResponseEntity<Long>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping(path="/{idTag}")
	@ApiResponses({
		@ApiResponse(code = 200, message = "borrado OK"),
		@ApiResponse(code = 404, message = "Tag inexistente."),
		@ApiResponse(code = 400, message = "Id no valido."),
		@ApiResponse(code = 500, message = "Unexpected error.")
		})
	public @ResponseBody ResponseEntity<Void> removeJuego(@PathVariable("idTag") String idTag) {
		try {
			tagService.removeTag(idTag);
			return new ResponseEntity<Void>(HttpStatus.OK);
		} catch(TagNotFound e) {
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		} catch(NumberFormatException e) {
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		} catch(Exception e) {
			logger.error("Internal server error", e);
			return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping(path="/{idTag}")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Editado OK"),
		@ApiResponse(code = 404, message = "Tag inexistente."),
		@ApiResponse(code = 400, message = "Id no valido."),
		@ApiResponse(code = 500, message = "Unexpected error.")
		})
	public @ResponseBody ResponseEntity<Void> editJuego(@PathVariable("idTag") String idTag, @RequestBody TagItem body) {
		try {
			tagService.editTag( idTag, body);
			return new ResponseEntity<Void>(HttpStatus.OK);
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
