package com.algaworks.repository.filter;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class LancamentoFilter {
	
	private String descricao;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dataVencimentoDe;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dataVencimentoAte;
	
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public Date getDataVencimentoDe() {
		return dataVencimentoDe;
	}
	public void setDataVencimentoDe(Date dataVencimentoDe) {
		this.dataVencimentoDe = dataVencimentoDe;
	}
	public Date getDataVencimentoAte() {
		return dataVencimentoAte;
	}
	public void setDataVencimentoAte(Date dataVencimentoAte) {
		this.dataVencimentoAte = dataVencimentoAte;
	}

}
