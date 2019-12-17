package com.affinion.gce.model.asset.type;

import com.affinion.gce.jpa.entity.AssetAttribute;
import com.affinion.gce.model.asset.AssetId;
import com.affinion.gce.model.asset.AssetType;
import com.affinion.gce.model.asset.BaseAssetTest;

import java.util.Collections;
import java.util.List;

public class IdentityCardTest extends BaseAssetTest<IdentityCard> {

    @Override
    public Class<IdentityCard> assetClazz() {
        return IdentityCard.class;
    }

    @Override
    public IdentityCard newAsset() {
        return new IdentityCard(new AssetId(123L, AssetType.ID_CARD), "idName", 12345L,
                123L, true, "PAN123NUMB");
    }

    @Override
    public List<AssetAttribute> expectedAttributes() {
        return Collections.singletonList(new AssetAttribute("identity_card", "PAN123NUMB"));
    }

    @Override
    public List<AssetAttribute> expectedHashedAttributes() {
        return Collections.singletonList(
                new AssetAttribute("identity_card", "fe94042c478bee1b90b363720bc0f92a962630a156c3923c17714b56d3356fb8")
        );
    }

    @Override
    public String serialized() {
        return "{\"id\":{\"id\":123,\"type\":\"CSS_IdentityCard\"},\"name\":\"idName\"," +
                "\"tenantId\":123,\"active\":true,\"member_id\":12345,\"identity_card\":\"PAN123NUMB\"}";
    }
}