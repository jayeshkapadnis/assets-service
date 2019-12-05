package com.affinion.gce.model.asset.type;

import com.affinion.gce.annotation.Validator;
import com.affinion.gce.jpa.entity.AssetAttributeEntity;
import com.affinion.gce.model.asset.Asset;
import com.affinion.gce.validator.PostalAddressValidator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Stream;

@NoArgsConstructor
@Getter
@Setter
@Validator(PostalAddressValidator.class)
public class PostalAddress extends Asset {
    @JsonProperty("address_line1")
    private String addressLine1;
    @JsonProperty("address_line2")
    private String addressLine2;
    @JsonProperty("address_line3")
    private String addressLine3;
    private String city;
    private String state;
    private String country;
    private String county;
    @JsonProperty("country_code")
    private String countryCode;
    @JsonProperty("postal_code")
    private String postalCode;

    @Override
    public List<AssetAttributeEntity> attributes() {
        return fromStream(Stream.of(
                newAttribute("address_line1", getAddressLine1()),
                newAttribute("address_line2", getAddressLine2()),
                newAttribute("address_line3", getAddressLine3()),
                newAttribute("city", getCity()),
                newAttribute("state", getState()),
                newAttribute("county", getCounty()),
                newAttribute("country", getCountry()),
                newAttribute("country_code", getCountryCode()),
                newAttribute("postal_code", getPostalCode())
        ));
    }
}
