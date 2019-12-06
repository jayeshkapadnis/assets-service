package com.affinion.gce.model.asset.type;

import com.affinion.gce.annotation.Validator;
import com.affinion.gce.jpa.entity.AssetAttributeEntity;
import com.affinion.gce.jpa.entity.AssetEntity;
import com.affinion.gce.model.asset.Asset;
import com.affinion.gce.validator.PaymentCardValidator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Stream;

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

    public PaymentCard(AssetEntity entity) {
        super(entity);
        this.number = entity.attributeValue("credit_card");
        this.name = entity.attributeValue("card_name");
        this.nickName = entity.attributeValue("nick_name");
        this.issuer = entity.attributeValue("card_issuer");
        this.scheme = entity.attributeValue("card_scheme");
        this.type = entity.attributeValue("card_type");
        this.expiry = entity.attributeValue("exp_date_mmyyyy");
    }

    @Override
    public List<AssetAttributeEntity> attributes() {
        return fromStream(Stream.of(
                newAttribute("credit_card", getNumber()),
                newAttribute("card_name", getName()),
                newAttribute("nick_name", getNickName()),
                newAttribute("card_issuer", getIssuer()),
                newAttribute("card_scheme", getScheme()),
                newAttribute("card_type", getType()),
                newAttribute("exp_date_mmyyyy", getExpiry())
        ));
    }
}
