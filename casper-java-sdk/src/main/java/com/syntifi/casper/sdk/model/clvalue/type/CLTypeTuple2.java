package com.syntifi.casper.sdk.model.clvalue.type;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.syntifi.casper.sdk.exception.NoSuchTypeException;

import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * CLType for {@link CLType.TUPLE2}
 * 
 * @author Alexandre Carvalho
 * @author Andre Bertolace
 * @see CLType
 * @since 0.0.1
 */
@Getter
@EqualsAndHashCode(callSuper = true, of = { "typeName" })
public class CLTypeTuple2 extends CLTypeWithChildren {
    private final String typeName = CLType.TUPLE2;

    @JsonProperty(CLType.TUPLE2)
    private List<Object> childTypeObjects;

    protected void setChildTypeObjects(List<Object> childTypeObjects)
            throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
            NoSuchMethodException, SecurityException, NoSuchTypeException {
        this.childTypeObjects = childTypeObjects;
        super.loadCLTypes(childTypeObjects);
    }
}
