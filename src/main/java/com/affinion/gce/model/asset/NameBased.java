package com.affinion.gce.model.asset;

import com.affinion.gce.jpa.entity.AssetAttributeEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
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
        ArrayList<Optional<AssetAttributeEntity>> attributes = new ArrayList<>();
        attributes.add(newAttribute("first_name", getFirstName()));
        attributes.add(newAttribute("middle_name", getMiddleName()));
        attributes.add(newAttribute("last_name", getLastName()));
        return attributes.stream().filter(Optional::isPresent)
                .map(Optional::get).collect(Collectors.toList());
    };
}
