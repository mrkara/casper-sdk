package com.syntifi.casper.sdk.model.clvalue.type;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * CLType for {@link CLType.OPTION}
 * 
 * @author Alexandre Carvalho
 * @author Andre Bertolace
 * @see CLType
 * @since 0.0.1
 */
@Getter
@EqualsAndHashCode(callSuper = true, of = { "typeName" })
public class CLTypeOption extends CLTypeChildren {
    @JsonIgnore
    private final String typeName = CLType.OPTION;

    @JsonProperty(CLType.OPTION)
    private List<Object> childTypeObjects;

    public List<Object> getChildTypeObjects() {
        super.loadChildTypeObjects(childTypeObjects);
        return this.childTypeObjects;
    }

    public void setChildTypeObjects(List<Object> childTypeObjects) {
        this.childTypeObjects = childTypeObjects;
        super.loadCLTypes(childTypeObjects);
    }

    public List<Object> loadChildTypeObjects() {
        return this.childTypeObjects;
    }
}