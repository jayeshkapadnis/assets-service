package com.affinion.gce.model.request;

import com.affinion.gce.jpa.entity.AssetEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AssetVaultRequest {
    @JsonProperty("Value")
    private AssetEntity value;
    @JsonProperty("BusinessDomainId")
    private Integer businessDomainId = 1;
    @JsonProperty("isSearchable")
    private Integer searchable = 0;
    @JsonProperty("token")
    private String token;
}
