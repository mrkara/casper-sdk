package com.syntifi.casper.sdk.model.clvalue.type;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class CLTypeResult extends CLType {
    @JsonIgnore
    private String typeName = CLType.RESULT;

    @JsonProperty("ByteArray")
    private int length;
}
