package com.affinion.gce.model.asset.type;

import com.affinion.gce.annotation.Validator;
import com.affinion.gce.jpa.entity.AssetAttribute;
import com.affinion.gce.jpa.entity.AssetEntity;
import com.affinion.gce.model.asset.Asset;
import com.affinion.gce.model.asset.AssetId;
import com.affinion.gce.validator.PhoneNumberValidator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collections;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Validator(PhoneNumberValidator.class)
public class PhoneNumber extends Asset {
    @JsonProperty("phone")
    private String number;

    public PhoneNumber(AssetEntity entity) {
        super(entity);
        this.number = entity.attributeValue(type().id());
    }

    public PhoneNumber(AssetId id, String name, Long memberId, Long tenantId, Boolean active, String number) {
        super(id, name, memberId, tenantId, active);
        this.number = number;
    }

    @Override
    public List<AssetAttribute> attributes() {
        return Collections.singletonList(new AssetAttribute(type().id(), getNumber()));
    }
}
