package com.affinion.gce.model.asset;

import com.affinion.gce.jackson.AssetTypeResolver;
import com.affinion.gce.jpa.entity.AssetAttributeEntity;
import com.affinion.gce.model.Hashable;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonTypeResolver;
import lombok.*;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonTypeInfo(use = JsonTypeInfo.Id.NONE)
@JsonTypeResolver(AssetTypeResolver.class)
public abstract class Asset implements Hashable {
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

    protected Optional<AssetAttributeEntity> newAttribute(String key, String value){
        return StringUtils.isEmpty(value) ? Optional.empty() :
                Optional.of(new AssetAttributeEntity(key, value));
    }
}
