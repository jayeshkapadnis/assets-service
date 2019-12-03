package com.affinion.gce.model.asset.type;

import com.affinion.gce.jpa.entity.AssetAttributeEntity;
import com.affinion.gce.model.asset.AssetId;
import com.affinion.gce.model.asset.AssetType;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

import static com.shazam.shazamcrest.matcher.Matchers.sameBeanAs;
import static org.junit.Assert.assertThat;

public class AdultSsnTest extends BaseAssetTest<AdultSsn>{

    @Override
    public Class<AdultSsn> assetClazz() {
        return AdultSsn.class;
    }

    @Test
    public void testAssetHashAttributes(){
        AdultSsn asset = newAsset();

        List<AssetAttributeEntity> actual = asset.hashAttributes();

        assertThat(actual, sameBeanAs(expectedHashedAttributes()));
    }

    @Override
    public AdultSsn newAsset(){
        return new AdultSsn(new AssetId(1234L, AssetType.US_ADULT_SSN), 1234L,
                213L, true, "213123123");
    }

    @Override
    public List<AssetAttributeEntity> expectedAttributes() {
        return Collections.singletonList(new AssetAttributeEntity("ssn", "213123123"));
    }

    public List<AssetAttributeEntity> expectedHashedAttributes() {
        return Collections.singletonList(
                new AssetAttributeEntity("ssn", "edac463b2560fc14a1fc94567bd6a533b6ce482174c42e04a7de0f77adfd536b")
        );
    }

    @Override
    public String serialized() {
        return "{\"id\":{\"id\":1234,\"type\":\"CSS_AdultSSN_US\"},\"memberId\":1234,\"tenantId\":213," +
                "\"active\":true,\"ssn\":\"213123123\"}";
    }

}