package dev.uedercardoso.apivideos.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.uedercardoso.apivideos.domain.model.Midia;

@Repository
public interface MidiaRepository extends JpaRepository<Midia, Integer> {
	
	boolean existsById(Integer id);
	
	List<Midia> findByDeleted(Boolean deleted);
	
}