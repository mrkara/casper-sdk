package com.syntifi.casper.sdk.model.clvalue;

import com.syntifi.casper.sdk.model.storedvalue.clvalue.CLTypeData;

import lombok.Data;

/**
 * `Result` with `Ok` and `Err` variants of `CLType`s.
 * 
 * @author Alexandre Carvalho
 * @author Andre Bertolace
 * @see CLTypeData
 * @since 0.0.1
 */
@Data
public class Result {
    private CLValue<?, ?> ok;

    private CLValue<?, ?> err;

}
