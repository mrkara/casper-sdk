package com.syntifi.casper.sdk.model.clvalue.type;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.syntifi.casper.sdk.exception.NoSuchTypeException;

import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * CLType for {@link AbstractCLType.TUPLE1}
 * 
 * @author Alexandre Carvalho
 * @author Andre Bertolace
 * @see AbstractCLType
 * @since 0.0.1
 */
@Getter
@EqualsAndHashCode(callSuper = true, of = { "typeName" })
public class CLTypeTuple1 extends AbstractCLTypeWithChildren {
    private final String typeName = AbstractCLType.TUPLE1;

    @JsonProperty(AbstractCLType.TUPLE1)
    private List<Object> childTypeObjects;

    protected void setChildTypeObjects(List<Object> childTypeObjects)
            throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
            NoSuchMethodException, SecurityException, NoSuchTypeException {
        this.childTypeObjects = childTypeObjects;
        super.loadCLTypes(childTypeObjects);
    }
}
