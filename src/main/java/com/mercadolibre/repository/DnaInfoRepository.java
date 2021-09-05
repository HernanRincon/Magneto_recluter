package com.mercadolibre.repository;

import java.util.List;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.mercadolibre.model.DnaInfo;

import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

@ApplicationScoped
public class DnaInfoRepository extends AbstractDynamoService {

    @Inject
    DynamoDbClient dynamoDB;

    public List<DnaInfo> findAll() {
        return dynamoDB.scanPaginator(scanRequest()).items().stream()
                .map(DnaInfo::from)
                .collect(Collectors.toList());
    }

    public List<DnaInfo> add(DnaInfo dnaInfo) {
        dynamoDB.putItem(putRequest(dnaInfo));
        return findAll();
    }

    public DnaInfo get(String id) {
        return DnaInfo.from(dynamoDB.getItem(getRequestByKey(id)).item());
    }
}