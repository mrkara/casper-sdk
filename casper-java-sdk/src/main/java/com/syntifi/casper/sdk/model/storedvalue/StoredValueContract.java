package com.syntifi.casper.sdk.model.storedvalue;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.syntifi.casper.sdk.model.contract.Contract;

import lombok.Data;

@Data
@JsonTypeName("Contract")
public class StoredValueContract implements StoredValue<Contract> {
    @JsonProperty("Contract")
    public Contract value;
}
