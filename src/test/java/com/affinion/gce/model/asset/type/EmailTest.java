package com.affinion.gce.model.asset.type;

import com.affinion.gce.jpa.entity.AssetAttributeEntity;
import com.affinion.gce.model.asset.AssetId;
import com.affinion.gce.model.asset.AssetType;

import java.util.Collections;
import java.util.List;

public class EmailTest extends BaseAssetTest<Email>{

    @Override
    public Class<Email> assetClazz() {
        return Email.class;
    }

    @Override
    public Email newAsset() {
        return Email.builder()
                .id(new AssetId(123L, AssetType.EMAIL))
                .tenantId(123L)
                .memberId(12345L)
                .active(true)
                .email("some.other@host.com")
                .build();
    }

    @Override
    public List<AssetAttributeEntity> expectedAttributes() {
        return Collections.singletonList(new AssetAttributeEntity("email", "some.other@host.com"));
    }

    @Override
    public String serialized() {
        return "{\"id\":{\"id\":123,\"type\":\"CSS_EmailAddress\"},\"memberId\":12345," +
                "\"tenantId\":123,\"active\":true,\"email\":\"some.other@host.com\"}";
    }
}