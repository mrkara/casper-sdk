package com.syntifi.casper.sdk.model.clvalue.type;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.syntifi.casper.sdk.exception.DynamicInstanceException;
import com.syntifi.casper.sdk.exception.NoSuchTypeException;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@EqualsAndHashCode(callSuper = false, of = { "typeName", "keyValueTypes" })
public class CLTypeMap extends CLTypeBasic {
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class CLTypeMapEntryType {
        @JsonProperty("key")
        private String key;
        @JsonProperty("value")
        private String value;

        @JsonIgnore
        public CLType getKeyType() throws DynamicInstanceException, NoSuchTypeException {
            return CLTypeData.createCLTypeFromCLTypeName(this.key);
        }

        @JsonIgnore
        public void setKeyType(CLType keyType) {
            this.key = keyType.getTypeName();
        }

        @JsonIgnore
        public CLType getValueType() throws DynamicInstanceException, NoSuchTypeException {
            return CLTypeData.createCLTypeFromCLTypeName(this.value);
        }

        @JsonIgnore
        public void setValueType(CLType valueType) {
            this.key = valueType.getTypeName();
        }
    }

    @JsonIgnore
    private final String typeName = CLType.MAP;

    @Setter
    @JsonProperty(CLType.MAP)
    private CLTypeMapEntryType keyValueTypes;
}
