package com.syntifi.casper.sdk.model.clvalue.type;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.syntifi.casper.sdk.exception.DynamicInstanceException;
import com.syntifi.casper.sdk.exception.NoSuchTypeException;

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
@EqualsAndHashCode(callSuper = false, of = { "typeName", "listTypeName" })
public class CLTypeList extends CLTypeBasic {
    @JsonIgnore
    private final String typeName = CLType.LIST;

    @Setter
    @JsonProperty(CLType.LIST)
    private String listTypeName;

    @JsonIgnore
    public CLType getListType() throws DynamicInstanceException, NoSuchTypeException {
        return CLTypeData.createCLTypeFromCLTypeName(listTypeName);
    }

    @JsonIgnore
    public void setListType(CLType listType) {
        this.listTypeName = listType.getTypeName();
    }
}
