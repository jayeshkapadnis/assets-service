package com.affinion.gce.validator;

import com.affinion.gce.exception.DataValidationException;
import com.affinion.gce.model.asset.type.Passport;
import com.affinion.gce.model.rule.RuleResult;
import com.affinion.gce.repository.AssetRepository;
import com.affinion.gce.service.RuleService;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Mono;

public class PassportValidator extends AssetDataValidator<Passport> {
    public PassportValidator(AssetRepository repository,
                             RuleService service,
                             String brmsToken,
                             Passport asset) {
        super(repository, service, brmsToken, asset);
    }

    @Override
    protected Mono<Passport> validateData(RuleResult result) {
        return super.validateData(result)
                .filter(a -> !StringUtils.isEmpty(a.getPassport()))
                .switchIfEmpty(Mono.error(new DataValidationException("Passport number can not be empty")))
                .flatMap(a -> validatePattern(a.getPassport(), "passport", result));
    }
}
