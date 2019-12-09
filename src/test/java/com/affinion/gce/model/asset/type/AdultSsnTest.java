package com.affinion.gce.model.asset.type;

import com.affinion.gce.jpa.entity.AssetAttribute;
import com.affinion.gce.model.asset.AssetId;
import com.affinion.gce.model.asset.AssetType;
import com.affinion.gce.model.asset.BaseAssetTest;

import java.util.Collections;
import java.util.List;

public class AdultSsnTest extends BaseAssetTest<AdultSsn> {

    @Override
    public Class<AdultSsn> assetClazz() {
        return AdultSsn.class;
    }

    @Override
    public AdultSsn newAsset(){
        return new AdultSsn(new AssetId(1234L, AssetType.US_ADULT_SSN), 1234L,
                213L, true, "213123123");
    }

    @Override
    public List<AssetAttribute> expectedAttributes() {
        return Collections.singletonList(new AssetAttribute("ssn", "213123123"));
    }

    @Override
    public List<AssetAttribute> expectedHashedAttributes() {
        return Collections.singletonList(
                new AssetAttribute("ssn", "edac463b2560fc14a1fc94567bd6a533b6ce482174c42e04a7de0f77adfd536b")
        );
    }

    @Override
    public String serialized() {
        return "{\"id\":{\"id\":1234,\"type\":\"CSS_AdultSSN_US\"},\"memberId\":1234,\"tenantId\":213," +
                "\"active\":true,\"ssn\":\"213123123\"}";
    }

}