package com.syntifi.casper.model.info.get.peers;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CasperNodeData {
    @JsonProperty("api_version")
    public String apiVersion;

    @JsonProperty("peers")
    public List<CasperNode> peers;

}
