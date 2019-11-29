package com.affinion.gce.model;

import com.affinion.gce.jpa.entity.AssetAttributeEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class PaymentCard extends Asset{
    private String number;
    private String name;
    private String nickName;
    private String issuer;
    private String scheme;
    private String type;
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
