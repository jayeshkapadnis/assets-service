package com.affinion.gce.model.asset;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AssetId {
    private Long id;
    private AssetType type;

    public Category getCategory(){
        return this.type.category();
    }
}
