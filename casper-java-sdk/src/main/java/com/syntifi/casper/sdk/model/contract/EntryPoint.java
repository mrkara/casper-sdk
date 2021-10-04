package com.syntifi.casper.sdk.model.contract;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.syntifi.casper.sdk.model.clvalue.type.CLType;
import com.syntifi.casper.sdk.model.clvalue.type.CLTypeBasic;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

/**
 * A contract struct that can be serialized as JSON object.
 * 
 * @author Alexandre Carvalho
 * @author Andre Bertolace
 * @since 0.0.1
 */
@Data
public class EntryPoint {
    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public enum Access {
        PUBLIC("Public");

        private String value;
    }

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public enum Type {
        SESSION("Session"), CONTRACT("Contract");

        private String value;
    }

    /**
     * access(enum/String) - Groups ?
     */
    @JsonProperty("access")
    private String access; // TODO: Change to enum

    /**
     * args(Array/Object) - Parameter to a method
     */
    @JsonProperty("args")
    private List<Parameter> args;

    /**
     * entry_point_type(enum/String) - Context of method execution
     */
    @JsonProperty("entry_point_type")
    private String type; // TODO: Change to enum

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
    public void setJsonRet(CLType clType) {
        this.ret = clType;
    }

    @JsonGetter("ret")
    public Object getJsonRet() {
        if (this.ret instanceof CLTypeBasic) {
            return this.ret.getTypeName();
        } else {
            return this.ret;
        }
    }
}
