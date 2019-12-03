package com.affinion.gce.service;

import com.affinion.gce.model.asset.AssetType;
import com.affinion.gce.model.rule.RuleResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Collections;

@Component
public class RuleService {

    private final WebClient client;
    private final ObjectMapper mapper;

    public RuleService(@Qualifier("loadBalancedWebClient") WebClient.Builder client,
                       @Value("{integration.brms}") String baseUrl){
        this.client = client.baseUrl(baseUrl).build();
        this.mapper = new ObjectMapper();
    }

    public Mono<RuleResult> rulesForAsset(AssetType type, String brmsToken){
        //Use guava ImmutableMap.of
        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("attributeName", "asset_type");
        requestParams.add("attributeValue", type.getTypeKey());
        return client.post()
                .uri("/attributevalues")
                .header("X-BRMS-Token-Key", brmsToken)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(Collections.singletonList(requestParams)))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError,
                        clientResponse -> Mono.error(new IllegalArgumentException()))
                .onStatus(HttpStatus::is5xxServerError,
                        clientResponse -> Mono.error(new IllegalStateException()))
                .bodyToMono(RuleResult.class)
                .retry(3, e -> e instanceof IllegalStateException);
    }
}
