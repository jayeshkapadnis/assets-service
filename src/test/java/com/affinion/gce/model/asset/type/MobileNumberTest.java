package com.affinion.gce.model.asset.type;

import com.affinion.gce.jpa.entity.AssetAttributeEntity;
import com.affinion.gce.model.asset.AssetId;
import com.affinion.gce.model.asset.AssetType;

import java.util.Collections;
import java.util.List;

public class MobileNumberTest extends BaseAssetTest<PhoneNumber>{

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
    public List<AssetAttributeEntity> expectedAttributes() {
        return Collections.singletonList(new AssetAttributeEntity("MobileNumber", "9856678905"));
    }

    @Override
    public String serialized() {
        return "{\"id\":{\"id\":null,\"type\":\"CSS_PhoneNumberMobile\"},\"memberId\":12345," +
                "\"tenantId\":123,\"active\":true,\"phone\":\"9856678905\"}";
    }
}
