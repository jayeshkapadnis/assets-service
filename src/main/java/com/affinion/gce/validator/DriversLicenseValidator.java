package com.affinion.gce.validator;

import com.affinion.gce.model.asset.type.DriversLicense;
import com.affinion.gce.model.rule.RuleResult;
import com.affinion.gce.repository.AssetRepository;
import com.affinion.gce.service.RuleService;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Mono;

public class DriversLicenseValidator extends AssetDataValidator<DriversLicense> {
    public DriversLicenseValidator(AssetRepository repository, RuleService service,
                                   String brmsToken, DriversLicense asset) {
        super(repository, service, brmsToken, asset);
    }

    @Override
    protected Mono<DriversLicense> validateData(RuleResult result) {
        return super.validateData(result)
                .filter(a -> !StringUtils.isEmpty(a.getNumber()))
                .switchIfEmpty(Mono.error(new IllegalArgumentException("License Number can not be empty")))
                .flatMap(a -> validatePattern(a.getNumber(), "driverLicense", result));
    }
}
