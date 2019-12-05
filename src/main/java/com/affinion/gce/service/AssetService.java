package com.affinion.gce.service;

import com.affinion.gce.annotation.Validator;
import com.affinion.gce.jpa.entity.AssetEntity;
import com.affinion.gce.model.asset.Asset;
import com.affinion.gce.repository.AssetRepository;
import com.affinion.gce.validator.AssetDataValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.lang.reflect.InvocationTargetException;

@Component
public class AssetService {

    private final AssetRepository repository;
    private final RuleService ruleService;

    @Autowired
    public AssetService(AssetRepository repository, RuleService service){
        this.repository = repository;
        this.ruleService = service;
    }

    //@SuppressWarnings("unchecked")
    public Mono<AssetEntity> addAsset(Asset asset, String brmsToken){
        return Mono.justOrEmpty(asset).map(AssetEntity::new)
                .map(a -> {
                    a.setToken("token");
                    return a;
                })
                .flatMap(a -> Mono.just(repository.save(a)));
        /*return Mono.justOrEmpty(asset).map(AssetEntity::new)
                .map(a -> {
                    a.setToken("token");
                    return a;
                })
                .flatMap(repository::save)
                .flatMap(this::saveAttributes);*/
        /*return newValidator(asset, brmsToken)
                .doOnError(e -> Mono.error(new IllegalAccessError(e.getMessage())))
                .flatMap(AssetDataValidator::validate)
                .cast(Asset.class)
                .map(AssetEntity::new)
                .flatMap(repository::save);*/
    }

    protected Mono<? extends AssetDataValidator> newValidator(Asset asset, String brmsToken){
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
