package com.affinion.gce.validator;

import com.affinion.gce.model.asset.type.NationalIdentifier;
import com.affinion.gce.model.rule.RuleResult;
import com.affinion.gce.repository.AssetRepository;
import com.affinion.gce.service.RuleService;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Mono;

public class NationalIdValidator extends AssetDataValidator<NationalIdentifier>{
    public NationalIdValidator(AssetRepository repository, RuleService service,
                               String brmsToken, NationalIdentifier asset) {
        super(repository, service, brmsToken, asset);
    }

    @Override
    protected Mono<NationalIdentifier> validateData(RuleResult result) {
        return super.validateData(result)
                .filter(a -> !StringUtils.isEmpty(a.getIdentifier()))
                .switchIfEmpty(Mono.error(new IllegalArgumentException("National ID can not be empty")))
                .flatMap(a -> validatePattern(a.getIdentifier(), "national_Id", result));
    }
}
