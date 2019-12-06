package com.affinion.gce.model.asset.type;

import com.affinion.gce.annotation.Validator;
import com.affinion.gce.jpa.entity.AssetAttributeEntity;
import com.affinion.gce.jpa.entity.AssetEntity;
import com.affinion.gce.model.asset.Asset;
import com.affinion.gce.model.asset.AssetId;
import com.affinion.gce.validator.EmailValidator;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collections;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Validator(EmailValidator.class)
public class Email extends Asset {

    private String email;

    public Email(AssetEntity entity) {
        super(entity);
        this.email = entity.attributeValue(type().id());
    }

    public Email(AssetId id, Long memberId, Long tenantId, Boolean active, String email) {
        super(id, memberId, tenantId, active);
        this.email = email;
    }

    @Override
    public List<AssetAttributeEntity> attributes() {
        return Collections.singletonList(new AssetAttributeEntity(type().id(), getEmail()));
    }
}
