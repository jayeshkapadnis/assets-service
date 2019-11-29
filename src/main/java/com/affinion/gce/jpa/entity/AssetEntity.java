package com.affinion.gce.jpa.entity;

import com.affinion.gce.model.Asset;
import com.affinion.gce.model.AssetType;
import org.springframework.data.annotation.Id;

import javax.persistence.*;
import java.util.List;

@Entity
public abstract class AssetEntity {
    @Id
    private Long id;

    @Column
    private Long memberId;

    @Column
    private Long tenantId;

    @Column
    @Enumerated(EnumType.STRING)
    private AssetType type;

    @Column
    private String token;

    @ElementCollection(targetClass = AssetAttributeEntity.class)
    @CollectionTable(name = "asset_attributes",
            joinColumns = @JoinColumn(name = "asset_id"))
    @AttributeOverrides({
            @AttributeOverride(name="key", column=@Column(name = "asset_attribute_name")),
            @AttributeOverride(name="value", column=@Column(name = "asset_attribute_value"))
    })
    @Embedded
    private List<AssetAttributeEntity> attributes;

    public AssetEntity(Asset asset){
        this.id = asset.getId().getId();
        this.memberId = asset.getMemberId();
        this.tenantId = asset.getTenantId();
        this.type = asset.type();
        this.attributes = asset.attributes();
    }

    public void setToken(String token){
        this.token = token;
    }
}
