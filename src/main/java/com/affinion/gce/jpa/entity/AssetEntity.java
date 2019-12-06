package com.affinion.gce.jpa.entity;

import com.affinion.gce.model.asset.Asset;
import com.affinion.gce.model.asset.AssetType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "assets")
public class AssetEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long memberId;

    private Long tenantId;

    @Enumerated(EnumType.STRING)
    private AssetType type;

    private String token;

    private boolean active;

    @ElementCollection(targetClass = AssetAttributeEntity.class)
    @CollectionTable(name = "asset_attributes",
            joinColumns = @JoinColumn(name = "asset_id"))
    @AttributeOverrides({
            @AttributeOverride(name="key", column=@Column(name="key")),
            @AttributeOverride(name="value", column=@Column(name="value"))
    })
    @Embedded
    private List<AssetAttributeEntity> attributes;

    public AssetEntity(Asset asset){
        this.id = asset.getId().getId();
        this.memberId = asset.getMemberId();
        this.tenantId = asset.getTenantId();
        this.type = asset.type();
        this.active = asset.getActive();
        this.attributes = asset.attributes();
    }

    public AssetEntity setToken(String token){
        this.token = token;
        return this;
    }

    public AssetEntity setAttributes(List<AssetAttributeEntity> attributes){
        this.attributes = attributes;
        return this;
    }

    public Asset toData() throws Exception {
        Class<? extends Asset> clazz = this.getType().domain();
        return clazz.getConstructor(AssetEntity.class)
                .newInstance(this);
    }

    public String attributeValue(String key){
        return attributes.stream()
                .filter(a -> a.getKey().equalsIgnoreCase(key))
                .findFirst()
                .map(AssetAttributeEntity::getValue)
                .orElse(null);
    }
}
