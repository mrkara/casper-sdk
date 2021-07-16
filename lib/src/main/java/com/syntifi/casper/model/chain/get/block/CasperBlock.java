package com.syntifi.casper.model.chain.get.block;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CasperBlock {
    @JsonProperty("header")
    public CasperBlockHeader header;

    @JsonProperty("hash")
    public String hash;

    @JsonProperty("body")
    public CasperBlockBody body;
    
    @JsonProperty("proofs")
    public List<CasperBlockProof> proofs;
}
