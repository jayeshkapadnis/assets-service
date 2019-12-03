package com.affinion.gce.service;

import com.affinion.gce.model.asset.AssetType;
import com.affinion.gce.model.rule.RuleAttribute;
import com.affinion.gce.model.rule.RuleResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Collections;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static com.shazam.shazamcrest.matcher.Matchers.sameBeanAs;
import static org.junit.Assert.assertThat;

public class RuleServiceTest {
    private static int port = 8888;
    private static ObjectMapper mapper = new ObjectMapper();
    @Rule
    public WireMockRule wireMockRule = new WireMockRule(options().port(port));
    private final RuleService service = new RuleService(WebClient
            .builder(),String.format("http://localhost:%d", port));

    @Test
    public void testGetRuleAttributesForPhoneNumberAssetWithServerError() {
        givenThat(post(urlMatching("/attributevalues")).willReturn(aResponse().withStatus(500)));
        Mono<RuleResult> result = service.rulesForAsset(AssetType.PHONE_NUMBER, "12345");

        StepVerifier.create(result)
                .expectError(IllegalStateException.class)
                .verify();

    }

    @Test
    public void testGetRuleAttributesForPhoneNumberAssetWithClientError() {
        givenThat(post(urlMatching("/attributevalues")).willReturn(aResponse().withStatus(403)));
        Mono<RuleResult> result = service.rulesForAsset(AssetType.PHONE_NUMBER, "12345");

        StepVerifier.create(result)
                .expectError(IllegalArgumentException.class)
                .verify();

    }

    @Test
    public void testGetRuleAttributesForPhoneNumberAssetWithSuccess() throws JsonProcessingException {
        List<RuleAttribute> attributes = Collections.singletonList(new RuleAttribute("asset_count_max", "10"));
        RuleResult response = new RuleResult(attributes, "Some rule", 123);

        givenThat(post(urlMatching("/attributevalues"))
                .willReturn(aResponse()
                        .withStatus(200).withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBody(mapper.writeValueAsString(response))));

        Mono<RuleResult> result = service.rulesForAsset(AssetType.PHONE_NUMBER, "12345");

        StepVerifier.create(result)
                .expectSubscription()
                .assertNext(r -> assertThat(r, sameBeanAs(response)))
                .verifyComplete();
    }
}