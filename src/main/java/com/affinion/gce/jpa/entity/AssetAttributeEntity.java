package com.affinion.gce.jpa.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.io.Serializable;

@NoArgsConstructor
@Getter
@Setter
@Embeddable
public class AssetAttributeEntity implements Serializable {

    public AssetAttributeEntity(String key, String value) {
        this.key = key;
        this.value = value;
    }

    private String key;
    private String value;
}
