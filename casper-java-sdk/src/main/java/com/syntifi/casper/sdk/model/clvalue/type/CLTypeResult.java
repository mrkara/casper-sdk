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
public class CLTypeResult extends CLTypeBasic {

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
    public class CLTypeResultOkErrTypes {
        @JsonProperty("ok")
        private String ok;
        @JsonProperty("err")
        private String err;

        @JsonIgnore
        public CLType getOkType() throws DynamicInstanceException, NoSuchTypeException {
            return CLTypeData.createCLTypeFromCLTypeName(this.ok);
        }

        @JsonIgnore
        public void setOkType(CLType okType) {
            this.ok = okType.getTypeName();
        }

        @JsonIgnore
        public CLType getErrType() throws DynamicInstanceException, NoSuchTypeException {
            return CLTypeData.createCLTypeFromCLTypeName(this.err);
        }

        @JsonIgnore
        public void setErrType(CLType errType) {
            this.ok = errType.getTypeName();
        }
    }

    @JsonIgnore
    private final String typeName = CLType.RESULT;

    @Setter
    @JsonProperty(CLType.RESULT)
    private CLTypeResultOkErrTypes okErrTypes;
}
