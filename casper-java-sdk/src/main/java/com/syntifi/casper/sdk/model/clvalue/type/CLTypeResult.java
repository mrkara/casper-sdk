package com.syntifi.casper.sdk.model.clvalue.type;

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
 * CLType for {@link CLType.RESULT}
 * 
 * @author Alexandre Carvalho
 * @author Andre Bertolace
 * @see CLType
 * @since 0.0.1
 */
@Getter
@EqualsAndHashCode(callSuper = false, of = { "typeName", "okErrTypes" })
public class CLTypeResult extends CLType {

    /**
     * Support class for {@link CLType.RESULT} ok/err types
     * 
     * @author Alexandre Carvalho
     * @author Andre Bertolace
     * @see CLType
     * @since 0.0.1
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonPropertyOrder({ "ok", "err" })
    public class CLTypeResultOkErrTypes {
        @JsonIgnore
        private CLType okClType;
        @JsonIgnore
        private CLType errClType;

        @JsonSetter("ok")
        protected void setJsonKey(CLType clType) {
            this.okClType = clType;
        }

        @JsonGetter("ok")
        protected Object getJsonKey() {
            if (this.okClType instanceof CLTypeBasic) {
                return this.okClType.getTypeName();
            } else {
                return this.okClType;
            }
        }

        @JsonSetter("err")
        protected void setJsonValue(CLType clType) {
            this.errClType = clType;
        }

        @JsonGetter("err")
        protected Object getJsonValue() {
            if (this.errClType instanceof CLTypeBasic) {
                return this.errClType.getTypeName();
            } else {
                return this.errClType;
            }
        }
    }

    private final String typeName = CLType.RESULT;

    @Setter
    @JsonProperty(CLType.RESULT)
    private CLTypeResultOkErrTypes okErrTypes;
}
