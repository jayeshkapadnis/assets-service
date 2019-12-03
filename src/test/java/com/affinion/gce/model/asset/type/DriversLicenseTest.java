package com.affinion.gce.model.asset.type;

import com.affinion.gce.jpa.entity.AssetAttributeEntity;
import com.affinion.gce.model.asset.AssetId;
import com.affinion.gce.model.asset.AssetType;

import java.util.Collections;
import java.util.List;

public class DriversLicenseTest extends BaseAssetTest<DriversLicense>{

    @Override
    public Class<DriversLicense> assetClazz() {
        return DriversLicense.class;
    }

    @Override
    public DriversLicense newAsset() {
        return new DriversLicense(new AssetId(123L, AssetType.DRIVERS_LICENSE), 12345L,
                123L, true, "567890");
    }

    @Override
    public List<AssetAttributeEntity> expectedAttributes() {
        return Collections.singletonList(new AssetAttributeEntity("driver_license", "567890"));
    }

    @Override
    public String serialized() {
        return "{\"id\":{\"id\":123,\"type\":\"CSS_DriversLicense\"},\"memberId\":12345," +
                "\"tenantId\":123,\"active\":true,\"license_number\":\"567890\"}";
    }
}