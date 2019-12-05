package com.affinion.gce.service;

import com.affinion.gce.model.asset.AssetId;
import com.affinion.gce.model.asset.AssetType;
import com.affinion.gce.model.asset.type.PhoneNumber;
import com.affinion.gce.repository.AssetRepository;
import com.affinion.gce.validator.AssetDataValidator;
import com.affinion.gce.validator.PhoneNumberValidator;
import org.junit.Before;
import org.junit.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

public class AssetServiceTest {

    private RuleService ruleService;
    private AssetRepository repository;
    private AssetService service;

    @Before
    public void init(){
        ruleService = mock(RuleService.class);
        repository = mock(AssetRepository.class);
        service = new AssetService(repository, ruleService);
    }

    @Test
    public void newValidatorInstanceCreatedByAnnotation(){
        PhoneNumber asset = new PhoneNumber(new AssetId(null, AssetType.PHONE_NUMBER), 1234L, 21L, true, "9876543210");

        Mono<? extends AssetDataValidator> result = service.newValidator(asset, "1234");

        StepVerifier
                .create(result)
                .assertNext(t -> assertTrue(t instanceof PhoneNumberValidator))
                .verifyComplete();
    }

}