package dev.uedercardoso.apivideos.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.uedercardoso.apivideos.domain.model.Media;

@Repository
public interface MediaRepository extends JpaRepository<Media, Integer> {
	
	boolean existsById(Integer id);
	Boolean existsByIdAndDeleted(Integer id, Boolean deleted);
	
	List<Media> findByDeleted(Boolean deleted);
	
}