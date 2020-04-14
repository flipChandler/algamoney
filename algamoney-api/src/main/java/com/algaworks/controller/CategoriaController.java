package com.algaworks.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.event.RecursoCriadoEvent;
import com.algaworks.model.Categoria;
import com.algaworks.repository.CategoriaRepository;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@GetMapping
	public List<Categoria> listar() {
		return categoriaRepository.findAll();
	}
	
	
	/*
	@GetMapping
	public ResponseEntity<?> listar() {
		// code 200 (ok) ou code 204 (no content found)
		List<Categoria> listaCategoria = categoriaRepository.findAll();
		return !listaCategoria.isEmpty() ? ResponseEntity.ok(listaCategoria) : ResponseEntity.noContent().build();
	}	 	 
	*/
	
	@PostMapping
	public ResponseEntity<Categoria> criar(@Valid @RequestBody Categoria categoria, HttpServletResponse response) {
		Categoria categoriaSalva = categoriaRepository.save(categoria);
		
		publisher.publishEvent(new RecursoCriadoEvent(this, response, categoriaSalva.getCodigo()) );
		
		return ResponseEntity.status(HttpStatus.CREATED).body( categoriaSalva );
	}  
	
	/*
 	@GetMapping("/{codigo}")
	public ResponseEntity<Categoria> buscarPeloCodigo(@PathVariable Long codigo) {		
		Optional<Categoria> categoria = this.categoriaRepository.findById( codigo );
		return categoria.isPresent() ? 
				ResponseEntity.ok( categoria.get() ) : ResponseEntity.notFound().build() ;
 	}
	*/
	
	@GetMapping("/{codigo}")
	public ResponseEntity<Categoria> buscarPeloCodigo(@PathVariable Long codigo) {
		return this.categoriaRepository.findById( codigo )
				.map( categoria -> ResponseEntity.ok( categoria) )
				.orElse( ResponseEntity.notFound().build() );
	}
	
	
	
}
