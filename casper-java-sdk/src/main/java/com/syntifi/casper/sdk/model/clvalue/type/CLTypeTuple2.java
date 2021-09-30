package com.syntifi.casper.sdk.model.clvalue.type;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class CLTypeTuple2 extends CLType {
    @JsonIgnore
    private String typeName = CLType.TUPLE2;

    @JsonProperty("ByteArray")
    private int length;
}
