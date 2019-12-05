package com.affinion.gce.model.asset.type;

import com.affinion.gce.jpa.entity.AssetAttributeEntity;
import com.affinion.gce.model.asset.AssetId;
import com.affinion.gce.model.asset.AssetType;
import com.affinion.gce.model.asset.BaseAssetTest;

import java.util.Arrays;
import java.util.List;

public class ChildSsnTest extends BaseAssetTest<ChildSsn> {

    @Override
    public Class<ChildSsn> assetClazz() {
        return ChildSsn.class;
    }

    @Override
    public ChildSsn newAsset(){
        ChildSsn asset = new ChildSsn();
        asset.setSsn("1231312312");
        asset.setFirstName("John");
        asset.setLastName("Doe");
        asset.setId(new AssetId(123L, AssetType.US_CHILD_SSN));
        asset.setMemberId(12345L);
        asset.setTenantId(2345L);
        return asset;
    }

    @Override
    public List<AssetAttributeEntity> expectedAttributes() {
        return Arrays.asList(
                new AssetAttributeEntity("first_name", "John"),
                new AssetAttributeEntity("last_name", "Doe"),
                new AssetAttributeEntity("childssn", "1231312312")
        );
    }

    @Override
    public List<AssetAttributeEntity> expectedHashedAttributes() {
        return Arrays.asList(
                new AssetAttributeEntity("first_name", "John"),
                new AssetAttributeEntity("last_name", "Doe"),
                new AssetAttributeEntity("childssn", "7e30d65f992f28eaf491943db331d11a2a01ee69980a6089c9241c5f150fa16e")
        );
    }

    @Override
    public String serialized() {
        return "{\"id\":{\"id\":123,\"type\":\"CSS_ChildSSN_US\"},\"memberId\":12345,\"tenantId\":2345,\"active\":false," +
                "\"ssn\":\"1231312312\",\"first_name\":\"John\",\"middle_name\":null,\"last_name\":\"Doe\"}";
    }

}