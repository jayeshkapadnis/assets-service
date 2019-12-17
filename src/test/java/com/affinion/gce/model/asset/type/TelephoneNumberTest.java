package com.affinion.gce.model.asset.type;

import com.affinion.gce.jpa.entity.AssetAttribute;
import com.affinion.gce.model.asset.AssetId;
import com.affinion.gce.model.asset.AssetType;
import com.affinion.gce.model.asset.BaseAssetTest;

import java.util.Collections;
import java.util.List;

public class TelephoneNumberTest extends BaseAssetTest<PhoneNumber> {

    @Override
    public Class<PhoneNumber> assetClazz() {
        return PhoneNumber.class;
    }

    @Override
    public PhoneNumber newAsset() {
        return new PhoneNumber(new AssetId(null, AssetType.PHONE_NUMBER), "Home Telephone", 123L,
                12345L, true, "9856678905");
    }

    @Override
    public List<AssetAttribute> expectedAttributes() {
        return Collections.singletonList(new AssetAttribute("telephone", "9856678905"));
    }

    @Override
    public List<AssetAttribute> expectedHashedAttributes() {
        return Collections.singletonList(
                new AssetAttribute("telephone", "94805179531900df2501a1e38a9f09c6304d5d9d4a3655c220ce7a613afb2268")
        );
    }

    @Override
    public String serialized() {
        return "{\"id\":{\"id\":null,\"type\":\"CSS_PhoneNumber\"},\"name\":\"Home Telephone\"," +
                "\"tenantId\":12345,\"active\":true,\"member_id\":123,\"phone\":\"9856678905\"}";
    }
}