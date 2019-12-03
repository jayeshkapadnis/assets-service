package com.affinion.gce.model.asset;

import com.affinion.gce.jpa.entity.AssetAttributeEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;

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
        return Arrays.asList(
                new AssetAttributeEntity("account_number", getNumber()),
                new AssetAttributeEntity("nick_name", getNickName()),
                new AssetAttributeEntity("account_type", getType())
        );
    }
}
