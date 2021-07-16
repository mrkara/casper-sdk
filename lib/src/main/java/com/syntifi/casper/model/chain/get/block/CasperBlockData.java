package com.syntifi.casper.model.chain.get.block;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CasperBlockData {
    @JsonProperty("api_version")
    public String apiVersion;

    @JsonProperty("block")
    public CasperBlock block;
}
