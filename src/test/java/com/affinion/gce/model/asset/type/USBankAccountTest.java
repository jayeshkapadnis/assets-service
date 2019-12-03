package com.affinion.gce.model.asset.type;

import com.affinion.gce.jpa.entity.AssetAttributeEntity;
import com.affinion.gce.model.asset.AssetId;
import com.affinion.gce.model.asset.AssetType;

import java.util.Arrays;
import java.util.List;

public class USBankAccountTest extends BaseAssetTest<USBankAccount>{

    @Override
    public Class<USBankAccount> assetClazz() {
        return USBankAccount.class;
    }

    @Override
    public USBankAccount newAsset() {
        USBankAccount asset = new USBankAccount();
        asset.setId(new AssetId(null, AssetType.US_BANK_ACCOUNT));
        asset.setTenantId(123L);
        asset.setMemberId(12345L);
        asset.setActive(true);
        asset.setNumber("096123456769");
        asset.setNickName("Personal Account");
        asset.setType("Savings");
        //asset.setIban("BE71096123456769");
        asset.setBankCode("ICIC");
        asset.setBranchCode("2900");
        asset.setRoutingNumber("CHASUS33XXX");
        asset.setSortCode("ICICINBBCTS");
        return asset;
    }

    @Override
    public List<AssetAttributeEntity> expectedAttributes() {
        return Arrays.asList(
                new AssetAttributeEntity("bankaccount", "096123456769"),
                new AssetAttributeEntity("nick_name", "Personal Account"),
                new AssetAttributeEntity("account_type", "Savings"),
                new AssetAttributeEntity("routing_number", "CHASUS33XXX"),
                new AssetAttributeEntity("bank_code", "ICIC"),
                new AssetAttributeEntity("branch_code", "2900"),
                new AssetAttributeEntity("sort_code", "ICICINBBCTS")
        );
    }

    @Override
    public String serialized() {
        return "{\"id\":{\"id\":null,\"type\":\"CSS_BankAccount_US\"},\"memberId\":12345,\"tenantId\":123," +
                "\"active\":true,\"type\":\"Savings\",\"iban\":null,\"account_num\":\"096123456769\"," +
                "\"nick_name\":\"Personal Account\",\"routing_num\":\"CHASUS33XXX\",\"bank_code\":\"ICIC\"," +
                "\"branch_code\":\"2900\",\"security_code\":null,\"sort_code\":\"ICICINBBCTS\"}";
    }
}