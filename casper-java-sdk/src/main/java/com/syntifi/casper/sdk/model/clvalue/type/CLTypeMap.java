package com.syntifi.casper.sdk.model.clvalue.type;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.syntifi.casper.sdk.exception.DynamicInstanceException;
import com.syntifi.casper.sdk.exception.NoSuchTypeException;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * CLType for {@link CLType.MAP}
 * 
 * @author Alexandre Carvalho
 * @author Andre Bertolace
 * @see CLType
 * @since 0.0.1
 */
@Getter
@EqualsAndHashCode(callSuper = false, of = { "typeName", "keyValueTypes" })
public class CLTypeMap extends CLType {
    /**
     * Support class for {@link CLType.MAP} entry types
     * 
     * @author Alexandre Carvalho
     * @author Andre Bertolace
     * @see CLType
     * @since 0.0.1
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonPropertyOrder({ "key", "value" })
    public class CLTypeMapEntryType {
        @JsonIgnore
        private CLType keyType;
        @JsonIgnore
        private CLType valueType;

        @JsonSetter("key")
        protected void setJsonKey(CLType clType) {
            this.keyType = clType;
        }

        @JsonGetter("key")
        protected Object getJsonKey() {
            if (this.keyType instanceof CLTypeBasic) {
                return this.keyType.getTypeName();
            } else {
                return this.keyType;
            }
        }

        @JsonSetter("value")
        protected void setJsonValue(CLType clType) {
            this.valueType = clType;
        }

        @JsonGetter("value")
        protected Object getJsonValue() {
            if (this.valueType instanceof CLTypeBasic) {
                return this.valueType.getTypeName();
            } else {
                return this.valueType;
            }
        }
    }

    private final String typeName = CLType.MAP;

    @Setter
    @JsonProperty(CLType.MAP)
    private CLTypeMapEntryType keyValueTypes;
}
