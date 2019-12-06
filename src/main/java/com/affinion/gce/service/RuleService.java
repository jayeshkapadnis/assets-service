package com.affinion.gce.service;

import com.affinion.gce.exception.RestClientException;
import com.affinion.gce.model.asset.AssetType;
import com.affinion.gce.model.rule.RuleResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import reactor.retry.Retry;

import java.time.Duration;
import java.util.Collections;

@Component
public class RuleService {
    public static final String RULE_GET_ATTRIBUTE_URI = "/attributevalues";
    private final WebClient client;
    private final ObjectMapper mapper;

    public RuleService(@Qualifier("loadBalancedWebClient") WebClient.Builder client,
                       @Value("${integration.brms}") String baseUrl){
        this.client = client.baseUrl(baseUrl).build();
        this.mapper = new ObjectMapper();
    }

    public Mono<RuleResult> rulesForAsset(AssetType type, String brmsToken){
        //Use guava ImmutableMap.of
        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("attributeName", "asset_type");
        requestParams.add("attributeValue", type.getTypeKey());
        return client.post()
                .uri(RULE_GET_ATTRIBUTE_URI)
                .header("X-BRMS-Token-Key", brmsToken)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(Collections.singletonList(requestParams)))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(RuleResult.class)
                .retryWhen(
                        Retry.onlyIf(ctx -> ctx.exception() instanceof WebClientResponseException.InternalServerError)
                        .exponentialBackoff(Duration.ofSeconds(5), Duration.ofSeconds(10))
                        .retryMax(2)
                ).doOnError(e -> {
                    throw new RestClientException(e.getMessage(), e);
                });
    }
}
