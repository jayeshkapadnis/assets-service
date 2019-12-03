package com.affinion.gce.model.asset;

import com.affinion.gce.model.asset.type.*;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum AssetType {
    US_CHILD_SSN("CSS_ChildSSN_US", "childssn", ChildSsn.class),
    //Personal
    EMAIL("CSS_EmailAddress", "email", Email.class),
    DOB("CSS_DOB", "date_of_birth", DateOfBirth.class),
    POSTAL_ADDRESS("CSS_PostalAddress", "address_line_1", PostalAddress.class),
    NAME("CSS_MemberName", "first_name", Name.class),
    // Identity
    PASSPORT("CSS_Passport", "passport_number", Passport.class),
    US_ADULT_SSN("CSS_AdultSSN_US", "ssn", AdultSsn.class),
    DRIVERS_LICENSE("CSS_DriversLicense", "driver_license", DriversLicense.class),
    NATIONAL_ID("CSS_NationalId", "nid", NationalIdentifier.class),
    OTHER_ID("CSS_OtherID", "otherID", OtherIdentifier.class),
    ID_CARD("CSS_IdentityCard", "identity_card", IdentityCard.class),
    KEY_TAG("CSS_KeyTag", "serial_number", KeyTag.class),
    //Phone Numbers
    PHONE_NUMBER("CSS_PhoneNumber", "telephone", PhoneNumber.class),
    MOBILE_NUMBER("CSS_PhoneNumberMobile", "MobileNumber", PhoneNumber.class),
    //Financial
    PAYMENT_CARD("CSS_PaymentCard", "payment_card", PaymentCard.class),
    PROTECTED_CARD("Asset_Helix_ProtectedCard", "protected_card", Asset.class),
    US_BANK_ACCOUNT("CSS_BankAccount_US", "iban", USBankAccount.class),
    BANK_ACCOUNT("Asset_Helix_BankAccount", "account_number", BankAccount.class);

    private final String typeKey;
    private final String id;
    private final Class<? extends Asset> domain;

    AssetType(String typeKey, String id, Class<? extends Asset> domain) {
        this.typeKey = typeKey;
        this.id = id;
        this.domain = domain;
    }

    @JsonValue
    public String getTypeKey() {
        return typeKey;
    }

    public String id(){
        return this.id;
    }

    public Class<? extends Asset> domain(){
        return this.domain;
    }

    @JsonCreator
    public static AssetType fromValue(String typeKey){
        for (AssetType type: AssetType.values()){
            if(type.typeKey.equalsIgnoreCase(typeKey)){
                return type;
            }
        }
        return null;
    }
}
