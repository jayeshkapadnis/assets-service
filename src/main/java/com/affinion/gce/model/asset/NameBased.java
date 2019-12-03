package com.affinion.gce.model.asset;

import com.affinion.gce.jpa.entity.AssetAttributeEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Stream;

@NoArgsConstructor
@Getter
@Setter
public abstract class NameBased extends Asset{
    @JsonProperty("first_name")
    private String firstName;
    @JsonProperty("middle_name")
    private String middleName;
    @JsonProperty("last_name")
    private String lastName;

    protected List<AssetAttributeEntity> nameAttributes(){
        return fromStream(Stream.of(
                newAttribute("first_name", getFirstName()),
                newAttribute("middle_name", getMiddleName()),
                newAttribute("last_name", getLastName())
        ));
    }
}
