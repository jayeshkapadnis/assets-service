package com.affinion.gce.controller;

import com.affinion.gce.exception.CyberException;
import com.affinion.gce.exception.ErrorCode;
import com.affinion.gce.model.asset.Asset;
import com.affinion.gce.service.AssetService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.ServerResponse.status;

@RestController
@RequestMapping("/api/assets")
public class AssetController {

    private final AssetService assetService;

    public AssetController(AssetService assetService) {
        this.assetService = assetService;
    }

    @PostMapping
    public Mono<ServerResponse> saveAsset(@RequestAttribute String clientBrmsKey, @RequestBody Asset asset) {
        return assetService.addAsset(asset, clientBrmsKey)
                .switchIfEmpty(Mono.error(new CyberException("Error while adding asset", ErrorCode.GENERAL)))
                .flatMap(a ->
                        status(HttpStatus.CREATED)
                                .body(BodyInserters.fromValue(a))
                );
    }

    @GetMapping("/{id}")
    public Mono<ServerResponse> getAssetBy(@PathVariable Long id){

        return null;
    }
}
