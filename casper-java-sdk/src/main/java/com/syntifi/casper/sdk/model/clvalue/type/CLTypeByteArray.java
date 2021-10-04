package com.syntifi.casper.sdk.model.clvalue.type;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * CLType for {@link CLType.BYTE_ARRAY}
 * 
 * @author Alexandre Carvalho
 * @author Andre Bertolace
 * @see CLType
 * @since 0.0.1
 */
@Getter
@EqualsAndHashCode(callSuper = false, of = { "typeName", "length" })
public class CLTypeByteArray extends CLType {
    private final String typeName = CLType.BYTE_ARRAY;

    @Setter
    @JsonProperty(CLType.BYTE_ARRAY)
    private int length;
}
