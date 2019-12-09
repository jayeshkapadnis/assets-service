package com.affinion.gce.model.asset.type;

import com.affinion.gce.jpa.entity.AssetAttribute;
import com.affinion.gce.model.asset.AssetId;
import com.affinion.gce.model.asset.AssetType;
import com.affinion.gce.model.asset.BaseAssetTest;

import java.util.Collections;
import java.util.List;

public class EmailTest extends BaseAssetTest<Email> {

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
    public List<AssetAttribute> expectedAttributes() {
        return Collections.singletonList(new AssetAttribute("email", "some.other@host.com"));
    }

    @Override
    public List<AssetAttribute> expectedHashedAttributes() {
        return Collections.singletonList(
                new AssetAttribute("email", "3da5d7947bbaf727b98bce2c30a9f67b8b88e2cfd51d2c2118a167d6ebaddcf7")
        );
    }

    @Override
    public String serialized() {
        return "{\"id\":{\"id\":123,\"type\":\"CSS_EmailAddress\"},\"memberId\":12345," +
                "\"tenantId\":123,\"active\":true,\"email\":\"some.other@host.com\"}";
    }
}