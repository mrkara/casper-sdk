package com.syntifi.casper.sdk.model.clvalue.type;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.syntifi.casper.sdk.exception.NoSuchTypeException;

import lombok.Data;

@Data
public class CLTypeOption extends CLType {
    @JsonIgnore
    private String typeName = CLType.OPTION;

    @JsonProperty("Option")
    private List<String> childTypes = new ArrayList<>();

    @JsonIgnore
    public CLTypeData getChildClTypeData() {
        try {
            return CLTypeData.getTypeByName(getChildTypes().get(0));
        } catch (NoSuchTypeException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }
}
