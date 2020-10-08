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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import dev.uedercardoso.apivideos.domain.exceptions.FileIsNotVideoException;
import dev.uedercardoso.apivideos.domain.exceptions.MediaIsEmptyException;
import dev.uedercardoso.apivideos.domain.exceptions.MediaNotFoundException;
import dev.uedercardoso.apivideos.domain.exceptions.UploadFailedException;
import dev.uedercardoso.apivideos.domain.model.Media;
import dev.uedercardoso.apivideos.domain.services.MediaService;

@RestController
@RequestMapping("medias")
public class MediaController {

	@Autowired
	private MediaService mediaService;
	
	@GetMapping("{all}")
	public ResponseEntity<List<Media>> getMedias(@PathVariable(required = true) Integer all){
		try {
			List<Media> media = mediaService.getMedias(all);
			
			return ResponseEntity.ok(media);
		} catch(MediaIsEmptyException e) {
			return ResponseEntity.noContent().build();
		} catch(Exception e) {
			return ResponseEntity.badRequest().build();
		}
	}
	
	@GetMapping("media/{id}")
	public ResponseEntity<Media> getMedia(@PathVariable(required = true) Integer id){
		try {
			Media media = mediaService.getMedia(id);
			
			return ResponseEntity.ok(media);
		} catch(MediaNotFoundException e) {
			return ResponseEntity.notFound().build();
		} catch(Exception e) {
			return ResponseEntity.badRequest().build();
		}
	}
	
	@PostMapping
	public ResponseEntity<Void> create(
			@RequestParam(required=true) String name,
			@RequestParam(required=false) Integer duration,
			@RequestParam(required=true) MultipartFile video){
		try {			
			mediaService.createMedia(name, duration, video);
			
			return ResponseEntity.status(HttpStatus.CREATED).build();
				
		} catch(FileIsNotVideoException e) {
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
		} catch(UploadFailedException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		} catch(MediaIsEmptyException e) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		} catch(Exception e) {
			return ResponseEntity.badRequest().build();
		}
	}
	
	@PutMapping
	public ResponseEntity<Void> update(@Valid @RequestBody Media media){
		try {
			
			mediaService.updateMedia(media);
			
			return ResponseEntity.ok().build();
				
		} catch(MediaNotFoundException e) {
			return ResponseEntity.notFound().build();
		} catch(Exception e) {
			return ResponseEntity.badRequest().build();
		}
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<Void> remove(@PathVariable(required=true) Integer id){
		try {
			
			mediaService.removeMedia(id);
			
			return ResponseEntity.ok().build();
				
		} catch(MediaNotFoundException e) {
			return ResponseEntity.notFound().build();
		} catch(Exception e) {
			return ResponseEntity.badRequest().build();
		}
	}
}
