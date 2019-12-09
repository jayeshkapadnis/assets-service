package com.affinion.gce.model.member;

import java.util.Optional;
import java.util.stream.Stream;

public enum IdType {
    ID("Id"), EXTERNAL_MEMBER_REF("ExternalMemberRef");

    private final String idType;

    IdType(String idType) {
        this.idType = idType;
    }

    public static Optional<IdType> fromValue(String idType){
        return Stream.of(values())
                .filter(i -> i.idType.equals(idType))
                .findFirst();
    }
}
