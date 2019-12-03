package com.affinion.gce.jpa.entity;

import com.affinion.gce.model.asset.Asset;
import com.affinion.gce.model.asset.AssetType;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.util.List;

@Table("assets")
public class AssetEntity implements Persistable<Long> {
    @Id
    private Long id;

    @Column
    private Long memberId;

    @Column
    private Long tenantId;

    @Column
    private AssetType type;

    @Column
    private String token;

    @MappedCollection(idColumn = "id", keyColumn = "attribute_name")
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

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public boolean isNew() {
        return this.id == null;
    }
}
