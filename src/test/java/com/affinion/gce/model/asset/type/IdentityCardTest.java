package com.affinion.gce.model.asset.type;

import com.affinion.gce.jpa.entity.AssetAttributeEntity;
import com.affinion.gce.model.asset.AssetId;
import com.affinion.gce.model.asset.AssetType;

import java.util.Collections;
import java.util.List;

public class IdentityCardTest extends BaseAssetTest<IdentityCard>{

    @Override
    public Class<IdentityCard> assetClazz() {
        return IdentityCard.class;
    }

    @Override
    public IdentityCard newAsset() {
        return IdentityCard.builder()
                .id(new AssetId(123L, AssetType.ID_CARD))
                .tenantId(123L)
                .memberId(12345L)
                .active(true)
                .identifier("PAN123NUMB")
                .build();
    }

    @Override
    public List<AssetAttributeEntity> expectedAttributes() {
        return Collections.singletonList(new AssetAttributeEntity("identity_card", "PAN123NUMB"));
    }

    @Override
    public String serialized() {
        return "{\"id\":{\"id\":123,\"type\":\"CSS_IdentityCard\"},\"memberId\":12345," +
                "\"tenantId\":123,\"active\":true,\"identity_card\":\"PAN123NUMB\"}";
    }
}