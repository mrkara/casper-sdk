package com.syntifi.casper.model.chain.get.block.era;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CasperValidatorWeight {
    @JsonProperty("validator")
    public String validator;

    @JsonProperty("weight")
    public long weight; 
}
