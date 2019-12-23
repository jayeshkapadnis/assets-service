package com.affinion.gce.controller;

import com.affinion.gce.exception.CyberException;
import com.affinion.gce.exception.ErrorCode;
import com.affinion.gce.model.asset.Asset;
import com.affinion.gce.service.AssetService;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.function.Function;

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
        return withTenantId(clientBrmsKey,
                (key) -> assetService.addAsset(asset, key)
                        .switchIfEmpty(Mono.error(new CyberException("Error while adding asset", ErrorCode.GENERAL)))
                        .flatMap(a -> {
                                    HttpStatus status = asset.getId().getId() == null ?
                                            HttpStatus.CREATED : HttpStatus.OK;
                                    return status(status)
                                            .body(BodyInserters.fromValue(a));
                                }
                        ));
    }

    @GetMapping("/{id}")
    public Mono<ServerResponse> getAssetBy(@RequestAttribute String clientBrmsKey, @PathVariable Long id) {
        return withTenantId(clientBrmsKey,
                (brmsKey) -> assetService.getAssetById(id)
                        .flatMap(a -> status(HttpStatus.FOUND)
                                .body(BodyInserters.fromValue(a))));
    }

    @GetMapping("/members/{memberId}")
    public Mono<ServerResponse> getAssetsByMemberId(@RequestAttribute String clientBrmsKey,
                                                    @PathVariable Long memberId,
                                                    @RequestParam(required = false) String idType) {
        return withTenantId(clientBrmsKey,
                (key) -> assetService.getAssetsByMemberIdAndType(memberId, idType)
                        .flatMap(a -> status(HttpStatus.FOUND)
                                .body(BodyInserters.fromValue(a))));
    }

    public void getAssetSummaryByMemberId(@RequestAttribute String clientBrmsKey,
                                          @PathVariable Long memberId){
        assetService.getAssetSummaryBy(memberId);
        /*withTenantId(clientBrmsKey, (key) -> {
            assetService.getAssetSummaryBy(memberId)
        });*/
    }

    private Mono<ServerResponse> withTenantId(String clientBrmsKey, Function<String, Mono<ServerResponse>> f) {
        if (StringUtils.isEmpty(clientBrmsKey)) {
            return Mono.error(new CyberException("Invalid Visibility Scope id", ErrorCode.PERMISSION_DENIED));
        } else {
            return f.apply(clientBrmsKey);
        }
    }
}
