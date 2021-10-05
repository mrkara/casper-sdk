package com.syntifi.casper.sdk.model.clvalue.type;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * CLType for {@link CLType.LIST}
 * 
 * @author Alexandre Carvalho
 * @author Andre Bertolace
 * @see CLType
 * @since 0.0.1
 */
@Getter
@EqualsAndHashCode(callSuper = false, of = { "typeName", "listType" })
public class CLTypeList extends CLType {
    private final String typeName = CLType.LIST;

    @Setter
    @JsonIgnore
    private CLType listType;

    @JsonSetter(CLType.LIST)
    protected void setJsonValue(CLType clType) {
        this.listType = clType;
    }

    @JsonGetter(CLType.LIST)
    protected Object getJsonValue() {
        if (this.listType instanceof CLTypeBasic) {
            return this.listType.getTypeName();
        } else {
            return this.listType;
        }
    }
}
