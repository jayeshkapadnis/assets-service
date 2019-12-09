package com.affinion.gce.model.asset.type;

import com.affinion.gce.jpa.entity.AssetAttribute;
import com.affinion.gce.model.asset.AssetId;
import com.affinion.gce.model.asset.AssetType;
import com.affinion.gce.model.asset.BaseAssetTest;

import java.util.Collections;
import java.util.List;

public class MobileNumberTest extends BaseAssetTest<PhoneNumber> {

    @Override
    public Class<PhoneNumber> assetClazz() {
        return PhoneNumber.class;
    }

    @Override
    public PhoneNumber newAsset() {
        PhoneNumber asset = new PhoneNumber();
        asset.setId(new AssetId(null, AssetType.MOBILE_NUMBER));
        asset.setTenantId(123L);
        asset.setMemberId(12345L);
        asset.setActive(true);
        asset.setNumber("9856678905");
        return asset;
    }

    @Override
    public List<AssetAttribute> expectedAttributes() {
        return Collections.singletonList(new AssetAttribute("MobileNumber", "9856678905"));
    }

    @Override
    public List<AssetAttribute> expectedHashedAttributes() {
        return Collections.singletonList(
                new AssetAttribute("MobileNumber", "94805179531900df2501a1e38a9f09c6304d5d9d4a3655c220ce7a613afb2268")
        );
    }

    @Override
    public String serialized() {
        return "{\"id\":{\"id\":null,\"type\":\"CSS_PhoneNumberMobile\"},\"memberId\":12345," +
                "\"tenantId\":123,\"active\":true,\"phone\":\"9856678905\"}";
    }
}
