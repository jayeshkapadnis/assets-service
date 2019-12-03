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
        return NationalIdentifier.builder()
                .id(new AssetId(null, AssetType.NATIONAL_ID))
                .tenantId(123L)
                .memberId(12345L)
                .active(true)
                .identifier("A67552900099")
                .build();
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