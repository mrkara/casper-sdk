package com.syntifi.casper.model.info.get.peers;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CasperNode {
    @JsonProperty("node_id")
    public String nodeId;

    @JsonProperty("address")
    public String address;
}
