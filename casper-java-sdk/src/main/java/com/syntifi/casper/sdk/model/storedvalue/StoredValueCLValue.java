package com.syntifi.casper.sdk.model.storedvalue;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.syntifi.casper.sdk.model.clvalue.CLValue;

import lombok.Data;

/**
 * Stored Value for {@link CLType}
 * 
 * @author Alexandre Carvalho
 * @author Andre Bertolace
 * @see StoredValue
 * @since 0.0.1
 */
@Data
@JsonTypeName("CLValue")
public class StoredValueCLValue implements StoredValue<CLValue<?, ?>> {
    @JsonProperty("CLValue")
    @JsonUnwrapped
    private CLValue<?, ?> value;
}
