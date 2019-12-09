package com.affinion.gce.model.asset.type;

import com.affinion.gce.jpa.entity.AssetAttribute;
import com.affinion.gce.model.asset.AssetId;
import com.affinion.gce.model.asset.AssetType;
import com.affinion.gce.model.asset.BaseAssetTest;

import java.util.Collections;
import java.util.List;

public class KeyTagTest extends BaseAssetTest<KeyTag> {
    @Override
    public Class<KeyTag> assetClazz() {
        return KeyTag.class;
    }

    @Override
    public KeyTag newAsset() {
        return new KeyTag(new AssetId(null, AssetType.KEY_TAG), 1234L,
                2345L, true, "1234455");
    }

    @Override
    public List<AssetAttribute> expectedAttributes() {
        return Collections.singletonList(new AssetAttribute("serial_number", "1234455"));
    }

    @Override
    public List<AssetAttribute> expectedHashedAttributes() {
        return Collections.singletonList(
                new AssetAttribute("serial_number", "3d306eb7a8eb5d0364b7cc006006735306e603abf132595bdf5305ff54992278")
        );
    }

    @Override
    public String serialized() {
        return "{\"id\":{\"id\":null,\"type\":\"CSS_KeyTag\"},\"memberId\":1234," +
                "\"tenantId\":2345,\"active\":true,\"serial_number\":\"1234455\"}";
    }
}
