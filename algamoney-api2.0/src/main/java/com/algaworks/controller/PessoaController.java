package com.algaworks.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.event.RecursoCriadoEvent;
import com.algaworks.model.Pessoa;
import com.algaworks.repository.PessoaRepository;
import com.algaworks.service.PessoaService;

@RestController
@RequestMapping("/pessoas")
public class PessoaController {
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private PessoaService pessoaService;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@GetMapping
	public List<Pessoa> listar (){
		return pessoaRepository.findAll();
	}
	
	//BUSCAR PELO CODIGO LAMBDA - RETORNA 200 OU 404
	@GetMapping("/{codigo}")
	public ResponseEntity<Pessoa> buscarPeloCodigo(@PathVariable Long codigo) {
		return this.pessoaRepository.findById(codigo)
				.map( pessoa -> ResponseEntity.ok( pessoa ) )
				.orElse( ResponseEntity.notFound().build() );
	}
	

	/*
	@GetMapping("/{codigo}")
	public ResponseEntity<Pessoa> buscarPeloCodigo(@PathVariable Long codigo) {
		Optional<Pessoa> pessoa = pessoaRepository.findById(codigo);
		return pessoa == null ? ResponseEntity.notFound().build() : ResponseEntity.ok( pessoa.get() );
	}
	*/
	
	@PostMapping
	public ResponseEntity<Pessoa> criar(@Valid @RequestBody Pessoa pessoa, HttpServletResponse response) {
		Pessoa pessoaSalva = pessoaRepository.save(pessoa);
		
		publisher.publishEvent( new RecursoCriadoEvent(this, response, pessoaSalva.getCodigo()) );		
		return ResponseEntity.status(HttpStatus.CREATED ).body( pessoaSalva );
	} 
	
	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long codigo) {
		pessoaRepository.deleteById( codigo );
	}
	
	
	// JÁ RETORNA UMA RESPOSTA HTTP 200 OU 404
	@PutMapping("/{codigo}")
	public Pessoa atualizar(@PathVariable Long codigo, @Valid @RequestBody Pessoa pessoa){
		
		Pessoa pessoaSalva = pessoaService.atualizar(codigo, pessoa);				
		return this.pessoaRepository.save(pessoaSalva);
	}
	
	@PutMapping("/{codigo}/ativo" )
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void atualizarPropriedadeAtivo(@PathVariable Long codigo, @RequestBody Boolean ativo) {
		pessoaService.atualizarPropriedadeAtivo(codigo, ativo);
	}
	

}
