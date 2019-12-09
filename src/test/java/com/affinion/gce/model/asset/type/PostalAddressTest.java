package com.affinion.gce.model.asset.type;

import com.affinion.gce.jpa.entity.AssetAttribute;
import com.affinion.gce.model.asset.AssetId;
import com.affinion.gce.model.asset.AssetType;
import com.affinion.gce.model.asset.BaseAssetTest;

import java.util.Arrays;
import java.util.List;

public class PostalAddressTest extends BaseAssetTest<PostalAddress> {

    @Override
    public Class<PostalAddress> assetClazz() {
        return PostalAddress.class;
    }

    @Override
    public PostalAddress newAsset() {
        PostalAddress asset = new PostalAddress();
        asset.setId(new AssetId(null, AssetType.POSTAL_ADDRESS));
        asset.setTenantId(123L);
        asset.setMemberId(12345L);
        asset.setActive(true);
        asset.setAddressLine1("E-103, Society");
        asset.setAddressLine2("Near Some landmark");
        asset.setCity("Wichita");
        asset.setCountry("United States");
        asset.setCountryCode("USA");
        asset.setState("Kansas");
        asset.setPostalCode("67502");
        return asset;
    }

    @Override
    public List<AssetAttribute> expectedAttributes() {
        return Arrays.asList(
                new AssetAttribute("address_line1", "E-103, Society"),
                new AssetAttribute("address_line2", "Near Some landmark"),
                new AssetAttribute("city", "Wichita"),
                new AssetAttribute("state", "Kansas"),
                new AssetAttribute("country", "United States"),
                new AssetAttribute("country_code", "USA"),
                new AssetAttribute("postal_code", "67502")
        );
    }

    @Override
    public List<AssetAttribute> expectedHashedAttributes() {
        return Arrays.asList(
                new AssetAttribute("address_line1", "fa647f271107bebcc5e2b826f3b4377ef92fc60b6efbee94e949b0d806b5a957"),
                new AssetAttribute("address_line2", "Near Some landmark"),
                new AssetAttribute("city", "Wichita"),
                new AssetAttribute("state", "Kansas"),
                new AssetAttribute("country", "United States"),
                new AssetAttribute("country_code", "USA"),
                new AssetAttribute("postal_code", "67502")
        );
    }

    @Override
    public String serialized() {
        return "{\"id\":{\"id\":null,\"type\":\"CSS_PostalAddress\"},\"memberId\":12345,\"tenantId\":123," +
                "\"active\":true,\"city\":\"Wichita\",\"state\":\"Kansas\",\"country\":\"United States\"," +
                "\"county\":null,\"address_line1\":\"E-103, Society\",\"address_line2\":\"Near Some landmark\"," +
                "\"address_line3\":null,\"country_code\":\"USA\",\"postal_code\":\"67502\"}";
    }
}