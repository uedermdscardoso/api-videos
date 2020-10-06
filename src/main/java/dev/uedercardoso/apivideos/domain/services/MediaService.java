package dev.uedercardoso.apivideos.domain.services;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.uedercardoso.apivideos.domain.exceptions.MediaIsEmptyException;
import dev.uedercardoso.apivideos.domain.exceptions.MediaNotFoundException;
import dev.uedercardoso.apivideos.domain.model.Media;
import dev.uedercardoso.apivideos.domain.repository.MediaRepository;

@Service
public class MediaService {

	@Autowired
	private MediaRepository midiaRepository;
	
	public List<Media> getMedias(Boolean all){
		List<Media> midias = new LinkedList<Media>();
		if(all) 
			midias = midiaRepository.findAll();
		else 
			midias = midiaRepository.findByDeleted(all);
		
		if(midias.isEmpty() || midias.size() == 0)
			throw new MediaIsEmptyException("Media is empty");
		
		return midias;
	}
	
	public void createMedia(Media midia) {
		midiaRepository.save(midia);
	}
	
	public void updateMedia(Media midia) {
		if(midia.getId() == null || !midiaRepository.existsById(midia.getId()))
			throw new MediaNotFoundException("Media not found");
		midiaRepository.save(midia);
	}
	
	public void removeMedia(Integer id) {
		if(!midiaRepository.existsById(id))
			throw new MediaNotFoundException("Media not found");
		midiaRepository.deleteById(id);
	}
	
}
