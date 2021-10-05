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
 * CLType for {@link AbstractCLType.RESULT}
 * 
 * @author Alexandre Carvalho
 * @author Andre Bertolace
 * @see AbstractCLType
 * @since 0.0.1
 */
@Getter
@EqualsAndHashCode(callSuper = false, of = { "typeName", "okErrTypes" })
public class CLTypeResult extends AbstractCLType {

    /**
     * Support class for {@link AbstractCLType.RESULT} ok/err types
     * 
     * @author Alexandre Carvalho
     * @author Andre Bertolace
     * @see AbstractCLType
     * @since 0.0.1
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonPropertyOrder({ "ok", "err" })
    public class CLTypeResultOkErrTypes {
        @JsonIgnore
        private AbstractCLType okClType;
        @JsonIgnore
        private AbstractCLType errClType;

        @JsonSetter("ok")
        protected void setJsonKey(AbstractCLType clType) {
            this.okClType = clType;
        }

        @JsonGetter("ok")
        protected Object getJsonKey() {
            if (this.okClType instanceof AbstractCLTypeBasic) {
                return this.okClType.getTypeName();
            } else {
                return this.okClType;
            }
        }

        @JsonSetter("err")
        protected void setJsonValue(AbstractCLType clType) {
            this.errClType = clType;
        }

        @JsonGetter("err")
        protected Object getJsonValue() {
            if (this.errClType instanceof AbstractCLTypeBasic) {
                return this.errClType.getTypeName();
            } else {
                return this.errClType;
            }
        }
    }

    private final String typeName = AbstractCLType.RESULT;

    @Setter
    @JsonProperty(AbstractCLType.RESULT)
    private CLTypeResultOkErrTypes okErrTypes;
}
