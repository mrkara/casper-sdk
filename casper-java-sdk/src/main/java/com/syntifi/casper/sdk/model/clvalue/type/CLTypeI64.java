package com.syntifi.casper.sdk.model.clvalue.type;

import com.fasterxml.jackson.annotation.JsonCreator;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * CLType for {@link CLType.I64}
 * 
 * @author Alexandre Carvalho
 * @author Andre Bertolace
 * @see CLType
 * @since 0.0.1
 */
@Getter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false, of = { "typeName" })
public class CLTypeI64 extends CLTypeBasic {
    private final String typeName = CLType.I64;

    @JsonCreator
    public CLTypeI64(String typeName) {
        if (!this.typeName.equals(typeName)) {
            throw new IllegalArgumentException(
                    String.format("%s is an invalid type for %s", getClass().getSimpleName(), typeName));
        }
    }
}
