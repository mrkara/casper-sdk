package com.syntifi.casper.sdk.model.clvalue.type;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * CLType for {@link CLType.OPTION}
 * 
 * @author Alexandre Carvalho
 * @author Andre Bertolace
 * @see CLType
 * @since 0.0.1
 */
@Getter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false, of = { "typeName" })
public class CLTypeOption extends CLType {
    private final String typeName = CLType.OPTION;

    @Setter
    @JsonIgnore
    private CLType childType;

    @JsonSetter(CLType.OPTION)
    protected void setJsonClType(CLType clType) {
        this.childType = clType;
    }

    @JsonGetter(CLType.OPTION)
    protected Object getJsonClType() {
        if (this.childType instanceof CLTypeBasic) {
            return this.childType.getTypeName();
        } else {
            return this.childType;
        }
    }
}
