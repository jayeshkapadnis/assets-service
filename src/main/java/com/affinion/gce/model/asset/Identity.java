package com.affinion.gce.model.asset;

import com.affinion.gce.jpa.entity.AssetAttributeEntity;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;

@NoArgsConstructor
//@Validator(IdentityValidator.class)
public class Identity extends Asset{
    protected String identifier;

    @Builder
    public Identity(AssetId id, Long memberId, Long tenantId, Boolean active, String identifier){
        super(id, memberId, tenantId, active);
        this.identifier = identifier;
    }

    @Override
    public String hash() {
        return null;
    }

    @Override
    public List<AssetAttributeEntity> attributes() {
        return Collections.singletonList(new AssetAttributeEntity(type().id(), identifier));
    }
}
