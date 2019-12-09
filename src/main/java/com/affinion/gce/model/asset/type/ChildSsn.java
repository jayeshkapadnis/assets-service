package com.affinion.gce.model.asset.type;

import com.affinion.gce.annotation.Validator;
import com.affinion.gce.jpa.entity.AssetAttribute;
import com.affinion.gce.jpa.entity.AssetEntity;
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

    public ChildSsn(AssetEntity entity) {
        super(entity);
        this.ssn = entity.attributeValue(type().id());
    }

    @Override
    public List<AssetAttribute> attributes() {
        List<AssetAttribute> attributes = nameAttributes();
        attributes.add(new AssetAttribute(type().id(), getSsn()));
        return attributes;
    }
}
