package com.affinion.gce.model.asset.type;

import com.affinion.gce.jpa.entity.AssetAttributeEntity;
import com.affinion.gce.model.asset.AssetId;
import com.affinion.gce.model.asset.AssetType;

import java.util.Collections;
import java.util.List;

public class KeyTagTest extends BaseAssetTest<KeyTag>{
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
    public List<AssetAttributeEntity> expectedAttributes() {
        return Collections.singletonList(new AssetAttributeEntity("serial_number", "1234455"));
    }

    @Override
    public String serialized() {
        return "{\"id\":{\"id\":null,\"type\":\"CSS_KeyTag\"},\"memberId\":1234," +
                "\"tenantId\":2345,\"active\":true,\"serial_number\":\"1234455\"}";
    }
}
