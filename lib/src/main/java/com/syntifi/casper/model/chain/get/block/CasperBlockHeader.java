package com.syntifi.casper.model.chain.get.block;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.syntifi.casper.model.chain.get.block.era.CasperEra;

public class CasperBlockHeader {
    @JsonProperty("height")
    public long height;

    @JsonProperty("state_root_hash")
    public String stateRootHash;
    
    @JsonProperty("random_bit")
    public boolean randomBit;

    @JsonProperty("era_end")
    public CasperEra eraEnd;

    @JsonProperty("body_hash")
    public String bodyHash;

    @JsonProperty("parent_hash")
    public String parentHash;

    @JsonProperty("accumulated_seed")
    public String accumulatedSeed;

    @JsonProperty("timestamp")
    public Date timeStamp;

    @JsonProperty("era_id")
    public long eraId; 

    @JsonProperty("protocol_version")
    public String protocolVersion;
    
}
