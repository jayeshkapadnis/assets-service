package com.affinion.gce.model.asset.type;

import com.affinion.gce.annotation.Validator;
import com.affinion.gce.jpa.entity.AssetAttributeEntity;
import com.affinion.gce.model.asset.Asset;
import com.affinion.gce.validator.PaymentCardValidator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Validator(PaymentCardValidator.class)
public class PaymentCard extends Asset {
    @JsonProperty("card_number")
    private String number;
    @JsonProperty("card_name")
    private String name;
    @JsonProperty("nick_name")
    private String nickName;
    @JsonProperty("card_issuer")
    private String issuer;
    @JsonProperty("card_scheme")
    private String scheme;
    @JsonProperty("card_type")
    private String type;
    @JsonProperty("exp_date_mmyyyy")
    private String expiry;

    @Override
    public String hash() {
        return null;
    }

    @Override
    public List<AssetAttributeEntity> attributes() {
        return Arrays.asList(
                new AssetAttributeEntity("credit_card", getNumber()),
                new AssetAttributeEntity("card_name", getName()),
                new AssetAttributeEntity("card_nickName", getNickName()),
                new AssetAttributeEntity("card_issuer", getIssuer()),
                new AssetAttributeEntity("card_scheme", getScheme()),
                new AssetAttributeEntity("card_type", getType()),
                new AssetAttributeEntity("card_expiry_date", getExpiry())
        );
    }
}
