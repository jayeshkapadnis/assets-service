package com.affinion.gce.validator;

import com.affinion.gce.model.asset.type.KeyTag;
import com.affinion.gce.model.rule.RuleResult;
import com.affinion.gce.repository.AssetRepository;
import com.affinion.gce.service.RuleService;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Mono;

public class KeyTagValidator extends AssetDataValidator<KeyTag>{
    public KeyTagValidator(AssetRepository repository,
                           RuleService service,
                           String brmsToken,
                           KeyTag asset) {
        super(repository, service, brmsToken, asset);
    }

    @Override
    protected Mono<KeyTag> validateData(RuleResult result) {
        return super.validateData(result)
                .filter(k -> !StringUtils.isEmpty(asset.getSerialNumber()))
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Serial Number can not be empty")))
                .flatMap(a -> validatePattern(a.getSerialNumber(), "serial_number", result));
    }
}
