package dev.uedercardoso.apivideos.web.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import dev.uedercardoso.apivideos.domain.exceptions.MediaIsEmptyException;
import dev.uedercardoso.apivideos.domain.exceptions.MediaNotFoundException;
import dev.uedercardoso.apivideos.domain.model.Midia;
import dev.uedercardoso.apivideos.domain.services.MidiaService;

@RestController
public class MidiaController {

	@Autowired
	private MidiaService midiaService;
	
	@GetMapping("{ all }")
	public ResponseEntity<List<Midia>> getMedias(@PathVariable(required = true) Boolean all){
		try {
			List<Midia> medias = midiaService.getMedias(all);
			
			return ResponseEntity.ok(medias);
		} catch(Exception e) {
			return ResponseEntity.badRequest().build();
		}
	}
	
	@PostMapping
	public ResponseEntity<Void> create(@Valid @RequestBody Midia midia){
		try {
			
			midiaService.createMedia(midia);
			
			return ResponseEntity.status(HttpStatus.CREATED).build();
				
		} catch(MediaIsEmptyException e) {
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
		} catch(Exception e) {
			return ResponseEntity.badRequest().build();
		}
	}
	
	@PutMapping
	public ResponseEntity<Void> update(@Valid @RequestBody Midia midia){
		try {
			
			midiaService.updateMedia(midia);
			
			return ResponseEntity.status(HttpStatus.CREATED).build();
				
		} catch(MediaNotFoundException e) {
			return ResponseEntity.notFound().build();
		} catch(Exception e) {
			return ResponseEntity.badRequest().build();
		}
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<Void> remove(@PathVariable(required=true) Integer id){
		try {
			
			midiaService.removeMedia(id);
			
			return ResponseEntity.ok().build();
				
		} catch(MediaNotFoundException e) {
			return ResponseEntity.notFound().build();
		} catch(Exception e) {
			return ResponseEntity.badRequest().build();
		}
	}
}
