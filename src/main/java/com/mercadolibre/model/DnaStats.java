package com.mercadolibre.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * Class to return stats information with statistics about humans and mutants 
 * @author hernan
 *
 */
@JsonPropertyOrder(value = {"countMutantDna","countHumanDna","ratio"})
public class DnaStats {
	
	@JsonAlias("count_mutant_dna")
	private long countMutantDna;
	@JsonAlias("count_human_dna")
	private long countHumanDna;
	private double ratio;
	
	@JsonGetter("count_mutant_dna")
	public long getCountMutantDna() {
		return countMutantDna;
	}
	public void setCountMutantDna(long countMutantDna) {
		this.countMutantDna = countMutantDna;
	}
	@JsonGetter("count_human_dna")
	public long getCountHumanDna() {
		return countHumanDna;
	}
	public void setCountHumanDna(long countHumanDna) {
		this.countHumanDna = countHumanDna;
	}
	public double getRatio() {
		return ratio;
	}
	public void setRatio(double ratio) {
		this.ratio = ratio;
	}
	
	

}
