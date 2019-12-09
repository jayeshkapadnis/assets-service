package com.affinion.gce.model.asset.type;

import com.affinion.gce.annotation.Validator;
import com.affinion.gce.jpa.entity.AssetAttribute;
import com.affinion.gce.jpa.entity.AssetEntity;
import com.affinion.gce.model.asset.NameBased;
import com.affinion.gce.validator.MemberNameValidator;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Validator(MemberNameValidator.class)
public class Name extends NameBased {

    public Name(AssetEntity entity) {
        super(entity);
    }

    @Override
    public List<AssetAttribute> attributes() {
        return nameAttributes();
    }
}
