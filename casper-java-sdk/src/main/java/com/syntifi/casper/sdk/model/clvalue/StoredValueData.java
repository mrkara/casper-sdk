package com.syntifi.casper.sdk.model.clvalue;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class StoredValueData {
    @JsonProperty("api_version")
    private String apiVersion;

    @JsonProperty("stored_value")
    private StoredValue<?> storedValue;

    @JsonProperty("merkle_proof")
    private String merkleProof;
}
