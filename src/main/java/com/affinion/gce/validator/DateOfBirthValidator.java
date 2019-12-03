package com.affinion.gce.validator;

import com.affinion.gce.model.asset.type.DateOfBirth;
import com.affinion.gce.model.rule.RuleResult;
import com.affinion.gce.repository.AssetRepository;
import com.affinion.gce.service.RuleService;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Mono;

public class DateOfBirthValidator extends AssetDataValidator<DateOfBirth> {
    public DateOfBirthValidator(AssetRepository repository, RuleService service, String brmsToken, DateOfBirth asset) {
        super(repository, service, brmsToken, asset);
    }

    @Override
    protected Mono<DateOfBirth> validateData(RuleResult result) {
        return super.validateData(result)
                .filter(a -> !StringUtils.isEmpty(a.getDob()))
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Date of birth can not be empty")))
                .flatMap(a -> validatePattern(a.getDob(), "date_of_birth", result));
    }
}
