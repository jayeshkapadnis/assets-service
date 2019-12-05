package com.affinion.gce.service;

import com.affinion.gce.jpa.entity.AssetEntity;
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
    public static final String NEW_TOKEN_URI = "/datavault_token";
    private final WebClient client;

    public VaultService(@Value("${integration.data-vault}") String baseUrl,
                        @Qualifier("loadBalancedWebClient") WebClient.Builder builder){
        this.client = builder.baseUrl(baseUrl).build();
    }

    public Mono<String> saveAsset(AssetEntity asset){
        AssetVaultRequest request = new AssetVaultRequest(asset, 1, 0, null);
        return client.post()
                .uri(NEW_TOKEN_URI)
                .header("x-api-key", "key")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(request))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Map.class)
                .switchIfEmpty(Mono.error(new IllegalStateException("No Response received")))
                .map(m -> m.get("Value"))
                .cast(String.class)
                .flatMap(m -> Mono.justOrEmpty(emptyToNull(m)))
                .switchIfEmpty(Mono.error(new IllegalStateException("Token not received")))
                .flatMap(r ->
                    r.equalsIgnoreCase("Transaction Failure") ?
                            Mono.error(new IllegalStateException("Error while getting token")) : Mono.just(r)
                );
    }

    private String emptyToNull(String field){
        return StringUtils.isEmpty(field) ? null : field;
    }
}
