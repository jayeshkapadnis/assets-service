package com.affinion.gce.service;

import com.affinion.gce.annotation.Validator;
import com.affinion.gce.jpa.entity.AssetEntity;
import com.affinion.gce.model.asset.Asset;
import com.affinion.gce.repository.AssetRepository;
import com.affinion.gce.validator.AssetDataValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.lang.reflect.InvocationTargetException;

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
