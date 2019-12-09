package com.affinion.gce.model.asset.type;

import com.affinion.gce.jpa.entity.AssetAttribute;
import com.affinion.gce.model.asset.AssetId;
import com.affinion.gce.model.asset.AssetType;
import com.affinion.gce.model.asset.BaseAssetTest;

import java.util.Collections;
import java.util.List;

public class NationalIdentifierTest extends BaseAssetTest<NationalIdentifier> {

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
    public List<AssetAttribute> expectedAttributes() {
        return Collections.singletonList(new AssetAttribute("nid", "A67552900099"));
    }

    @Override
    public List<AssetAttribute> expectedHashedAttributes() {
        return Collections.singletonList(
                new AssetAttribute("nid", "285b7858eb0b36467768cbfc247aad36f3ece0f939af657134ce2d6a4f18dc4f")
        );
    }

    @Override
    public String serialized() {
        return "{\"id\":{\"id\":null,\"type\":\"CSS_NationalId\"},\"memberId\":12345," +
                "\"tenantId\":123,\"active\":true,\"nationalId\":\"A67552900099\"}";
    }
}