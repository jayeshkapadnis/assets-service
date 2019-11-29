package com.affinion.gce.model;

import com.affinion.gce.jpa.entity.AssetAttributeEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class PostalAddress extends Asset{
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
        return super.attributes();
    }
}
