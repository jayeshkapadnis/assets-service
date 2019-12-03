package com.affinion.gce.model.asset.type;

import com.affinion.gce.jpa.entity.AssetAttributeEntity;
import com.affinion.gce.model.asset.AssetId;
import com.affinion.gce.model.asset.AssetType;

import java.util.Arrays;
import java.util.List;

public class NameTest extends BaseAssetTest<Name>{

    @Override
    public Class<Name> assetClazz() {
        return Name.class;
    }

    @Override
    public Name newAsset() {
        Name asset = new Name();
        asset.setId(new AssetId(null, AssetType.NAME));
        asset.setTenantId(123L);
        asset.setMemberId(12345L);
        asset.setActive(true);
        asset.setFirstName("John");
        asset.setMiddleName("Peter");
        asset.setLastName("Doe");
        return asset;
    }

    @Override
    public List<AssetAttributeEntity> expectedAttributes() {
        return Arrays.asList(
                new AssetAttributeEntity("first_name", "John"),
                new AssetAttributeEntity("middle_name", "Peter"),
                new AssetAttributeEntity("last_name", "Doe")
        );
    }

    @Override
    public String serialized() {
        return "{\"id\":{\"id\":null,\"type\":\"CSS_MemberName\"},\"memberId\":12345," +
                "\"tenantId\":123,\"active\":true,\"first_name\":\"John\"," +
                "\"middle_name\":\"Peter\",\"last_name\":\"Doe\"}";
    }
}