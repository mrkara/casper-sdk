package com.syntifi.casper.sdk.model.clvalue.type;

import lombok.NoArgsConstructor;

/**
 * Base class for all types which are simple mappings
 * 
 * @author Alexandre Carvalho
 * @author Andre Bertolace
 * @since 0.0.1
 */
@NoArgsConstructor
public abstract class CLTypeBasic extends CLType {
    protected CLTypeBasic(String typeName) {        
        if (!this.getTypeName().equals(typeName)) {
            throw new IllegalArgumentException(
                    String.format("%s is an invalid type for %s", getClass().getSimpleName(), typeName));
        }
    }
}
