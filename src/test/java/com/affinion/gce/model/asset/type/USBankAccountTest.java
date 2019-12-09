package com.affinion.gce.model.asset.type;

import com.affinion.gce.jpa.entity.AssetAttribute;
import com.affinion.gce.model.asset.AssetId;
import com.affinion.gce.model.asset.AssetType;
import com.affinion.gce.model.asset.BaseAssetTest;

import java.util.Arrays;
import java.util.List;

public class USBankAccountTest extends BaseAssetTest<USBankAccount> {

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
    public List<AssetAttribute> expectedAttributes() {
        return Arrays.asList(
                new AssetAttribute("bankaccount", "096123456769"),
                new AssetAttribute("nick_name", "Personal Account"),
                new AssetAttribute("account_type", "Savings"),
                new AssetAttribute("routing_number", "CHASUS33XXX"),
                new AssetAttribute("bank_code", "ICIC"),
                new AssetAttribute("branch_code", "2900"),
                new AssetAttribute("sort_code", "ICICINBBCTS")
        );
    }

    @Override
    public List<AssetAttribute> expectedHashedAttributes() {
        return Arrays.asList(
                new AssetAttribute("bankaccount", "2052bdf17ab41859cdcbae9b02d114cef509b5815525b621e93c4cd373a3e074"),
                new AssetAttribute("nick_name", "Personal Account"),
                new AssetAttribute("account_type", "Savings"),
                new AssetAttribute("routing_number", "CHASUS33XXX"),
                new AssetAttribute("bank_code", "ICIC"),
                new AssetAttribute("branch_code", "2900"),
                new AssetAttribute("sort_code", "ICICINBBCTS")
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