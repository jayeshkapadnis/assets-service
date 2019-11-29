package com.affinion.gce.model;

import com.affinion.gce.jpa.entity.AssetAttributeEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class Name extends NameBased{
    @Override
    public String hash() {
        return null;
    }

    @Override
    public List<AssetAttributeEntity> attributes() {
        return nameAttributes();
    }
}
