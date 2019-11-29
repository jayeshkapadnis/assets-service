package com.affinion.gce.model.rule;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RuleAttribute {
    private String attributeName;
    private String attributeValue;
}
