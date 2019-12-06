package com.affinion.gce.validator;

import com.affinion.gce.exception.DataValidationException;
import com.affinion.gce.model.asset.type.ChildSsn;
import com.affinion.gce.model.rule.RuleResult;
import com.affinion.gce.repository.AssetRepository;
import com.affinion.gce.service.RuleService;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Mono;

public class ChildSsnValidator extends AssetDataValidator<ChildSsn>{
    public ChildSsnValidator(AssetRepository repository, RuleService service, String brmsToken, ChildSsn asset) {
        super(repository, service, brmsToken, asset);
    }

    @Override
    protected Mono<ChildSsn> validateData(RuleResult result) {
        return super.validateData(result)
                .filter(a -> !StringUtils.isEmpty(a.getSsn()))
                .switchIfEmpty(Mono.error(new DataValidationException("Child SSN can not be empty")))
                .flatMap(a -> validatePattern(a.getSsn(), "childssn", result));
    }
}
