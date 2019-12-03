package com.affinion.gce.model.asset.type;

import com.affinion.gce.jpa.entity.AssetAttributeEntity;
import com.affinion.gce.model.asset.AssetId;
import com.affinion.gce.model.asset.AssetType;

import java.util.Collections;
import java.util.List;

public class DateOfBirthTest extends BaseAssetTest<DateOfBirth>{

    @Override
    public Class<DateOfBirth> assetClazz() {
        return DateOfBirth.class;
    }

    @Override
    public DateOfBirth newAsset(){
        return new DateOfBirth(new AssetId(null, AssetType.DOB), 1234L, 2345L, true, "01022017");
    }

    @Override
    public List<AssetAttributeEntity> expectedAttributes() {
        return Collections.singletonList(new AssetAttributeEntity("dob", "01022017"));
    }

    @Override
    public String serialized() {
        return "{\"id\":{\"id\":null,\"type\":\"CSS_DOB\"},\"memberId\":1234,\"tenantId\":2345," +
                "\"active\":true,\"dob\":\"01022017\"}";
    }
}