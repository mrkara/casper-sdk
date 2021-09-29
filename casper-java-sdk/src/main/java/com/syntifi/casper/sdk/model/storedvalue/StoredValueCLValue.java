package com.syntifi.casper.sdk.model.storedvalue;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.syntifi.casper.sdk.model.storedvalue.clvalue.AbstractCLValue;

import lombok.Data;

@Data
@JsonTypeName("CLValue")
public class StoredValueCLValue implements StoredValue<AbstractCLValue<?>> {
    @JsonProperty("CLValue")
    public AbstractCLValue<?> value;
}
