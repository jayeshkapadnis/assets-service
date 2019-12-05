package com.affinion.gce.jpa.entity;

import com.affinion.gce.model.asset.Asset;
import com.affinion.gce.model.asset.AssetType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

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
        this.attributes = asset.attributes();
    }

    public void setToken(String token){
        this.token = token;
    }
}
