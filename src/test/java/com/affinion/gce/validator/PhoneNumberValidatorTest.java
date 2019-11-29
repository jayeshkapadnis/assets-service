package com.affinion.gce.validator;

import com.affinion.gce.model.AssetId;
import com.affinion.gce.model.AssetType;
import com.affinion.gce.model.PhoneNumber;
import com.affinion.gce.model.rule.RuleAttribute;
import com.affinion.gce.model.rule.RuleResult;
import com.affinion.gce.service.RuleService;
import org.junit.Before;
import org.junit.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Arrays;

import static com.shazam.shazamcrest.matcher.Matchers.sameBeanAs;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PhoneNumberValidatorTest {
    private PhoneNumberValidator validator;

    @Before
    public void init(){
        RuleService service = mock(RuleService.class);
        this.validator = new PhoneNumberValidator(service, "1234");
        when(service.rulesForAsset(any(), anyString())).thenReturn(Mono.just(mockRuleResult()));
    }

    @Test
    public void validateCorrectNumericPhoneNumber(){
        PhoneNumber asset = mockAsset("1234567890");

        Mono<PhoneNumber> result = validator.validate(asset);

        StepVerifier
                .create(result)
                .expectSubscription()
                .assertNext(a -> assertThat(a, sameBeanAs(asset)))
                .verifyComplete();
    }

    @Test
    public void validateIncorrectAlphaNumericPhoneNumber(){
        PhoneNumber asset = mockAsset("12345abc");

        Mono<PhoneNumber> result = validator.validate(asset);

        StepVerifier
                .create(result)
                .expectErrorMatches(e -> e instanceof IllegalArgumentException &&
                        e.getMessage().equals(String.format(PhoneNumberValidator.errorMessage,
                                asset.getNumber(), asset.type().getTypeKey())))
                .verify();
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