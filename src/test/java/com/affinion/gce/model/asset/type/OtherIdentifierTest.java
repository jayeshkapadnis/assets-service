package com.affinion.gce.model.asset.type;

import com.affinion.gce.jpa.entity.AssetAttributeEntity;
import com.affinion.gce.model.asset.AssetId;
import com.affinion.gce.model.asset.AssetType;
import com.affinion.gce.model.asset.BaseAssetTest;

import java.util.Collections;
import java.util.List;

public class OtherIdentifierTest extends BaseAssetTest<OtherIdentifier> {

    @Override
    public Class<OtherIdentifier> assetClazz() {
        return OtherIdentifier.class;
    }

    @Override
    public OtherIdentifier newAsset() {
        return new OtherIdentifier(new AssetId(null, AssetType.OTHER_ID), 12345L,
                123L, true, "SOME_ONLINE_ID");
    }

    @Override
    public List<AssetAttributeEntity> expectedAttributes() {
        return Collections.singletonList(new AssetAttributeEntity("otherID", "SOME_ONLINE_ID"));
    }

    @Override
    public List<AssetAttributeEntity> expectedHashedAttributes() {
        return Collections.singletonList(
                new AssetAttributeEntity("otherID", "62f90f9afd47e56be0d2e49f92aacf7eaf8171f806ac957c2d7ac64dc741a9d6")
        );
    }

    @Override
    public String serialized() {
        return "{\"id\":{\"id\":null,\"type\":\"CSS_OtherID\"},\"memberId\":12345," +
                "\"tenantId\":123,\"active\":true,\"online_id\":\"SOME_ONLINE_ID\"}";
    }
}