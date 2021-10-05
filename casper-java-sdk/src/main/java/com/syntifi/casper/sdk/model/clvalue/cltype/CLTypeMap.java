package com.syntifi.casper.sdk.model.clvalue.cltype;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSetter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * CLType for {@link AbstractCLType.MAP}
 * 
 * @author Alexandre Carvalho
 * @author Andre Bertolace
 * @see AbstractCLType
 * @since 0.0.1
 */
@Getter
@EqualsAndHashCode(callSuper = false, of = { "typeName", "keyValueTypes" })
public class CLTypeMap extends AbstractCLType {
    /**
     * Support class for {@link AbstractCLType.MAP} entry types
     * 
     * @author Alexandre Carvalho
     * @author Andre Bertolace
     * @see AbstractCLType
     * @since 0.0.1
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonPropertyOrder({ "key", "value" })
    public class CLTypeMapEntryType {
        @JsonIgnore
        private AbstractCLType keyType;
        @JsonIgnore
        private AbstractCLType valueType;

        @JsonSetter("key")
        protected void setJsonKey(AbstractCLType clType) {
            this.keyType = clType;
        }

        @JsonGetter("key")
        protected Object getJsonKey() {
            if (this.keyType instanceof AbstractCLTypeBasic) {
                return this.keyType.getTypeName();
            } else {
                return this.keyType;
            }
        }

        @JsonSetter("value")
        protected void setJsonValue(AbstractCLType clType) {
            this.valueType = clType;
        }

        @JsonGetter("value")
        protected Object getJsonValue() {
            if (this.valueType instanceof AbstractCLTypeBasic) {
                return this.valueType.getTypeName();
            } else {
                return this.valueType;
            }
        }
    }

    private final String typeName = AbstractCLType.MAP;

    @Setter
    @JsonProperty(AbstractCLType.MAP)
    private CLTypeMapEntryType keyValueTypes;
}
