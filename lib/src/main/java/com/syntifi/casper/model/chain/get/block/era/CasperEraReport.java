package com.syntifi.casper.model.chain.get.block.era;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class CasperEraReport {
    @JsonProperty("inactive_validators")
    public List<String> inactiveValidators; 

    @JsonProperty("equivocators")
    public List<String> equivocators;
    
    @JsonProperty("rewards")
    public List<CasperReward> rewards;
}
