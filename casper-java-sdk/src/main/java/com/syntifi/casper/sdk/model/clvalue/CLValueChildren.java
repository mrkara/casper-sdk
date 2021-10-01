package com.syntifi.casper.sdk.model.clvalue;

import com.syntifi.casper.sdk.model.clvalue.type.CLType;

import lombok.EqualsAndHashCode;

/**
 * 
 */
@EqualsAndHashCode(callSuper = true)
public abstract class CLValueChildren<T, P extends CLType> extends CLValue<T, P> {
    protected abstract void setChildTypes();
}
