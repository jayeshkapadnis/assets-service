package com.affinion.gce.validator;

import com.affinion.gce.model.asset.type.PostalAddress;
import com.affinion.gce.model.rule.RuleResult;
import com.affinion.gce.repository.AssetRepository;
import com.affinion.gce.service.RuleService;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Mono;

public class PostalAddressValidator extends AssetDataValidator<PostalAddress>{
    public PostalAddressValidator(AssetRepository repository,
                                  RuleService service,
                                  String brmsToken,
                                  PostalAddress asset) {
        super(repository, service, brmsToken, asset);
    }

    @Override
    protected Mono<PostalAddress> validateData(RuleResult result) {
        return super.validateData(result)
                .filter(a -> !StringUtils.isEmpty(a.getAddressLine1()))
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Address line 1 can not be empty")))
                .flatMap(a -> validatePattern(a.getAddressLine1(), "address_line1", result))
                .flatMap(a -> validateOptionalFieldPattern(a.getAddressLine2(), "address_line2", result))
                .flatMap(a -> validateOptionalFieldPattern(a.getAddressLine3(), "address_line3", result))
                .flatMap(a -> validateOptionalFieldPattern(a.getPostalCode(), "postal_code", result))
                .flatMap(a -> validateOptionalFieldPattern(a.getCity(), "city", result))
                .flatMap(a -> validateOptionalFieldPattern(a.getState(), "state", result))
                .flatMap(a -> validateOptionalFieldPattern(a.getCountry(), "country", result))
                .flatMap(a -> validateOptionalFieldPattern(a.getCounty(), "county", result))
                .flatMap(a -> validateOptionalFieldPattern(a.getCountryCode(), "county_code", result));
    }
}
