package com.affinion.gce.model.asset.type;

import com.affinion.gce.annotation.Validator;
import com.affinion.gce.jpa.entity.AssetAttributeEntity;
import com.affinion.gce.model.asset.Asset;
import com.affinion.gce.model.asset.AssetId;
import com.affinion.gce.validator.DateOfBirthValidator;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collections;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Validator(DateOfBirthValidator.class)
public class DateOfBirth extends Asset {

    private String dob;

    public DateOfBirth(AssetId id, Long memberId, Long tenantId, Boolean active, String dob){
        super(id, memberId, tenantId, active);
        this.dob = dob;
    }

    @Override
    public List<AssetAttributeEntity> attributes() {
        return Collections.singletonList(new AssetAttributeEntity(type().id(), getDob()));
    }
}
