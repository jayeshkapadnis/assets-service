package com.affinion.gce.validator;

import com.affinion.gce.model.asset.AssetId;
import com.affinion.gce.model.asset.AssetType;
import com.affinion.gce.model.asset.type.PhoneNumber;
import com.affinion.gce.model.rule.RuleAttribute;
import com.affinion.gce.model.rule.RuleResult;
import com.affinion.gce.repository.AssetRepository;
import com.affinion.gce.service.RuleService;
import org.junit.Before;
import org.junit.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Arrays;

import static com.shazam.shazamcrest.matcher.Matchers.sameBeanAs;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PhoneNumberValidatorTest {
    private AssetRepository repository;
    private RuleService service;

    @Before
    public void init(){
        service = mock(RuleService.class);
        this.repository = mock(AssetRepository.class);
        when(service.rulesForAsset(any(), anyString())).thenReturn(Mono.just(mockRuleResult()));
        when(repository.findAssetCountByMemberIdAndType(anyLong(), any())).thenReturn(Mono.just(2L));
    }

    @Test
    public void validateCorrectNumericPhoneNumber(){
        PhoneNumber asset = mockAsset("1234567890");
        PhoneNumberValidator validator = new PhoneNumberValidator(repository, service, "1234", asset);
        Mono<PhoneNumber> result = validator.validate();

        StepVerifier
                .create(result)
                .expectSubscription()
                .assertNext(a -> assertThat(a, sameBeanAs(asset)))
                .verifyComplete();
    }

    @Test
    public void validateIncorrectAlphaNumericPhoneNumber(){
        PhoneNumber asset = mockAsset("12345abc");
        PhoneNumberValidator validator = new PhoneNumberValidator(repository, service, "1234", asset);
        Mono<PhoneNumber> result = validator.validate();

        StepVerifier
                .create(result)
                .expectErrorMatches(e -> e instanceof IllegalArgumentException &&
                        e.getMessage().equals(String.format(PhoneNumberValidator.formatError,
                                asset.getNumber(), asset.type().getTypeKey())))
                .verify();
    }

    @Test
    public void validateMaxAssetCountExceededForCreateAsset(){
        when(repository.findAssetCountByMemberIdAndType(anyLong(), any())).thenReturn(Mono.just(10L));

        PhoneNumber asset = mockAsset("1234567890");
        PhoneNumberValidator validator = new PhoneNumberValidator(repository, service, "1234", asset);

        Mono<PhoneNumber> result = validator.validate();

        StepVerifier
                .create(result)
                .expectErrorMatches(e -> e instanceof IllegalStateException &&
                        e.getMessage().equals(String.format(PhoneNumberValidator.assetCountError, asset.type().getTypeKey())))
                .verify();
    }

    @Test
    public void validateMaxAssetCountShouldNotExecuteOnUpdateAsset(){
        when(repository.findAssetCountByMemberIdAndType(anyLong(), any())).thenReturn(Mono.just(10L));

        PhoneNumber asset = mockAsset("1234567890");
        asset.getId().setId(123L);
        PhoneNumberValidator validator = new PhoneNumberValidator(repository, service, "1234", asset);

        Mono<PhoneNumber> result = validator.validate();

        StepVerifier
                .create(result)
                .expectSubscription()
                .assertNext(a -> assertThat(a, sameBeanAs(asset)))
                .verifyComplete();
    }

    private RuleResult mockRuleResult(){
        String phoneNumberPattern = "\\d{10}|(?:\\d{3}-){2}\\d{4}|\\(\\d{3}\\)\\d{3}-?\\d{4}";
        return new RuleResult(Arrays.asList(
                new RuleAttribute("phone", phoneNumberPattern),
                new RuleAttribute("asset_count_max", "10")),
                "Phone Number Rule", 1234);
    }

    private PhoneNumber mockAsset(String number){
        return new PhoneNumber(
                new AssetId(null, AssetType.PHONE_NUMBER),
                1234L, 4321L, true, number);
    }

}