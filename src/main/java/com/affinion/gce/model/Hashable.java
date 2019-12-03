package com.affinion.gce.model;

public interface Hashable {
    public String hash();

    default String hashSequence(String value){
        return null;
    }
}
