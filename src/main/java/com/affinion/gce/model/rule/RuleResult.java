package com.affinion.gce.model.rule;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Optional;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RuleResult {
    private List<RuleAttribute> result;
    private String ruleName;
    @JsonProperty("ruleID")
    private Integer ruleId;

    public Optional<String> attributeValueByKey(String attributeName){
        return result.stream()
                .filter(a -> a.getAttributeName().equalsIgnoreCase(attributeName))
                .findFirst()
                .map(RuleAttribute::getAttributeValue);
    }

    public Optional<Integer> assetMaxAttributeValue(){
        return attributeValueByKey("asset_count_max")
                .map(Integer::valueOf);
    }
}
