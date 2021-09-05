package com.mercadolibre.api.service;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.mercadolibre.model.DnaInfo;
import com.mercadolibre.model.DnaSequence;
import com.mercadolibre.model.DnaStats;
import com.mercadolibre.model.ValidationDnaData;
import com.mercadolibre.repository.DnaInfoRepository;
import com.mercadolibre.validator.MutantVerification;

import software.amazon.awssdk.utils.CollectionUtils;

@Path("/mutantsValidator")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MutantValidatorService {
	@Inject
	private MutantVerification verificator;
	@Inject
	private DnaInfoRepository repository;

	@GET
	@Path("/stats")
	public Response getStats() {
		DnaStats stats = new DnaStats();
		List<DnaInfo> dnaInfoList=repository.findAll();
		if (!CollectionUtils.isNullOrEmpty(dnaInfoList)) {
			stats.setCountMutantDna(dnaInfoList.stream().parallel().filter(fil -> fil.isMutant()).count()) ;
			stats.setCountHumanDna(dnaInfoList.stream().parallel().filter(fil -> !fil.isMutant()).count());
			double ratio=stats.getCountHumanDna()!=0?stats.getCountMutantDna()/stats.getCountHumanDna():0;
			stats.setRatio(ratio);
		}
		return Response.ok(stats).build();
	}
	
	@POST
	@Path("/mutant")
	public Response verifyDNA(DnaSequence dna) {
		Optional<List<String>> dnaSequence = Optional.ofNullable(dna.getDna());
		ValidationDnaData validations = verificator.verifyDna(dnaSequence);
		if (validations.isValidDna()) {
			DnaInfo info= new DnaInfo();
			boolean isMutant = verificator.isMutant(dnaSequence);
			info.setMutant(isMutant);
			info.setSequence(dna.getDna());
			if (isMutant) {
				repository.add(info);
				return Response.ok(info).build();
			} else {
				repository.add(info);
				return Response.status(Response.Status.FORBIDDEN.getStatusCode()).entity(info).build();
			}
		} else {
			return Response.status(Response.Status.BAD_REQUEST).entity(validations).build();
		}
	}
}