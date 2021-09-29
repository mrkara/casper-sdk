package com.syntifi.casper.sdk.model.deploy;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import com.syntifi.casper.sdk.exception.NoSuchTypeException;

/**
 * The type of operation performed while executing a deploy.
 * 
 * @author Alexandre Carvalho
 * @author Andre Bertolace
 * @since 0.0.1
 */
public enum OpKind {
    @JsonProperty("Read")
    READ,
    @JsonProperty("Write")
    WRITE,
    @JsonProperty("Add")
    ADD, 
    @JsonProperty("NoOp")
    NOOP;
}
