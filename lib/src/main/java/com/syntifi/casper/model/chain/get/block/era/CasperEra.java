package com.syntifi.casper.model.chain.get.block.era;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class CasperEra {

   @JsonProperty("era_report")
   public CasperEraReport eraReport;

    @JsonProperty("next_era_validator_weights")
    public List<CasperValidatorWeight> nextEraValidatorWeights;
    
}
