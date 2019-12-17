package com.affinion.gce.model.asset.type;

import com.affinion.gce.jpa.entity.AssetAttribute;
import com.affinion.gce.model.asset.AssetId;
import com.affinion.gce.model.asset.AssetType;
import com.affinion.gce.model.asset.BaseAssetTest;

import java.util.Collections;
import java.util.List;

public class PassportTest extends BaseAssetTest<Passport> {

    @Override
    public Class<Passport> assetClazz() {
        return Passport.class;
    }

    @Override
    public Passport newAsset() {
        return new Passport(new AssetId(null, AssetType.PASSPORT), "My Passport", 12345L,
                123L, true, "L267590");
    }

    @Override
    public List<AssetAttribute> expectedAttributes() {
        return Collections.singletonList(new AssetAttribute("passport_number", "L267590"));
    }

    @Override
    public List<AssetAttribute> expectedHashedAttributes() {
        return Collections.singletonList(
                new AssetAttribute("passport_number", "07137070a284ebf4884cdf1c8b785e894349c401a923605db1df83c94f82a941")
        );
    }

    @Override
    public String serialized() {
        return "{\"id\":{\"id\":null,\"type\":\"CSS_Passport\"},\"name\":\"My Passport\"," +
                "\"tenantId\":123,\"active\":true,\"member_id\":12345,\"passport_number\":\"L267590\"}";
    }
}