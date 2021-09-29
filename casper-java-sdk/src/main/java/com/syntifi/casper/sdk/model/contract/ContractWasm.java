package com.syntifi.casper.sdk.model.contract;

import lombok.Data;

/**
 * A contract's Wasm.
 * 
 * @author Alexandre Carvalho
 * @author Andre Bertolace
 * @since 0.0.1
 */
@Data
public class ContractWasm {

    /**
     * ContractWasm(object/string) A contract's Wasm.
     */
    private String wasm;
}
