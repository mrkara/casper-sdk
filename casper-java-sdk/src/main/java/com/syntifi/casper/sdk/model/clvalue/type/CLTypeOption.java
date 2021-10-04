package com.syntifi.casper.sdk.model.clvalue.type;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.syntifi.casper.sdk.exception.NoSuchTypeException;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
@EqualsAndHashCode(callSuper = true, of = { "typeName" })
public class CLTypeOption extends CLTypeWithChildren {
    private final String typeName = CLType.OPTION;

    @JsonProperty(CLType.OPTION)
    private List<Object> childTypeObjects;

    public List<Object> getChildTypeObjects() {
        super.loadChildTypeObjects(childTypeObjects);
        return this.childTypeObjects;
    }

    public void setChildTypeObjects(List<Object> childTypeObjects)
            throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
            NoSuchMethodException, SecurityException, NoSuchTypeException {
        this.childTypeObjects = childTypeObjects;
        super.loadCLTypes(childTypeObjects);
    }

    public List<Object> loadChildTypeObjects() {
        return this.childTypeObjects;
    }
}
