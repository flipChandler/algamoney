package com.algaworks.repository.lancamento;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.algaworks.model.Lancamento;
import com.algaworks.repository.filter.LancamentoFilter;

public interface LancamentoRepositoryQuery {

	public Page<Lancamento> filtrar(LancamentoFilter lancamentoFilter, Pageable pageable);
	 
}
