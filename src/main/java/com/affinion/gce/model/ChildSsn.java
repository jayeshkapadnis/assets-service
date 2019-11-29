package com.affinion.gce.model;

import com.affinion.gce.jpa.entity.AssetAttributeEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class ChildSsn extends NameBased{
    private String identifier;

    @Override
    public List<AssetAttributeEntity> attributes() {
        List<AssetAttributeEntity> attributes = nameAttributes();
        attributes.add(new AssetAttributeEntity(type().id(), getIdentifier()));
        return attributes;
    }

    @Override
    public String hash() {
        return null;
    }
}
