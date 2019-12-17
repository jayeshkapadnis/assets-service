package com.affinion.gce.model.asset.type;

import com.affinion.gce.annotation.Validator;
import com.affinion.gce.jpa.entity.AssetAttribute;
import com.affinion.gce.jpa.entity.AssetEntity;
import com.affinion.gce.model.asset.Asset;
import com.affinion.gce.model.asset.AssetId;
import com.affinion.gce.validator.NationalIdValidator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collections;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Validator(NationalIdValidator.class)
public class NationalIdentifier extends Asset {
    @JsonProperty("nationalId")
    private String identifier;

    public NationalIdentifier(AssetEntity entity) {
        super(entity);
        this.identifier = entity.attributeValue(type().id());
    }

    public NationalIdentifier(AssetId id, String name, Long memberId, Long tenantId, Boolean active, String identifier) {
        super(id, name, memberId, tenantId, active);
        this.identifier = identifier;
    }

    @Override
    public List<AssetAttribute> attributes() {
        return Collections.singletonList(new AssetAttribute(type().id(), getIdentifier()));
    }
}
