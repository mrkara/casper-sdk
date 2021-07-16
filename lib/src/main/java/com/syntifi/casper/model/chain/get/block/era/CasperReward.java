package com.syntifi.casper.model.chain.get.block.era;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CasperReward {
    @JsonProperty("validator")
    public String validator;

    @JsonProperty("amount")
    public long amount;
}
