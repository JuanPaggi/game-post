package com.game.services.tag;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.game.controllers.tags.dto.TagItem;
import com.game.exceptions.ApiException;
import com.game.persistence.models.Tag;
import com.game.persistence.repository.TagRepository;

/**
 * @author Juan Paggi
 *
 */

@Service
public class TagService {
	
	@Autowired
	TagRepository tagRepository;
	
	public TagItem getTag(long id) {
		
		try {			
			Optional<Tag> tag = tagRepository.findById(id);
			TagItem tagItem = new TagItem();
			if(tag.isPresent()) {				
				tagItem.id_tag = tag.get().getId_tag();
				tagItem.etiqueta = tag.get().getEtiqueta();
				return tagItem;
			}else {
				throw new ApiException(404, "No existe el tag");
			}
		} catch (ApiException e) {
			throw e;
		} catch (Exception exception) {
			throw exception; 
		}
		
	}
	
	public List<TagItem> getAllTags() {
		
		try {			
			List<Tag> tags = tagRepository.findAll();
			List<TagItem> out = new ArrayList<TagItem>();
			for(Tag tag: tags) {
				TagItem item = new TagItem();
				item.id_tag = tag.getId_tag();
				item.etiqueta = tag.getEtiqueta();
				out.add(item);
			}
			return out;
		} catch (ApiException e) {
			throw e;
		} catch (Exception exception) {
			throw exception; 
		}
		
	}
	
	public long addTag(TagItem tagIn) {
		
		try {			
			Tag tag = new Tag();
			tag.setEtiqueta(tagIn.etiqueta);
			tag = tagRepository.save(tag);
			return tag.getId_tag();
		} catch (ApiException e) {
			throw e;
		} catch (Exception exception) {
			throw exception; 
		}
		
	}
	
	public void removeTag(String id) {
		
		try {			
			Optional<Tag> tag = tagRepository.findById(Long.parseLong(id));
			if (tag.isPresent()) {
				tagRepository.delete(tag.get());
			}else {
				throw new ApiException(404, "No existe el tag");
			}	
		} catch (ApiException e) {
			throw e;
		} catch (Exception exception) {
			throw exception; 
		}
		
	}
	
	public void editTag(String id, TagItem tagIn) {
		
		try {			
			Optional<Tag> tag = tagRepository.findById(Long.parseLong(id));
			if(tag.isPresent()) {
				Tag tagObj = tag.get();
				tagObj.setEtiqueta(tagIn.etiqueta);
				tagRepository.save(tagObj);
			}else {
				throw new ApiException(404, "No existe el tag");
			}
		} catch (ApiException e) {
			throw e;
		} catch (Exception exception) {
			throw exception; 
		}
		
	}
	
}
