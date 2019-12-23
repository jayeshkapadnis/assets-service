package com.affinion.gce.model.asset;

import com.affinion.gce.model.asset.type.*;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum AssetType {
    US_CHILD_SSN("CSS_ChildSSN_US", "childssn", ChildSsn.class, Category.DOCUMENT),
    //Personal
    EMAIL("CSS_EmailAddress", "email", Email.class, Category.PERSONAL),
    DOB("CSS_DOB", "dob", DateOfBirth.class, Category.PERSONAL),
    POSTAL_ADDRESS("CSS_PostalAddress", "address_line1", PostalAddress.class, Category.PERSONAL),
    NAME("CSS_MemberName", "first_name", Name.class, Category.PERSONAL),
    // Identity
    PASSPORT("CSS_Passport", "passport_number", Passport.class, Category.DOCUMENT),
    US_ADULT_SSN("CSS_AdultSSN_US", "ssn", AdultSsn.class, Category.DOCUMENT),
    DRIVERS_LICENSE("CSS_DriversLicense", "driver_license", DriversLicense.class, Category.DOCUMENT),
    NATIONAL_ID("CSS_NationalId", "nid", NationalIdentifier.class, Category.DOCUMENT),
    OTHER_ID("CSS_OtherID", "otherID", OtherIdentifier.class, Category.DOCUMENT),
    ID_CARD("CSS_IdentityCard", "identity_card", IdentityCard.class, Category.DOCUMENT),
    KEY_TAG("CSS_KeyTag", "serial_number", KeyTag.class, Category.DOCUMENT),
    //Phone Numbers
    PHONE_NUMBER("CSS_PhoneNumber", "telephone", PhoneNumber.class, Category.PERSONAL),
    MOBILE_NUMBER("CSS_PhoneNumberMobile", "MobileNumber", PhoneNumber.class, Category.PERSONAL),
    //Financial
    PAYMENT_CARD("CSS_PaymentCard", "credit_card", PaymentCard.class, Category.FINANCIAL),
    PROTECTED_CARD("Asset_Helix_ProtectedCard", "protected_card", Asset.class, Category.FINANCIAL),
    US_BANK_ACCOUNT("CSS_BankAccount_US", "bankaccount", USBankAccount.class, Category.FINANCIAL),
    BANK_ACCOUNT("Asset_Helix_BankAccount", "account_number", BankAccount.class, Category.FINANCIAL);

    private final String typeKey;
    private final String id;
    private final Class<? extends Asset> domain;
    private final Category category;

    AssetType(String typeKey, String id, Class<? extends Asset> domain, Category category) {
        this.typeKey = typeKey;
        this.id = id;
        this.domain = domain;
        this.category = category;
    }

    @JsonValue
    public String getTypeKey() {
        return typeKey;
    }

    public String id() {
        return this.id;
    }

    public Class<? extends Asset> domain() {
        return this.domain;
    }

    public Category category(){
        return this.category;
    }

    @JsonCreator
    public static AssetType fromValue(String typeKey) {
        for (AssetType type : AssetType.values()) {
            if (type.typeKey.equalsIgnoreCase(typeKey)) {
                return type;
            }
        }
        return null;
    }
}
