package com.affinion.gce.jpa.entity;

import com.affinion.gce.model.asset.Asset;
import com.affinion.gce.model.asset.AssetType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
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

    @Column(name = "is_active")
    private boolean active;

    @ElementCollection(targetClass = AssetAttribute.class)
    @CollectionTable(name = "asset_attributes",
            joinColumns = @JoinColumn(name = "asset_id"))
    @AttributeOverrides({
            @AttributeOverride(name = "key", column = @Column(name = "asset_attribute_name")),
            @AttributeOverride(name = "value", column = @Column(name = "asset_attribute_value"))
    })
    @Embedded
    private List<AssetAttribute> attributes;

    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date createdDate;

    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private Date modifiedDate;

    public AssetEntity(Asset asset) {
        this.id = asset.getId().getId();
        this.memberId = asset.getMemberId();
        this.tenantId = asset.getTenantId();
        this.type = asset.type();
        this.active = asset.getActive();
        this.attributes = asset.attributes();
    }

    public AssetEntity setToken(String token) {
        this.token = token;
        return this;
    }

    public AssetEntity setAttributes(List<AssetAttribute> attributes) {
        this.attributes = attributes;
        return this;
    }

    public Asset toData() throws Exception {
        Class<? extends Asset> clazz = this.getType().domain();
        return clazz.getConstructor(AssetEntity.class)
                .newInstance(this);
    }

    public String attributeValue(String key) {
        return attributes.stream()
                .filter(a -> a.getKey().equalsIgnoreCase(key))
                .findFirst()
                .map(AssetAttribute::getValue)
                .orElse(null);
    }
}
