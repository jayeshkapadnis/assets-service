package com.affinion.gce.model;

import com.affinion.gce.jpa.entity.AssetAttribute;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.List;

public interface Hashable {
    public List<AssetAttribute> hashAttributes();

    default String hashSequence(String value) {
        return DigestUtils.sha256Hex(value);
    }
}
