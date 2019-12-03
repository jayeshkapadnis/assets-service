package com.affinion.gce.model.asset.type;

import com.affinion.gce.annotation.Validator;
import com.affinion.gce.jpa.entity.AssetAttributeEntity;
import com.affinion.gce.model.asset.BankAccount;
import com.affinion.gce.validator.USBankAccountValidator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Validator(USBankAccountValidator.class)
public class USBankAccount extends BankAccount {
    @JsonProperty("routing_num")
    private String routingNumber;
    @JsonProperty("bank_code")
    private String bankCode;
    @JsonProperty("branch_code")
    private String branchCode;
    @JsonProperty("security_code")
    private String securityCode;
    @JsonProperty("sort_code")
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
