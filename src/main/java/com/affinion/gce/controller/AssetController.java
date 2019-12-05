package com.affinion.gce.controller;

import com.affinion.gce.jpa.entity.AssetEntity;
import com.affinion.gce.model.asset.Asset;
import com.affinion.gce.service.AssetService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/assets")
public class AssetController {

    private final AssetService assetService;

    public AssetController(AssetService assetService) {
        this.assetService = assetService;
    }

    @PostMapping
    public Mono<String> addAsset(@RequestBody Asset asset){
        return assetService.addAsset(asset, "1234")
                .switchIfEmpty(Mono.error(new IllegalStateException("No response")))
                .flatMap(a -> {
                    try {
                        return Mono.just(new ObjectMapper().writeValueAsString(a));
                    } catch (JsonProcessingException e) {
                        return Mono.error(e);
                    }
                });
    }
}
