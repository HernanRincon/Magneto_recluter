package com.mercadolibre.repository;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.mercadolibre.model.DnaInfo;

import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.GetItemRequest;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;
import software.amazon.awssdk.services.dynamodb.model.ScanRequest;

/**
 * The purpose of this class is set up the information is to persist in dynamo db
 * @author hernan
 *
 */
public class AbstractDynamoService {
	public static final String DNA_ID_COL = "id";
	public static final String DNA_SECUENCE_COL = "dnaSequence";
	public static final String DNA_IS_MUTANT_COL = "isMutant";

	public String getTableName() {
		return "DnaSequenceInfo";
	}

	protected ScanRequest scanRequest() {
		return ScanRequest.builder().tableName(getTableName()).attributesToGet(DNA_ID_COL,DNA_SECUENCE_COL, DNA_IS_MUTANT_COL).build();
	}

	protected PutItemRequest putRequest(DnaInfo dnaInfo) {
		Map<String, AttributeValue> item = new HashMap<>();
		item.put(DNA_ID_COL, AttributeValue.builder().s(UUID.randomUUID().toString()).build());
		item.put(DNA_SECUENCE_COL, AttributeValue.builder().ss(dnaInfo.getSequence()).build());
		item.put(DNA_IS_MUTANT_COL, AttributeValue.builder().bool(dnaInfo.isMutant()).build());

		return PutItemRequest.builder().tableName(getTableName()).item(item).build();
	}

	protected GetItemRequest getRequestByKey(String id) {
		Map<String, AttributeValue> key = new HashMap<>();
		key.put(DNA_ID_COL, AttributeValue.builder().s(id).build());

		return GetItemRequest.builder().tableName(getTableName()).key(key)
				.attributesToGet(DNA_ID_COL, DNA_SECUENCE_COL,DNA_IS_MUTANT_COL).build();
	}

}
