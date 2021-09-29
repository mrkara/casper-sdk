package com.syntifi.casper.sdk.model.deploy;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonTypeResolver;
import com.syntifi.casper.sdk.jackson.TransformResolver;


/**
 * Abstract Transform containing the actual transformation performed while executing a deploy. 
 * It can be any of the following types: 
 * @see Failure 
 * @see Success 
 * 
 * @author Alexandre Carvalho
 * @author Andre Bertolace
 * @since 0.0.1
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.WRAPPER_OBJECT)
@JsonTypeResolver(TransformResolver.class)
public interface Transform {
}
