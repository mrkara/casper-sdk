package com.syntifi.casper.sdk.model.clvalue.type;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.syntifi.casper.sdk.exception.DynamicInstanceException;
import com.syntifi.casper.sdk.exception.NoSuchTypeException;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@EqualsAndHashCode(callSuper = true)
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
