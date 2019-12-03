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
        return KeyTag.builder()
                .id(new AssetId(null, AssetType.KEY_TAG))
                .active(true)
                .memberId(1234L)
                .tenantId(2345L)
                .serialNumber("1234455").build();
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
