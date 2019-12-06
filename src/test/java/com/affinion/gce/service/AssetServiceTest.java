package com.affinion.gce.service;

import com.affinion.gce.jpa.entity.AssetEntity;
import com.affinion.gce.model.asset.Asset;
import com.affinion.gce.model.asset.AssetId;
import com.affinion.gce.model.asset.AssetType;
import com.affinion.gce.model.asset.type.PhoneNumber;
import com.affinion.gce.model.rule.RuleAttribute;
import com.affinion.gce.model.rule.RuleResult;
import com.affinion.gce.repository.AssetRepository;
import com.affinion.gce.validator.AssetDataValidator;
import com.affinion.gce.validator.PhoneNumberValidator;
import org.junit.Before;
import org.junit.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;

import static com.shazam.shazamcrest.matcher.Matchers.sameBeanAs;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AssetServiceTest {

    private RuleService ruleService;
    private AssetRepository repository;
    private AssetService service;
    private VaultService vaultService;

    @Before
    public void init(){
        ruleService = mock(RuleService.class);
        repository = mock(AssetRepository.class);
        vaultService = mock(VaultService.class);
        service = new AssetService(repository, ruleService, vaultService);
    }

    @Test
    public void newValidatorInstanceCreatedByAnnotation(){
        Mono<? extends AssetDataValidator> result = service.newValidator(newAsset(), "1234");

        StepVerifier
                .create(result)
                .assertNext(t -> assertTrue(t instanceof PhoneNumberValidator))
                .verifyComplete();
    }

    @Test
    public void testSuccessCreateNewAsset(){
        Asset asset = newAsset();
        AssetEntity assetEntity = new AssetEntity(asset);
        when(repository.countAllByMemberIdAndType(asset.getMemberId(), asset.type())).thenReturn(0L);
        when(ruleService.rulesForAsset(AssetType.PHONE_NUMBER, "1234")).thenReturn(Mono.just(mockResult()));
        when(vaultService.saveAsset(any(AssetEntity.class))).thenReturn(Mono.just("token_string"));
        assetEntity.setId(1L);
        when(repository.save(any(AssetEntity.class))).thenReturn(assetEntity);

        Mono<Asset> actual = service.addAsset(asset, "1234");

        asset.getId().setId(1L);

        StepVerifier.create(actual)
                .assertNext(a -> assertThat(a, sameBeanAs(asset)))
                .verifyComplete();
    }

    private RuleResult mockResult(){
        List<RuleAttribute> attributes = Arrays.asList(new RuleAttribute("asset_count_max", "10"),
                new RuleAttribute("phone", "\\d{10}|(?:\\d{3}-){2}\\d{4}|\\(\\d{3}\\)\\d{3}-?\\d{4}"));
        return new RuleResult(attributes, "Phone Number Rule", 123);
    }

    private Asset newAsset(){
        return new PhoneNumber(new AssetId(null, AssetType.PHONE_NUMBER), 1234L,
                21L, true, "9876543210");
    }

}