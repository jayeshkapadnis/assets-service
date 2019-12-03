package com.affinion.gce.model.asset.type;

import com.affinion.gce.jpa.entity.AssetAttributeEntity;
import com.affinion.gce.model.asset.AssetId;
import com.affinion.gce.model.asset.AssetType;

import java.util.Arrays;
import java.util.List;

public class PaymentCardTest extends BaseAssetTest<PaymentCard>{

    @Override
    public Class<PaymentCard> assetClazz() {
        return PaymentCard.class;
    }

    @Override
    public PaymentCard newAsset() {
        PaymentCard card = new PaymentCard();
        card.setId(new AssetId(null, AssetType.PAYMENT_CARD));
        card.setTenantId(123L);
        card.setMemberId(12345L);
        card.setActive(true);
        card.setExpiry("022020");
        card.setName("Member Name");
        card.setNickName("Personal Card");
        card.setNumber("213434567890");
        card.setType("Platinum");
        card.setIssuer("ICICI BANK");
        return card;
    }

    @Override
    public List<AssetAttributeEntity> expectedAttributes() {
        return Arrays.asList(
                new AssetAttributeEntity("credit_card", "213434567890"),
                new AssetAttributeEntity("card_name", "Member Name"),
                new AssetAttributeEntity("nick_name", "Personal Card"),
                new AssetAttributeEntity("card_issuer", "ICICI BANK"),
                new AssetAttributeEntity("card_type", "Platinum"),
                new AssetAttributeEntity("exp_date_mmyyyy", "022020")
        );
    }

    @Override
    public String serialized() {
        return "{\"id\":{\"id\":null,\"type\":\"CSS_PaymentCard\"},\"memberId\":12345,\"tenantId\":123," +
                "\"active\":true,\"card_number\":\"213434567890\",\"card_name\":\"Member Name\"," +
                "\"nick_name\":\"Personal Card\",\"card_issuer\":\"ICICI BANK\",\"card_scheme\":null," +
                "\"card_type\":\"Platinum\",\"exp_date_mmyyyy\":\"022020\"}";
    }
}