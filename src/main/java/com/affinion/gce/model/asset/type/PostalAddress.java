package com.affinion.gce.model.asset.type;

import com.affinion.gce.annotation.Validator;
import com.affinion.gce.jpa.entity.AssetAttributeEntity;
import com.affinion.gce.model.asset.Asset;
import com.affinion.gce.validator.PostalAddressValidator;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Validator(PostalAddressValidator.class)
public class PostalAddress extends Asset {
    private String addressLine1;
    private String addressLine2;
    private String addressLine3;
    private String city;
    private String state;
    private String country;
    private String county;
    private String countryCode;
    private String postalCode;

    @Override
    public String hash() {
        return null;
    }

    @Override
    public List<AssetAttributeEntity> attributes() {
        return Arrays.asList(
                new AssetAttributeEntity("address_line1", getAddressLine1()),
                new AssetAttributeEntity("address_line2", getAddressLine2()),
                new AssetAttributeEntity("address_line3", getAddressLine3()),
                new AssetAttributeEntity("city", getCity()),
                new AssetAttributeEntity("county", getCounty()),
                new AssetAttributeEntity("state", getState()),
                new AssetAttributeEntity("country", getCountry()),
                new AssetAttributeEntity("country_code", getCountryCode()),
                new AssetAttributeEntity("postal_code", getPostalCode())
        );
    }
}
