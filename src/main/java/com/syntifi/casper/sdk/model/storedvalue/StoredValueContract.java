package com.syntifi.casper.sdk.model.storedvalue;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.syntifi.casper.sdk.model.contract.Contract;

import lombok.Data;

/**
 * Stored Value for {@link Contract}
 * 
 * @author Alexandre Carvalho
 * @author Andre Bertolace
 * @see StoredValue
 * @since 0.0.1
 */
@Data
@JsonTypeName("Contract")
public class StoredValueContract implements StoredValue<Contract> {
    @JsonProperty("Contract")
    public Contract value;
}
