package com.syntifi.casper.model.chain.get.block;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CasperBlockProof {

    @JsonProperty("public_key")
    public String publicKey;
    
    @JsonProperty("signature")
    public String signature;

}
