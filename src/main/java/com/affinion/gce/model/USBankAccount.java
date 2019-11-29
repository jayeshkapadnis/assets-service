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
public class USBankAccount extends BankAccount{
    private String routingNumber;
    private String bankCode;
    private String branchCode;
    private String securityCode;
    private String sortCode;
    private String iban;

    @Override
    public String hash() {
        return null;
    }

    @Override
    public List<AssetAttributeEntity> attributes() {
        List<AssetAttributeEntity> bankAccountAttributes = super.attributes();
        bankAccountAttributes.addAll(Arrays.asList(
                new AssetAttributeEntity("routing_number", getRoutingNumber()),
                new AssetAttributeEntity("bank_code", getBankCode()),
                new AssetAttributeEntity("branch_code", getBranchCode()),
                new AssetAttributeEntity("sort_code", getSortCode()),
                new AssetAttributeEntity("security_code", getSecurityCode()),
                new AssetAttributeEntity("iban", getIban())
        ));
        return bankAccountAttributes;
    }
}
