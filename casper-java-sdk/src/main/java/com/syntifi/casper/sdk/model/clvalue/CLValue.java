package com.syntifi.casper.sdk.model.clvalue;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonTypeResolver;
import com.syntifi.casper.sdk.jackson.CLTypeResolver;
import com.syntifi.casper.sdk.model.clvalue.interfaces.DecodableValue;
import com.syntifi.casper.sdk.model.clvalue.interfaces.EncodableValue;
import com.syntifi.casper.sdk.model.clvalue.type.CLType;

import lombok.Data;

@Data
@JsonTypeInfo(use = JsonTypeInfo.Id.NONE)
@JsonTypeResolver(CLTypeResolver.class)
@JsonPropertyOrder({ "cl_type", "bytes", "parsed" }) // TODO: Just for testing
public abstract class CLValue<T, P extends CLType> implements EncodableValue, DecodableValue {
    @JsonProperty("bytes")
    private String bytes;
    @JsonProperty("parsed")
    @JsonInclude(Include.NON_NULL)
    private String parsed;
    @JsonIgnore
    private T value;

    public abstract P getClType();

    public abstract void setClType(P value);
}
