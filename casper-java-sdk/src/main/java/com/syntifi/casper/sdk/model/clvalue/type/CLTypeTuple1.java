package com.syntifi.casper.sdk.model.clvalue.type;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.syntifi.casper.sdk.exception.NoSuchTypeException;

import lombok.Data;

@Data
public class CLTypeTuple1 extends CLType {
    @JsonIgnore
    private String typeName = CLType.TUPLE1;

    @JsonProperty("Tuple1")
    private List<String> childTypes = new ArrayList<>();

    @JsonIgnore
    public CLTypeData getChildClTypeData(int index) {
        try {
            return CLTypeData.getTypeByName(getChildTypes().get(index));
        } catch (NoSuchTypeException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }
}
