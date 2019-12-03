package com.affinion.gce.model.asset.type;

import com.affinion.gce.jpa.entity.AssetAttributeEntity;
import com.affinion.gce.model.asset.AssetId;
import com.affinion.gce.model.asset.AssetType;

import java.util.Collections;
import java.util.List;

public class NationalIdentifierTest extends BaseAssetTest<NationalIdentifier>{

    @Override
    public Class<NationalIdentifier> assetClazz() {
        return NationalIdentifier.class;
    }

    @Override
    public NationalIdentifier newAsset() {
        return new NationalIdentifier(new AssetId(null, AssetType.NATIONAL_ID), 12345L,
                123L, true, "A67552900099");
    }

    @Override
    public List<AssetAttributeEntity> expectedAttributes() {
        return Collections.singletonList(new AssetAttributeEntity("nid", "A67552900099"));
    }

    @Override
    public String serialized() {
        return "{\"id\":{\"id\":null,\"type\":\"CSS_NationalId\"},\"memberId\":12345," +
                "\"tenantId\":123,\"active\":true,\"nationalId\":\"A67552900099\"}";
    }
}