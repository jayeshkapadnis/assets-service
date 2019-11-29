package com.affinion.gce.model;

import com.affinion.gce.jpa.entity.AssetAttributeEntity;
import lombok.*;

import java.util.Collections;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class PhoneNumber extends Asset{
    private String number;

    public PhoneNumber(AssetId id, Long memberId, Long tenantId, Boolean active, String number){
        super(id, memberId, tenantId, active);
        this.number = number;
    }

    @Override
    public String hash() {
        return null;
    }

    @Override
    public List<AssetAttributeEntity> attributes() {
        return Collections.singletonList(new AssetAttributeEntity(type().id(), getNumber()));
    }
}
