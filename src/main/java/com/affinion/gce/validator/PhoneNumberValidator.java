package com.affinion.gce.validator;

import com.affinion.gce.exception.DataValidationException;
import com.affinion.gce.model.asset.type.PhoneNumber;
import com.affinion.gce.model.rule.RuleResult;
import com.affinion.gce.repository.AssetRepository;
import com.affinion.gce.service.RuleService;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Mono;

public class PhoneNumberValidator extends AssetDataValidator<PhoneNumber> {

    public PhoneNumberValidator(AssetRepository repository, RuleService service, String brmsToken, PhoneNumber asset) {
        super(repository, service, brmsToken, asset);
    }

    @Override
    protected Mono<PhoneNumber> validateData(RuleResult result) {
        return super.validateData(result)
                .filter(a -> !StringUtils.isEmpty(a.getNumber()))
                .switchIfEmpty(Mono.error(new DataValidationException("Phone number can not be empty")))
                .flatMap(a -> validatePattern(a.getNumber(), "phone", result));
    }

    private void validateDuplicateAsset(){

    }
}
