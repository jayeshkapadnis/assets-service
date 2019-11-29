package com.affinion.gce.jpa.entity;

import lombok.Value;

import javax.persistence.Embeddable;

@Value
@Embeddable
public class AssetAttributeEntity {
    private String key;
    private String value;
}
