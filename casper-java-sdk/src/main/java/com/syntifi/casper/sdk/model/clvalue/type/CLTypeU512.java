package com.syntifi.casper.sdk.model.clvalue.type;

import com.fasterxml.jackson.annotation.JsonCreator;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * CLType for {@link CLType.U512}
 * 
 * @author Alexandre Carvalho
 * @author Andre Bertolace
 * @see CLType
 * @since 0.0.1
 */

@Getter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false, of = { "typeName" })
public class CLTypeU512 extends CLTypeBasic {
    private final String typeName = CLType.U512;

    @JsonCreator
    protected CLTypeU512(String typeName) {
        super(typeName);
    }
}
