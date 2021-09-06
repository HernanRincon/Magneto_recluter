/*-
 * =====LICENSE-START=====
 * Java 11 Application
 * ------
 * Copyright (C) 2020 - 2021 Organization Name
 * ------
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 * =====LICENSE-END=====
 */

package com.mercadolibre.unit.test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.mercadolibre.model.ValidationDnaData;
import com.mercadolibre.validator.MutantVerification;

import io.quarkus.test.junit.QuarkusTest;


@QuarkusTest
public class MutantVerifyTest {
	private static Optional<List<String>> dnaTrue;
	private static Optional<List<String>> dnaFalse;
	private static Optional<List<String>> dnaNotSimetrict;
	private static Optional<List<String>> dnaNotValid;
	private static MutantVerification verify;
	

	@BeforeEach
	void init() {
		dnaTrue = Optional.ofNullable(List.of("ATGCGA", "CAGTGC", "TTATGT","AGAAGG","CCCCTA","TCACTG"));
		dnaFalse = Optional.ofNullable(List.of("ATGCTA", "CAGTGC", "TTATGT", "AGAAGG", "GCCCTA", "TCACTG"));
		dnaNotValid = Optional.ofNullable(List.of("ATGCGA","CAPTGC","TTATGH","AGAAGG","CCCCTA","TCACTG"));
		dnaNotSimetrict = Optional.ofNullable(List.of("ATGCGA","CAGT","TTATGT","AGAAGG","CCCCTA","TCACT"));
		verify = new MutantVerification();
		
	}
	
	@Test
	@DisplayName("is a mutant test")
	void isAMutantTest() {
		
		assertTrue(verify.isMutant(dnaTrue), "Actual value false and the spected value is true ");
	}

	@Test
	@DisplayName("is not a mutant test")
	void IsNotAmutant() {
		assertFalse(verify.isMutant(dnaFalse), "Actual value true and the spected value is false ");
	}

	@Test
	@DisplayName("In valid DNA")
	void invalidLettersDNA() {
		ValidationDnaData response=verify.verifyDna(dnaNotValid);
		assertFalse(response.isValidDna(), "verifyDna is not verifying properly dna characters ");
	}

	@Test
	@DisplayName("no simetrict DNA")
	void noSimetrictDnaSize() {
		ValidationDnaData response=verify.verifyDna(dnaNotSimetrict);
		assertFalse(response.isValidDna(), "verifyDna is not verifying properly simetrict dna");
	}
}
