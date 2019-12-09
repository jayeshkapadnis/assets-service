package com.affinion.gce.model.asset.type;

import com.affinion.gce.annotation.Validator;
import com.affinion.gce.jpa.entity.AssetAttribute;
import com.affinion.gce.jpa.entity.AssetEntity;
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

    public PostalAddress(AssetEntity entity) {
        super(entity);
        this.addressLine1 = entity.attributeValue("address_line1");
        this.addressLine2 = entity.attributeValue("address_line2");
        this.addressLine3 = entity.attributeValue("address_line3");
        this.city = entity.attributeValue("city");
        this.state = entity.attributeValue("state");
        this.county = entity.attributeValue("county");
        this.country = entity.attributeValue("country");
        this.countryCode = entity.attributeValue("country_code");
        this.postalCode = entity.attributeValue("postal_code");
    }

    @Override
    public List<AssetAttribute> attributes() {
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
