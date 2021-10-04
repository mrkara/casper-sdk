package com.syntifi.casper.sdk.model.clvalue;

import com.syntifi.casper.sdk.model.clvalue.type.CLType;

import lombok.EqualsAndHashCode;

/**
 * Abstract class for those CLValues which have a child collection
 * 
 * @author Alexandre Carvalho
 * @author Andre Bertolace
 * @see CLValue
 * @since 0.0.1
 */
@EqualsAndHashCode(callSuper = true)
public abstract class CLValueWithChildren<T, P extends CLType> extends CLValue<T, P> {
    protected abstract void setChildTypes();
}
