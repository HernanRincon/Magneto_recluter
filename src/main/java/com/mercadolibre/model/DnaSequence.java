package com.mercadolibre.model;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Default;

import com.fasterxml.jackson.annotation.JsonAlias;

@Default
@RequestScoped
public class DnaSequence {
	
	@JsonAlias("dna")
	private List<String> dna;

	public List<String> getDna() {
		return dna;
	}

	public void setDna(List<String> dna) {
		this.dna = dna;
	}

}
