package com.affinion.gce.validator;

import com.affinion.gce.model.asset.type.OtherIdentifier;
import com.affinion.gce.model.rule.RuleResult;
import com.affinion.gce.repository.AssetRepository;
import com.affinion.gce.service.RuleService;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Mono;

public class OtherIdValidator extends AssetDataValidator<OtherIdentifier> {
    public OtherIdValidator(AssetRepository repository, RuleService service,
                            String brmsToken, OtherIdentifier asset) {
        super(repository, service, brmsToken, asset);
    }

    @Override
    protected Mono<OtherIdentifier> validateData(RuleResult result) {
        return super.validateData(result)
                .filter(a -> !StringUtils.isEmpty(a.getIdentifier()))
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Online ID can not be null")))
                .flatMap(a -> validatePattern(a.getIdentifier(), "otherId", result));
    }
}
