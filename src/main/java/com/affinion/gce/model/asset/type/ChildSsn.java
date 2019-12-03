package com.affinion.gce.model.asset.type;

import com.affinion.gce.annotation.Validator;
import com.affinion.gce.jpa.entity.AssetAttributeEntity;
import com.affinion.gce.model.asset.NameBased;
import com.affinion.gce.validator.ChildSsnValidator;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Validator(ChildSsnValidator.class)
public class ChildSsn extends NameBased {
    private String ssn;

    @Override
    public List<AssetAttributeEntity> attributes() {
        List<AssetAttributeEntity> attributes = nameAttributes();
        attributes.add(new AssetAttributeEntity(type().id(), getSsn()));
        return attributes;
    }

    @Override
    public String hash() {
        return null;
    }
}
