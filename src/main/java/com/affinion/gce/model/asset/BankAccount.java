package com.affinion.gce.model.asset;

import com.affinion.gce.jpa.entity.AssetAttribute;
import com.affinion.gce.jpa.entity.AssetEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Stream;

@NoArgsConstructor
@Getter
@Setter
public abstract class BankAccount extends Asset {
    @JsonProperty("account_num")
    private String number;
    @JsonProperty("nick_name")
    private String nickName;
    private String type;

    public BankAccount(AssetEntity entity) {
        super(entity);
        this.number = entity.attributeValue("bankaccount");
        this.nickName = entity.attributeValue("nick_name");
        this.type = entity.attributeValue("account_type");
    }

    @Override
    public List<AssetAttribute> attributes() {
        return fromStream(Stream.of(
                newAttribute("bankaccount", getNumber()),
                newAttribute("nick_name", getNickName()),
                newAttribute("account_type", getType())
        ));
    }
}
