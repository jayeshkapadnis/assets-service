package com.affinion.gce.model;

import com.affinion.gce.jpa.entity.AssetAttributeEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public abstract class NameBased extends Asset{
    private String firstName;
    private String middleName;
    private String lastName;

    public List<AssetAttributeEntity> nameAttributes(){
        return Arrays.asList(
                new AssetAttributeEntity("first_name", getFirstName()),
                new AssetAttributeEntity("middle_name", getMiddleName()),
                new AssetAttributeEntity("last_name", getLastName())
        );
    };
}
