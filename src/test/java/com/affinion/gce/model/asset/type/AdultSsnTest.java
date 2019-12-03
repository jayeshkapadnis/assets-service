package com.affinion.gce.model.asset.type;

import com.affinion.gce.jpa.entity.AssetAttributeEntity;
import com.affinion.gce.model.asset.AssetId;
import com.affinion.gce.model.asset.AssetType;

import java.util.Collections;
import java.util.List;

public class AdultSsnTest extends BaseAssetTest<AdultSsn>{

    @Override
    public Class<AdultSsn> assetClazz() {
        return AdultSsn.class;
    }

    @Override
    public AdultSsn newAsset(){
        return AdultSsn.builder()
                .id(new AssetId(1234L, AssetType.US_ADULT_SSN))
                .memberId(1234L)
                .tenantId(213L)
                .active(true)
                .ssn("213123123").build();
    }

    @Override
    public List<AssetAttributeEntity> expectedAttributes() {
        return Collections.singletonList(new AssetAttributeEntity("ssn", "213123123"));
    }

    @Override
    public String serialized() {
        return "{\"id\":{\"id\":1234,\"type\":\"CSS_AdultSSN_US\"},\"memberId\":1234,\"tenantId\":213," +
                "\"active\":true,\"ssn\":\"213123123\"}";
    }

}