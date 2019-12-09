package com.affinion.gce.service;

import com.affinion.gce.exception.RestClientException;
import com.affinion.gce.jpa.entity.AssetEntity;
import com.affinion.gce.model.asset.Asset;
import com.affinion.gce.model.asset.AssetType;
import com.affinion.gce.model.request.AssetVaultRequest;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

@Component
public class VaultService {
    public static final String TOKEN_URI = "/datavault_token";
    private final WebClient client;

    public VaultService(@Value("${integration.data-vault.base-url}") String baseUrl,
                        @Value("${integration.data-vault.key}") String apiKey,
                        @Qualifier("loadBalancedWebClient") WebClient.Builder builder) {
        this.client = builder.baseUrl(baseUrl)
                .defaultHeader("x-api-key", apiKey)
                .build();
    }

    public Mono<String> saveAsset(AssetEntity asset) {
        AssetVaultRequest request = new AssetVaultRequest(asset, 1, 0, null);
        return client.post()
                .uri(TOKEN_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(request))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Map.class)
                .switchIfEmpty(Mono.error(new RestClientException("No Response received")))
                .map(m -> m.get("Value"))
                .cast(String.class)
                .flatMap(m -> Mono.justOrEmpty(emptyToNull(m)))
                .switchIfEmpty(Mono.error(new RestClientException("Token not received")))
                .flatMap(r ->
                        r.equalsIgnoreCase("Transaction Failure") ?
                                Mono.error(new RestClientException("Error while getting token")) : Mono.just(r)
                );
    }

    private String emptyToNull(String field) {
        return StringUtils.isEmpty(field) ? null : field;
    }
}
