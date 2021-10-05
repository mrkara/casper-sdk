package com.syntifi.casper.sdk.model.contract;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.syntifi.casper.sdk.model.clvalue.type.CLType;
import com.syntifi.casper.sdk.model.clvalue.type.CLTypeBasic;

import lombok.Data;

/**
 * No description available
 * 
 * @author Alexandre Carvalho
 * @author Andre Bertolace
 * @since 0.0.1
 */
@Data
public class EntryPoint {
    public enum EntryPointAccess {
        @JsonProperty("Public")
        PUBLIC;
    }

    public enum EntryPointType {
        @JsonProperty("Session")
        SESSION, @JsonProperty("Contract")
        CONTRACT;
    }

    /**
     * access(enum/String) - 
     */
    @JsonProperty("access")
    private EntryPointAccess access; // TODO: Check object/Groups on spec

    /**
     * args(Array/Object) - Parameter to a method
     */
    @JsonProperty("args")
    private List<Parameter> args;

    /**
     * entry_point_type(enum/String) - Context of method execution
     */
    @JsonProperty("entry_point_type")
    private EntryPointType type;

    /**
     * name(String)
     */
    @JsonProperty("name")
    private String name;

    /**
     * ret({@link CLType})
     */
    @JsonIgnore
    private CLType ret;

    @JsonSetter("ret")
    protected void setJsonRet(CLType clType) {
        this.ret = clType;
    }

    /**
     * The accessor for jackson serialization
     * 
     * @return String if cl_type is basic type, CLType object if not.
     */
    @JsonGetter("ret")
    protected Object getJsonRet() {
        if (this.ret instanceof CLTypeBasic) {
            return this.ret.getTypeName();
        } else {
            return this.ret;
        }
    }
}
