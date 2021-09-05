package com.mercadolibre.model;

import static com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.mercadolibre.repository.AbstractDynamoService;

import io.quarkus.runtime.annotations.RegisterForReflection;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

/**
 * class to persist information
 * @author hernan
 *
 */
@RegisterForReflection
public class DnaInfo {

	@JsonInclude(Include.NON_NULL)
	private String id;
	private List<String> sequence;
	private boolean mutant;

	public DnaInfo() {
	}

	public static DnaInfo from(Map<String, AttributeValue> item) {
		DnaInfo dnaInfo = new DnaInfo();
		if (item != null && !item.isEmpty()) {
			dnaInfo.setId(item.get(AbstractDynamoService.DNA_ID_COL).s());
			dnaInfo.setSequence(item.get(AbstractDynamoService.DNA_SECUENCE_COL).ss());
			dnaInfo.setMutant(item.get(AbstractDynamoService.DNA_IS_MUTANT_COL).bool());
		}
		return dnaInfo;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<String> getSequence() {
		return sequence;
	}

	public void setSequence(List<String> sequence) {
		this.sequence = sequence;
	}

	public boolean isMutant() {
		return mutant;
	}

	public void setMutant(boolean mutant) {
		this.mutant = mutant;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof DnaInfo)) {
			return false;
		}

		DnaInfo other = (DnaInfo) obj;

		return Objects.equals(other.id, this.id) || Objects.equals(other.sequence, this.sequence);
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.id);
	}

}
