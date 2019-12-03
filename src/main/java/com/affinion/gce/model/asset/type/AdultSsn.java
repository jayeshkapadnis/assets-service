package com.affinion.gce.model.asset.type;

import com.affinion.gce.annotation.Validator;
import com.affinion.gce.jpa.entity.AssetAttributeEntity;
import com.affinion.gce.model.asset.Asset;
import com.affinion.gce.model.asset.AssetId;
import com.affinion.gce.validator.AdultSsnValidator;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collections;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Validator(AdultSsnValidator.class)
public class AdultSsn extends Asset {
    private String ssn;

    @Builder
    public AdultSsn(AssetId id, Long memberId, Long tenantId, Boolean active, String ssn){
        super(id, memberId, tenantId, active);
        this.ssn = ssn;
    }

    @Override
    public String hash() {
        return null;
    }

    @Override
    public List<AssetAttributeEntity> attributes() {
        return Collections.singletonList(new AssetAttributeEntity(type().id(), getSsn()));
    }
}
