package dev.uedercardoso.apivideos.domain.services;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.uedercardoso.apivideos.domain.exceptions.MediaIsEmptyException;
import dev.uedercardoso.apivideos.domain.exceptions.MediaNotFoundException;
import dev.uedercardoso.apivideos.domain.model.Midia;
import dev.uedercardoso.apivideos.domain.repository.MidiaRepository;

@Service
public class MidiaService {

	@Autowired
	private MidiaRepository midiaRepository;
	
	public List<Midia> getMedias(Boolean all){
		List<Midia> midias = new LinkedList<Midia>();
		if(all) 
			midias = midiaRepository.findAll();
		else 
			midias = midiaRepository.findByDeleted(all);
		
		if(midias.isEmpty() || midias.size() == 0)
			throw new MediaIsEmptyException("Media is empty");
		
		return midias;
	}
	
	public void createMedia(Midia midia) {
		midiaRepository.save(midia);
	}
	
	public void updateMedia(Midia midia) {
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
