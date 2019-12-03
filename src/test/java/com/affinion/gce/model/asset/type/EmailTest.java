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
        return new Email(new AssetId(123L, AssetType.EMAIL), 12345L,
                123L, true, "some.other@host.com");
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