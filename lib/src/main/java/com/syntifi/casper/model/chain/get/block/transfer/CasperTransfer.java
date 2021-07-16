package com.syntifi.casper.model.chain.get.block.transfer;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CasperTransfer {
    @JsonProperty("id") 
    public long id;

    @JsonProperty("to")
    public String to;

    @JsonProperty("from")
    public String from;

    @JsonProperty("amount")
    public Double amount;

    @JsonProperty("deploy_hash")
    public String deployHash;

    @JsonProperty("source")
    public String source; 

    @JsonProperty("target")
    public String target;

    @JsonProperty("gas")
    public int gas;
}

