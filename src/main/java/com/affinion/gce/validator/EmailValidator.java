package com.affinion.gce.validator;

import com.affinion.gce.exception.DataValidationException;
import com.affinion.gce.model.asset.type.Email;
import com.affinion.gce.model.rule.RuleResult;
import com.affinion.gce.repository.AssetRepository;
import com.affinion.gce.service.RuleService;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Mono;

public class EmailValidator extends AssetDataValidator<Email> {
    public EmailValidator(AssetRepository repository,
                          RuleService service,
                          String brmsToken,
                          Email asset) {
        super(repository, service, brmsToken, asset);
    }

    @Override
    protected Mono<Email> validateData(RuleResult result) {
        return super.validateData(result).filter(a -> !StringUtils.isEmpty(a.getEmail()))
                .switchIfEmpty(Mono.error(new DataValidationException("Email can not be empty")))
                .flatMap(a -> validatePattern(a.getEmail(), "email", result));
    }
}
