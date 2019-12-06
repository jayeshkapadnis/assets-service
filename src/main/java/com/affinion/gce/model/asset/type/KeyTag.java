package com.affinion.gce.model.asset.type;

import com.affinion.gce.annotation.Validator;
import com.affinion.gce.jpa.entity.AssetAttributeEntity;
import com.affinion.gce.jpa.entity.AssetEntity;
import com.affinion.gce.model.asset.Asset;
import com.affinion.gce.model.asset.AssetId;
import com.affinion.gce.validator.KeyTagValidator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collections;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Validator(KeyTagValidator.class)
public class KeyTag extends Asset {
    @JsonProperty("serial_number")
    private String serialNumber;

    public KeyTag(AssetEntity entity) {
        super(entity);
        this.serialNumber = entity.attributeValue(type().id());
    }

    public KeyTag(AssetId id, Long memberId, Long tenantId, Boolean active, String serialNumber) {
        super(id, memberId, tenantId, active);
        this.serialNumber = serialNumber;
    }

    @Override
    public List<AssetAttributeEntity> attributes() {
        return Collections.singletonList(new AssetAttributeEntity(type().id(), getSerialNumber()));
    }
}
