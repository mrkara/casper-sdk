package com.syntifi.casper.sdk.model.clvalue.type;

import com.fasterxml.jackson.annotation.JsonCreator;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * CLType for {@link CLType.UREF}
 * 
 * @author Alexandre Carvalho
 * @author Andre Bertolace
 * @see CLType
 * @since 0.0.1
 */

@Getter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false, of = { "typeName" })
public class CLTypeURef extends CLTypeBasic {
    private final String typeName = CLType.UREF;

    @JsonCreator
    protected CLTypeURef(String typeName) {
        if (!this.typeName.equals(typeName)) {
            throw new IllegalArgumentException(
                    String.format("%s is an invalid type for %s", getClass().getSimpleName(), typeName));
        }
    }
}
