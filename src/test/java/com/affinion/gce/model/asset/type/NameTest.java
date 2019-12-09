package com.affinion.gce.model.asset.type;

import com.affinion.gce.jpa.entity.AssetAttribute;
import com.affinion.gce.model.asset.AssetId;
import com.affinion.gce.model.asset.AssetType;
import com.affinion.gce.model.asset.BaseAssetTest;

import java.util.Arrays;
import java.util.List;

public class NameTest extends BaseAssetTest<Name> {

    @Override
    public Class<Name> assetClazz() {
        return Name.class;
    }

    @Override
    public Name newAsset() {
        Name asset = new Name();
        asset.setId(new AssetId(null, AssetType.NAME));
        asset.setTenantId(123L);
        asset.setMemberId(12345L);
        asset.setActive(true);
        asset.setFirstName("John");
        asset.setMiddleName("Peter");
        asset.setLastName("Doe");
        return asset;
    }

    @Override
    public List<AssetAttribute> expectedAttributes() {
        return Arrays.asList(
                new AssetAttribute("first_name", "John"),
                new AssetAttribute("middle_name", "Peter"),
                new AssetAttribute("last_name", "Doe")
        );
    }

    @Override
    public List<AssetAttribute> expectedHashedAttributes() {
        return Arrays.asList(
                new AssetAttribute("first_name", "a8cfcd74832004951b4408cdb0a5dbcd8c7e52d43f7fe244bf720582e05241da"),
                new AssetAttribute("middle_name", "Peter"),
                new AssetAttribute("last_name", "Doe")
        );
    }

    @Override
    public String serialized() {
        return "{\"id\":{\"id\":null,\"type\":\"CSS_MemberName\"},\"memberId\":12345," +
                "\"tenantId\":123,\"active\":true,\"first_name\":\"John\"," +
                "\"middle_name\":\"Peter\",\"last_name\":\"Doe\"}";
    }
}