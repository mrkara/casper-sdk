package com.syntifi.casper.model.chain.get.block;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CasperBlockBody {
    @JsonProperty("proposer")
    public String proposer;

    @JsonProperty("deploy_hashes")
    public List<String> deployHashes;

    @JsonProperty("transfer_hashes")
    public List<String> transferHashes; 
    
}
