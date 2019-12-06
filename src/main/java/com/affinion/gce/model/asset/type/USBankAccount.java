package com.affinion.gce.model.asset.type;

import com.affinion.gce.annotation.Validator;
import com.affinion.gce.jpa.entity.AssetAttributeEntity;
import com.affinion.gce.jpa.entity.AssetEntity;
import com.affinion.gce.model.asset.BankAccount;
import com.affinion.gce.validator.USBankAccountValidator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    public USBankAccount(AssetEntity entity) {
        super(entity);
        this.routingNumber = entity.attributeValue("routing_number");
        this.iban = entity.attributeValue("iban");
        this.bankCode = entity.attributeValue("bank_code");
        this.branchCode = entity.attributeValue("branch_code");
        this.sortCode = entity.attributeValue("sort_code");
        this.securityCode = entity.attributeValue("security_code");
    }

    @Override
    public List<AssetAttributeEntity> hashAttributes() {
        return attributes().stream().map(a ->
                a.getKey().equals("iban") || a.getKey().equals("bankaccount") ?
                        new AssetAttributeEntity(a.getKey(), hashSequence(a.getValue())) : a
        ).collect(Collectors.toList());
    }

    @Override
    public List<AssetAttributeEntity> attributes() {
        List<AssetAttributeEntity> bankAccountAttributes = super.attributes();
        bankAccountAttributes.addAll(fromStream(Stream.of(
                newAttribute("iban", getIban()),
                newAttribute("routing_number", getRoutingNumber()),
                newAttribute("bank_code", getBankCode()),
                newAttribute("branch_code", getBranchCode()),
                newAttribute("sort_code", getSortCode()),
                newAttribute("security_code", getSecurityCode())
        )));
        return bankAccountAttributes;
    }
}
