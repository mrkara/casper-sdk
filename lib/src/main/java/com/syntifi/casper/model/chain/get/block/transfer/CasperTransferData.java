package com.syntifi.casper.model.chain.get.block.transfer;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CasperTransferData {
    @JsonProperty("api_version")
    public String apiVersion;
    
    @JsonProperty("block_hash")
    public String blockHash;
    
    @JsonProperty("transfers")
    public List<CasperTransfer> transfers;
}
