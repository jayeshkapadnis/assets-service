package com.affinion.gce.validator;

import com.affinion.gce.exception.DataValidationException;
import com.affinion.gce.model.asset.type.PaymentCard;
import com.affinion.gce.model.rule.RuleResult;
import com.affinion.gce.repository.AssetRepository;
import com.affinion.gce.service.RuleService;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Mono;

public class PaymentCardValidator extends AssetDataValidator<PaymentCard>{
    public PaymentCardValidator(AssetRepository repository,
                                RuleService service,
                                String brmsToken,
                                PaymentCard asset) {
        super(repository, service, brmsToken, asset);
    }

    @Override
    protected Mono<PaymentCard> validateData(RuleResult result) {
        return super.validateData(result)
                .filter(a -> !StringUtils.isEmpty(a.getNumber()))
                .switchIfEmpty(Mono.error(new DataValidationException("Card number can not be empty")))
                .flatMap(a -> validatePattern(a.getNumber(), "card_num", result))
                .flatMap(a -> validateOptionalFieldPattern(a.getName(), "card_name", result))
                .flatMap(a -> validateOptionalFieldPattern(a.getName(), "nick_name", result))
                .flatMap(a -> validateOptionalFieldPattern(a.getName(), "card_issuer", result))
                .flatMap(a -> validateOptionalFieldPattern(a.getName(), "card_scheme", result))
                .flatMap(a -> validateOptionalFieldPattern(a.getName(), "card_type", result))
                .flatMap(a -> validateOptionalFieldPattern(a.getName(), "exp_date_mmyyyy", result));
    }
}
