package com.affinion.gce.model.asset.type;

import com.affinion.gce.jpa.entity.AssetAttribute;
import com.affinion.gce.model.asset.AssetId;
import com.affinion.gce.model.asset.AssetType;
import com.affinion.gce.model.asset.BaseAssetTest;

import java.util.Collections;
import java.util.List;

public class DateOfBirthTest extends BaseAssetTest<DateOfBirth> {

    @Override
    public Class<DateOfBirth> assetClazz() {
        return DateOfBirth.class;
    }

    @Override
    public DateOfBirth newAsset(){
        return new DateOfBirth(new AssetId(null, AssetType.DOB), "My DOB",
                1234L, 2345L, true, "01022017");
    }

    @Override
    public List<AssetAttribute> expectedAttributes() {
        return Collections.singletonList(new AssetAttribute("dob", "01022017"));
    }

    @Override
    public List<AssetAttribute> expectedHashedAttributes() {
        return Collections.singletonList(
                new AssetAttribute("dob", "0ba3dbf2555ff80fd4f315d1d75523833a7cb318620f09f5a203568d7b4a748c")
        );
    }

    @Override
    public String serialized() {
        return "{\"id\":{\"id\":null,\"type\":\"CSS_DOB\"},\"name\":\"My DOB\"," +
                "\"tenantId\":2345,\"active\":true,\"dob\":\"01022017\",\"member_id\":1234}";
    }
}