package com.game.services.tag;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.game.controllers.tags.dto.TagItem;
import com.game.persistence.models.Tag;
import com.game.persistence.repository.TagRepository;
import com.game.services.tag.exceptions.TagNotFound;

/**
 * @author pachi
 *
 */

@Service
public class TagService {
	
	@Autowired
	TagRepository tagRepository;
	
	public TagItem getTag(long id) throws TagNotFound{
		
		Optional<Tag> tag = tagRepository.findById(id);
		TagItem tagItem = new TagItem();
		if(tag.isEmpty()) throw new TagNotFound();
		tagItem.id_tag = tag.get().getId_tag();
		tagItem.etiqueta = tag.get().getEtiqueta();
		return tagItem;
		
	}
	
	public List<TagItem> getAllTags() throws ParseException{
		
		List<Tag> tags = tagRepository.findAll();
		List<TagItem> out = new ArrayList<TagItem>();
		for(Tag tag: tags) {
			TagItem item = new TagItem();
			item.id_tag = tag.getId_tag();
			item.etiqueta = tag.getEtiqueta();
			out.add(item);
		}
		return out;
		
	}
	
	public long addTag(TagItem tagIn) throws TagNotFound{
		
		Tag tag = new Tag();
		tag.setEtiqueta(tagIn.etiqueta);
		tag = tagRepository.save(tag);
		return tag.getId_tag();
		
	}
	
	public void removeTag(String id) throws TagNotFound, NumberFormatException {
		
		Optional<Tag> tag = tagRepository.findById(Long.parseLong(id));
		if(tag.isEmpty()) throw new TagNotFound();
		tagRepository.delete(tag.get());
		
	}
	
	public void editTag(String id, TagItem tagIn) throws TagNotFound, NumberFormatException{
		
		Optional<Tag> tag = tagRepository.findById(Long.parseLong(id));
		if(tag.isEmpty()) throw new TagNotFound();
		Tag tagObj = tag.get();
		tagObj.setEtiqueta(tagIn.etiqueta);
		tagRepository.save(tagObj);
		
	}
	
}
