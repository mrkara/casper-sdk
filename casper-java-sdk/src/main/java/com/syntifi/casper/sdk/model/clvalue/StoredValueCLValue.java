package com.syntifi.casper.sdk.model.clvalue;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

import lombok.Data;

@Data
@JsonTypeName("CLValue")
public class StoredValueCLValue implements StoredValue<CLValue<?, ?>> {
    @JsonProperty("CLValue")
    @JsonUnwrapped
    private CLValue<?, ?> value;
}
