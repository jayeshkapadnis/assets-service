package com.affinion.gce.validator;

import com.affinion.gce.model.asset.type.USBankAccount;
import com.affinion.gce.model.rule.RuleResult;
import com.affinion.gce.repository.AssetRepository;
import com.affinion.gce.service.RuleService;
import reactor.core.publisher.Mono;

public class USBankAccountValidator extends AssetDataValidator<USBankAccount> {
    public USBankAccountValidator(AssetRepository repository,
                                  RuleService service,
                                  String brmsToken,
                                  USBankAccount asset) {
        super(repository, service, brmsToken, asset);
    }

    @Override
    protected Mono<USBankAccount> validateData(RuleResult result) {
        return super.validateData(result)
                .flatMap(a -> validateOptionalFieldPattern(a.getIban(), "iban", result))
                .flatMap(a -> validateOptionalFieldPattern(a.getNumber(), "account_number", result))
                .flatMap(a -> validateOptionalFieldPattern(a.getNickName(), "nick_name", result))
                .flatMap(a -> validateOptionalFieldPattern(a.getType(), "account_type", result))
                .flatMap(a -> validateOptionalFieldPattern(a.getBankCode(), "bank_code", result))
                .flatMap(a -> validateOptionalFieldPattern(a.getBranchCode(), "branch_code", result))
                .flatMap(a -> validateOptionalFieldPattern(a.getSortCode(), "sort_code", result))
                .flatMap(a -> validateOptionalFieldPattern(a.getSecurityCode(), "security_code", result))
                .flatMap(a -> validateOptionalFieldPattern(a.getRoutingNumber(), "routing_number", result))
                .flatMap(a -> validateDuplicate(a.type().id()));
    }
}
