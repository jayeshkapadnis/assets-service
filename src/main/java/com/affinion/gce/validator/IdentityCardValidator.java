package com.affinion.gce.validator;

import com.affinion.gce.exception.DataValidationException;
import com.affinion.gce.model.asset.type.IdentityCard;
import com.affinion.gce.model.rule.RuleResult;
import com.affinion.gce.repository.AssetRepository;
import com.affinion.gce.service.RuleService;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Mono;

public class IdentityCardValidator extends AssetDataValidator<IdentityCard> {
    public IdentityCardValidator(AssetRepository repository, RuleService service,
                                 String brmsToken, IdentityCard asset) {
        super(repository, service, brmsToken, asset);
    }

    @Override
    protected Mono<IdentityCard> validateData(RuleResult result) {
        return super.validateData(result)
                .filter(a -> !StringUtils.isEmpty(a.getIdentifier()))
                .switchIfEmpty(Mono.error(new DataValidationException("Identity Card can not be empty")))
                .flatMap(a -> validatePattern(a.getIdentifier(), "identity_card", result));
    }
}
