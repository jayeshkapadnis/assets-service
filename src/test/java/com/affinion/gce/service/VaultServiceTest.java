package com.affinion.gce.service;

import com.affinion.gce.exception.RestClientException;
import com.affinion.gce.jpa.entity.AssetEntity;
import com.affinion.gce.model.asset.AssetId;
import com.affinion.gce.model.asset.AssetType;
import com.affinion.gce.model.asset.type.PhoneNumber;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static com.affinion.gce.service.VaultService.TOKEN_URI;
import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static org.junit.Assert.assertEquals;

public class VaultServiceTest {
    private static int port = 8888;
    @Rule
    public WireMockRule wireMockRule = new WireMockRule(options().port(port));
    private final VaultService service = new VaultService(String.format("http://localhost:%d", port), "api-key",
            WebClient.builder().clientConnector(new ReactorClientHttpConnector()));

    @Test
    public void testSaveAttributeWithSuccessTokenResponse(){
        givenThat(post(urlMatching(TOKEN_URI))
                .willReturn(aResponse()
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBody("{\"Value\": \"token\"}")
                        .withStatus(200)));

        Mono<String> source = service.saveAsset(newAsset());

        StepVerifier.create(source)
                .assertNext(a -> assertEquals("token", a))
                .verifyComplete();
    }

    @Test
    public void testSaveAssetWithFailureResponse(){
        givenThat(post(urlMatching(TOKEN_URI))
                .willReturn(aResponse()
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBody("{\"Value\": \"Transaction Failure\"}")
                        .withStatus(200)));

        Mono<String> source = service.saveAsset(newAsset());

        StepVerifier.create(source)
                .expectErrorMatches(e -> e instanceof RestClientException &&
                        e.getMessage().equalsIgnoreCase("Error while getting token"))
                .verify();
    }

    @Test
    public void testSaveAssetWithEmptyToken(){
        givenThat(post(urlMatching(TOKEN_URI))
                .willReturn(aResponse()
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBody("{\"Value\": \"\"}")
                        .withStatus(200)));

        Mono<String> source = service.saveAsset(newAsset());

        StepVerifier.create(source)
                .expectErrorMatches(e -> e instanceof RestClientException &&
                        e.getMessage().equalsIgnoreCase("Token not received"))
                .verify();
    }

    @Test
    public void testSaveAssetWithEmptyResponse(){
        givenThat(post(urlMatching(TOKEN_URI))
                .willReturn(aResponse()
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBody(" ")
                        .withStatus(200)));

        Mono<String> source = service.saveAsset(newAsset());

        StepVerifier.create(source)
                .expectError()
                .verify();
    }

    @Test
    public void testSaveAssetWithUnauthorizedException(){
        givenThat(post(urlMatching(TOKEN_URI))
                .willReturn(aResponse()
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBody("{\"error\": \"Unauthorized\"}")
                        .withStatus(HttpStatus.UNAUTHORIZED.value())));

        Mono<String> source = service.saveAsset(newAsset());

        StepVerifier.create(source)
                .expectError(WebClientResponseException.Unauthorized.class)
                .verify();
    }

    @Test
    public void testSaveAssetWithBadRequestException(){
        givenThat(post(urlMatching(TOKEN_URI))
                .willReturn(aResponse()
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBody("{\"error\": \"Missing Asset\"}")
                        .withStatus(HttpStatus.BAD_REQUEST.value())));

        Mono<String> source = service.saveAsset(null);

        StepVerifier.create(source)
                .expectError(WebClientResponseException.BadRequest.class)
                .verify();
    }

    private AssetEntity newAsset(){
        return new AssetEntity(
                new PhoneNumber(new AssetId(123L, AssetType.PHONE_NUMBER), "Home Phone",
                        1234L, 123L, true, "1234567890"));
    }

}