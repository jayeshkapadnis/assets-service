package com.affinion.gce.validator;

import com.affinion.gce.exception.CyberException;
import com.affinion.gce.exception.DataValidationException;
import com.affinion.gce.exception.ErrorCode;
import com.affinion.gce.jpa.entity.AssetAttribute;
import com.affinion.gce.model.asset.Asset;
import com.affinion.gce.model.rule.RuleResult;
import com.affinion.gce.repository.AssetRepository;
import com.affinion.gce.service.RuleService;
import reactor.core.publisher.Mono;

import java.util.regex.Pattern;

public abstract class AssetDataValidator<D extends Asset> {
    public static final String ASSET_COUNT_MAX = "Maximum asset count exceeded for %s";
    public static final String ATTRIBUTE_FORMAT = "Pattern for attribute %s doesn't match for asset type %s";

    private final RuleService service;
    private final String brmsToken;
    protected final AssetRepository repository;
    protected final D asset;

    public AssetDataValidator(AssetRepository repository,
                              RuleService service,
                              String brmsToken, D asset) {
        this.service = service;
        this.brmsToken = brmsToken;
        this.repository = repository;
        this.asset = asset;
    }

    public Mono<D> validate() {
        return initialize()
                .switchIfEmpty(Mono.error(new IllegalStateException("Rules not found")))
                .flatMap(this::validateData)
                .doOnError(Mono::error);
    }

    protected Mono<D> validateData(RuleResult result) {
        if (asset.getId().getId() == null) {
            return Mono.justOrEmpty(result.assetMaxAttributeValue())
                    .flatMap(this::validateAssetCount);
        }
        return Mono.just(asset);
    }

    protected Mono<D> validatePattern(String value, String key, RuleResult result) {
        return Mono.justOrEmpty(result.attributeValueByKey(key))
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Attribute Name not found")))
                .map(Pattern::compile)
                .map(p -> p.matcher(value).matches())
                .flatMap(r -> {
                    if (r) return Mono.just(asset);
                    return validationError(value);
                });
    }

    private Mono<D> validationError(String value) {
        return Mono.error(new DataValidationException(String.format(ATTRIBUTE_FORMAT, value, asset.type().getTypeKey())));
    }

    protected Mono<D> validateOptionalFieldPattern(String value, String key, RuleResult result) {
        return Mono.justOrEmpty(value)
                .flatMap(v -> Mono.justOrEmpty(result.attributeValueByKey(key)))
                .map(Pattern::compile)
                .map(p -> p.matcher(value).matches())
                .flatMap(r -> {
                    if (r) return Mono.just(asset);
                    return validationError(value);
                });
    }

    protected Mono<D> validateDuplicate(String key){
        if(asset.getId().getId() == null) {
            return Mono.justOrEmpty(asset.hashAttributes().stream()
                    .filter(a -> a.getKey().equals(key))
                    .findFirst())
                    .map(AssetAttribute::getValue)
                    .map(v -> repository.countByIdAndTypeAndAttribute(asset.getMemberId(), asset.type(), key, v))
                    .flatMap(c -> c > 0 ?
                            Mono.error(new CyberException("Duplicate found", ErrorCode.CONFLICT_ITEM)) :
                            Mono.just(asset));
        }
        return Mono.just(asset);
    }

    //Todo: add some failure test for this
    private Mono<RuleResult> initialize() {
        return service.rulesForAsset(asset.type(), brmsToken);
    }

    private Mono<Long> fetchAssetCount() {
        return Mono.just(repository.countAllByMemberIdAndType(asset.getMemberId(), asset.type()));
    }

    private Mono<D> validateAssetCount(Integer maxCount) {
        return fetchAssetCount()
                .filterWhen(l -> Mono.just(l < maxCount))
                .switchIfEmpty(Mono.error(
                        new DataValidationException(String.format(ASSET_COUNT_MAX, asset.type().getTypeKey())))
                )
                .map(a -> asset);
    }
}
