package com.affinion.gce.validator;

import com.affinion.gce.model.Asset;
import com.affinion.gce.model.rule.RuleResult;
import com.affinion.gce.service.RuleService;
import reactor.core.publisher.Mono;

public abstract class AssetDataValidator<D extends Asset> {
    private final RuleService service;
    private final String brmsToken;

    public AssetDataValidator(RuleService service, String brmsToken){
        this.service = service;
        this.brmsToken = brmsToken;
    }

    public Mono<D> validate(final D asset){
        return initialize(asset)
                .switchIfEmpty(Mono.error(new IllegalStateException("Rules not found")))
                .flatMap(r -> validateData(asset, r));
    }

    protected Mono<D> validateData(D asset, RuleResult result){
        if(asset.getId().getId() == null){
            return Mono.justOrEmpty(result.assetMaxAttributeValue())
                    .flatMap(c -> validateAssetCount(asset, c));
        }
        return Mono.just(asset);
    }

    protected abstract Mono<D> validateAssetCount(D asset, Integer maxCount);

    private Mono<RuleResult> initialize(D asset){
        return service.rulesForAsset(asset.type(), brmsToken);
    }
}
