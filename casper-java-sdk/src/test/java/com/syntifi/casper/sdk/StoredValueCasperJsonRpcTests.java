package com.syntifi.casper.sdk;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.syntifi.casper.sdk.identifier.block.BlockIdentifierByHeight;
import com.syntifi.casper.sdk.model.block.JsonBlockData;
import com.syntifi.casper.sdk.model.stateroothash.StateRootHashData;

import org.junit.jupiter.api.Test;

public final class StoredValueCasperJsonRpcTests extends AbstractCasperJsonRpcTests {

    @Test
    void check_contract_package() {
        StateRootHashData stateRootHashData = casperServiceMainnet.getStateRootHash();
        JsonBlockData jsonBlockData = casperServiceMainnet
                .getBlock(BlockIdentifierByHeight.builder().height(100000).build());
        JsonBlockData firstJsonBlockData = casperServiceMainnet.getBlock();

        assertTrue(stateRootHashData instanceof StateRootHashData);
    }

}
