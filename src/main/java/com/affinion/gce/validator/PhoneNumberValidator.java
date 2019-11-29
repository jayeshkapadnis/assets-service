package com.affinion.gce.validator;

import com.affinion.gce.model.PhoneNumber;
import com.affinion.gce.model.rule.RuleResult;
import com.affinion.gce.repository.AssetRepository;
import com.affinion.gce.service.RuleService;
import reactor.core.publisher.Mono;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhoneNumberValidator extends AssetDataValidator<PhoneNumber>{

    public static String errorMessage = "Phone Number %s doesn't match for asset type %s";
    private AssetRepository repository;

    public PhoneNumberValidator(RuleService service, String brmsToken) {
        super(service, brmsToken);
    }

    public PhoneNumberValidator(AssetRepository repository, RuleService service, String brmsToken){
        this(service, brmsToken);
        this.repository = repository;
    }

    @Override
    protected PhoneNumber validateData(PhoneNumber asset, RuleResult result) throws Exception {
        super.validateData(asset, result);
        result.attributeValueByKey("phone")
                .map(Pattern::compile)
                .filter(p -> {
                    Matcher matcher = p.matcher(asset.getNumber());
                    return matcher.matches();
                }).orElseThrow(() -> new IllegalArgumentException(
                        String.format(errorMessage, asset.getNumber(), asset.type().getTypeKey()))
        );
        return asset;
    }

    @Override
    protected PhoneNumber validateAssetCount(PhoneNumber asset, Integer maxCount) {
        repository.findAssetCountByMemberIdAndType(asset.getMemberId(), asset.type())
                .filterWhen(l -> Mono.just(l <= maxCount))
                .switchIfEmpty(Mono.error(new IllegalStateException("")))
                .map(a -> asset);
        return asset;
    }
}
