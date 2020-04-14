package com.algaworks.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.algaworks.model.Pessoa;
import com.algaworks.repository.lancamento.LancamentoRepositoryQuery;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

}
