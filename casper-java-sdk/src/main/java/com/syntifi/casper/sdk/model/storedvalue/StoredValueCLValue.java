package com.syntifi.casper.sdk.model.storedvalue;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.syntifi.casper.sdk.model.clvalue.CLValue;

import lombok.Data;

@Data
@JsonTypeName("CLValue")
public class StoredValueCLValue implements StoredValue<CLValue<?, ?>> {
    @JsonProperty("CLValue")
    @JsonUnwrapped
    private CLValue<?, ?> value;
}
