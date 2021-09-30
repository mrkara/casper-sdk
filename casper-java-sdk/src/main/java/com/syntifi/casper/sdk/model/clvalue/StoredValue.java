package com.syntifi.casper.sdk.model.clvalue;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonTypeResolver;
import com.syntifi.casper.sdk.jackson.StoredValueResolver;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.WRAPPER_OBJECT)
@JsonTypeResolver(StoredValueResolver.class)
public interface StoredValue<T> {
    public T getValue();
}
