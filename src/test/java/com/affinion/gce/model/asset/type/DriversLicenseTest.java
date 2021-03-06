package com.affinion.gce.model.asset.type;

import com.affinion.gce.jpa.entity.AssetAttribute;
import com.affinion.gce.model.asset.AssetId;
import com.affinion.gce.model.asset.AssetType;
import com.affinion.gce.model.asset.BaseAssetTest;

import java.util.Collections;
import java.util.List;

public class DriversLicenseTest extends BaseAssetTest<DriversLicense> {

    @Override
    public Class<DriversLicense> assetClazz() {
        return DriversLicense.class;
    }

    @Override
    public DriversLicense newAsset() {
        return new DriversLicense(new AssetId(123L, AssetType.DRIVERS_LICENSE), "My License No.", 12345L,
                123L, true, "567890");
    }

    @Override
    public List<AssetAttribute> expectedAttributes() {
        return Collections.singletonList(new AssetAttribute("driver_license", "567890"));
    }

    @Override
    public List<AssetAttribute> expectedHashedAttributes() {
        return Collections.singletonList(
                new AssetAttribute("driver_license", "b39667cf64cd5bc6cd7adbfc711cd8446036f9144c1cceb604897b0e824a027d")
        );
    }

    @Override
    public String serialized() {
        return "{\"id\":{\"id\":123,\"type\":\"CSS_DriversLicense\"},\"name\":\"My License No.\"," +
                "\"tenantId\":123,\"active\":true,\"member_id\":12345,\"license_number\":\"567890\"}";
    }
}