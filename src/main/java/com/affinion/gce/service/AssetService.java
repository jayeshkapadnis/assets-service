package com.affinion.gce.service;

import com.affinion.gce.annotation.Validator;
import com.affinion.gce.exception.CyberException;
import com.affinion.gce.exception.ErrorCode;
import com.affinion.gce.jpa.entity.AssetEntity;
import com.affinion.gce.model.asset.Asset;
import com.affinion.gce.model.member.IdType;
import com.affinion.gce.repository.AssetRepository;
import com.affinion.gce.validator.AssetDataValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class AssetService {

    private final AssetRepository repository;
    private final RuleService ruleService;
    private final VaultService vaultService;

    @Autowired
    public AssetService(AssetRepository repository, RuleService service, VaultService vaultService) {
        this.repository = repository;
        this.ruleService = service;
        this.vaultService = vaultService;
    }

    public Mono<Asset> addAsset(Asset asset, String brmsToken) {
        return newValidator(asset, brmsToken)
                .flatMap(AssetDataValidator::validate)
                .cast(Asset.class)
                .map(AssetEntity::new)
                .flatMap(a -> vaultService.saveAsset(a).map(a::setToken))
                .map(a -> a.setAttributes(asset.hashAttributes()))
                .map(repository::save)
                .flatMap(a -> {
                    try {
                        return Mono.just(a.toData());
                    } catch (Exception e) {
                        return Mono.error(e);
                    }
                });
    }

    public Mono<Asset> getAssetById(final Long id){
        return Mono.justOrEmpty(repository.findById(id))
                .switchIfEmpty(Mono.error(new CyberException(String.format("Asset by id %d not found.", id)
                        , ErrorCode.ITEM_NOT_FOUND)))
                .flatMap(a -> {
                    try {
                        return Mono.just(a.toData());
                    } catch (Exception e) {
                        return Mono.error(new CyberException(String.format("Error getting asset with id %d", id)
                                , e, ErrorCode.GENERAL));
                    }
                });
    }

    public Mono<List<Asset>> getAssetsByMemberIdAndType(Long memberId, String idType) {
        return Mono.justOrEmpty(idType)
                .flatMap(i -> Mono.justOrEmpty(IdType.fromValue(idType)))
                .defaultIfEmpty(IdType.ID)
                .map(i -> {
                    if(i.equals(IdType.EXTERNAL_MEMBER_REF)){
                        return 123L;
                    }
                    return memberId;
                }).map(repository::findAllByMemberId)
                .flatMapMany(Flux::fromIterable)
                .flatMap(a -> {
                    try {
                        return Mono.just(a.toData());
                    } catch (Exception e) {
                        return Mono.error(new CyberException(String.format("Error while getting asset %d", a.getId()),
                                ErrorCode.GENERAL));
                    }
                })
                .collect(Collectors.toList());
    }

    protected Mono<? extends AssetDataValidator> newValidator(Asset asset, String brmsToken) {
        return Mono.justOrEmpty(asset.getClass().getAnnotation(Validator.class))
                .switchIfEmpty(Mono.error(new IllegalArgumentException("No validator configured")))
                .map(Validator::value)
                .flatMap(c -> {
                    try {
                        return Mono.justOrEmpty(c.getConstructor(AssetRepository.class, RuleService.class, String.class, asset.getClass()));
                    } catch (NoSuchMethodException e) {
                        return Mono.error(e);
                    }
                }).flatMap(c -> {
                    try {
                        return Mono.justOrEmpty(c.newInstance(repository, ruleService, brmsToken, asset));
                    } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                        return Mono.error(e);
                    }
                });
    }
}
