package com.affinion.gce.validator;

import com.affinion.gce.exception.DataValidationException;
import com.affinion.gce.model.asset.type.Name;
import com.affinion.gce.model.rule.RuleResult;
import com.affinion.gce.repository.AssetRepository;
import com.affinion.gce.service.RuleService;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Mono;

public class MemberNameValidator extends AssetDataValidator<Name> {

    public MemberNameValidator(AssetRepository repository, RuleService service, String brmsToken, Name asset) {
        super(repository, service, brmsToken, asset);
    }

    @Override
    protected Mono<Name> validateData(RuleResult result) {
        return super.validateData(result)
                .filter(a -> !StringUtils.isEmpty(a.getFirstName()))
                .switchIfEmpty(Mono.error(new DataValidationException("First Name can not be empty")))
                .flatMap(a -> validatePattern(a.getFirstName(), "first_name", result))
                .flatMap(a -> validateOptionalFieldPattern(a.getMiddleName(), "middle_name", result))
                .flatMap(a -> validateOptionalFieldPattern(a.getLastName(), "last_name", result));
    }
}
