package com.syntifi.casper.sdk.model.contract;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.syntifi.casper.sdk.model.clvalue.type.CLType;
import com.syntifi.casper.sdk.model.clvalue.type.CLTypeBasic;

import lombok.Data;

/**
 * A named key.
 * 
 * @author Alexandre Carvalho
 * @author Andre Bertolace
 * @since 0.0.1
 */
@Data
public class Parameter {
    /**
     * The value of the entry: a casper `Key` type.
     */
    @JsonIgnore
    private CLType clType;

    /**
     * The name of the entry.
     */
    @JsonProperty("name")
    private String name;

    @JsonSetter("cl_type")
    public void setJsonClType(CLType clType) {
        this.clType = clType;
    }

    @JsonGetter("cl_type")
    public Object getJsonClType() {
        if (this.clType instanceof CLTypeBasic) {
            return this.clType.getTypeName();
        } else {
            return this.clType;
        }
    }
}
