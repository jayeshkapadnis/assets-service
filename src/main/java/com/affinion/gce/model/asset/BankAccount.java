package com.affinion.gce.model.asset;

import com.affinion.gce.jpa.entity.AssetAttributeEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
@Setter
public abstract class BankAccount extends Asset{
    @JsonProperty("account_num")
    private String number;
    @JsonProperty("nick_name")
    private String nickName;
    private String type;

    @Override
    public List<AssetAttributeEntity> attributes() {
        return fromStream(Stream.of(
                newAttribute("bankaccount", getNumber()),
                newAttribute("nick_name", getNickName()),
                newAttribute("account_type", getType())
        ));
    }
}
