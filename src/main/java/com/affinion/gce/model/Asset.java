package com.affinion.gce.model;

import com.affinion.gce.jackson.AssetTypeResolver;
import com.affinion.gce.jpa.entity.AssetAttributeEntity;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonTypeResolver;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonTypeInfo(use = JsonTypeInfo.Id.NONE)
@JsonTypeResolver(AssetTypeResolver.class)
public abstract class Asset implements Hashable{
    private AssetId id;
    private Long memberId;
    private Long tenantId;
    private Boolean active = false;

    public AssetType type(){
        return id.getType();
    }

    public List<AssetAttributeEntity> attributes(){
        throw new UnsupportedOperationException();
    };
}
