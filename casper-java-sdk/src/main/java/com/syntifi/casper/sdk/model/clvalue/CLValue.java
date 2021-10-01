package com.syntifi.casper.sdk.model.clvalue;

import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonTypeResolver;
import com.syntifi.casper.sdk.exception.CLValueDecodeException;
import com.syntifi.casper.sdk.exception.CLValueEncodeException;
import com.syntifi.casper.sdk.exception.DynamicInstanceException;
import com.syntifi.casper.sdk.exception.NoSuchTypeException;
import com.syntifi.casper.sdk.jackson.CLTypeResolver;
import com.syntifi.casper.sdk.model.clvalue.encdec.CLValueDecoder;
import com.syntifi.casper.sdk.model.clvalue.encdec.CLValueEncoder;
import com.syntifi.casper.sdk.model.clvalue.interfaces.DecodableValue;
import com.syntifi.casper.sdk.model.clvalue.interfaces.EncodableValue;
import com.syntifi.casper.sdk.model.clvalue.type.CLType;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * Base class for CLValues
 * 
 * @author Alexandre Carvalho
 * @author Andre Bertolace
 * @see CLTypeData
 * @since 0.0.1
 */
@Getter
@Setter
@EqualsAndHashCode(of = { "bytes", "value" })
@JsonTypeInfo(use = JsonTypeInfo.Id.NONE)
@JsonTypeResolver(CLTypeResolver.class)
@JsonPropertyOrder({ "cl_type", "bytes", "parsed" }) // TODO: Just for testing
public abstract class CLValue<T, P extends CLType> implements EncodableValue, DecodableValue {
    private String bytes;
    @JsonProperty("parsed")
    @JsonInclude(Include.NON_NULL)
    private String parsed;
    @JsonIgnore
    private T value;

    @JsonSetter(value = "bytes")
    protected void setJsonBytes(String bytes)
            throws IOException, CLValueDecodeException, DynamicInstanceException, NoSuchTypeException {
        this.bytes = bytes;

        try (CLValueDecoder clvd = new CLValueDecoder(this.bytes)) {
            this.decode(clvd);
        }
    }

    @JsonGetter(value = "bytes")
    protected String getJsonBytes()
            throws IOException, CLValueEncodeException, DynamicInstanceException, NoSuchTypeException {
        try (CLValueEncoder clve = new CLValueEncoder()) {
            this.encode(clve);
        }

        return this.bytes;
    }

    public abstract P getClType();

    public abstract void setClType(P value);
}