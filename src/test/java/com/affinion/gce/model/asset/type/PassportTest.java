package com.affinion.gce.model.asset.type;

import com.affinion.gce.jpa.entity.AssetAttributeEntity;
import com.affinion.gce.model.asset.AssetId;
import com.affinion.gce.model.asset.AssetType;

import java.util.Collections;
import java.util.List;

public class PassportTest extends BaseAssetTest<Passport>{

    @Override
    public Class<Passport> assetClazz() {
        return Passport.class;
    }

    @Override
    public Passport newAsset() {
        return new Passport(new AssetId(null, AssetType.PASSPORT), 12345L,
                123L, true, "L267590");
    }

    @Override
    public List<AssetAttributeEntity> expectedAttributes() {
        return Collections.singletonList(new AssetAttributeEntity("passport_number", "L267590"));
    }

    @Override
    public String serialized() {
        return "{\"id\":{\"id\":null,\"type\":\"CSS_Passport\"},\"memberId\":12345," +
                "\"tenantId\":123,\"active\":true,\"passport_number\":\"L267590\"}";
    }
}