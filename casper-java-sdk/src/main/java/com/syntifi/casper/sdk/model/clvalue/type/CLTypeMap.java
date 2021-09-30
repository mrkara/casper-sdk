package com.syntifi.casper.sdk.model.clvalue.type;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class CLTypeMap extends CLType {
    @JsonIgnore
    private String typeName = CLType.MAP;

    @JsonProperty("ByteArray")
    private int length;
}
