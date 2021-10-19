package com.syntifi.casper.sdk.service;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import com.syntifi.casper.sdk.identifier.block.HeightBlockIdentifier;
import com.syntifi.casper.sdk.model.block.JsonBlockData;
import com.syntifi.casper.sdk.model.stateroothash.StateRootHashData;
import com.syntifi.casper.sdk.model.storedvalue.StoredValueData;

import org.junit.jupiter.api.Test;

public final class StoredValueCasperServiceTests extends AbstractJsonRpcTests {

    @Test
    void check_contract_package() {
        StateRootHashData stateRootHashData = casperServiceMainnet.getStateRootHash();
        JsonBlockData jsonBlockData = casperServiceMainnet
                .getBlock(HeightBlockIdentifier.builder().height(100000).build());
        JsonBlockData firstJsonBlockData = casperServiceMainnet.getBlock();

        // StateRootHashData rootHash =
        // casperServiceMainnet.getStateRootHash(BlockIdentifierByHash.builder()
        // .hash("09ac52260e370ed56bba5283a79b03d524b4f420bf964d7e629b0819dd1be09d").build());

        StoredValueData svdAccount = casperServiceMainnet.getStateItem(
                "09ac52260e370ed56bba5283a79b03d524b4f420bf964d7e629b0819dd1be09d",
                "account-hash-e1431ecb9f20f2a6e6571886b1e2f9dec49ebc6b2d3d640a53530abafba9bfa1", new ArrayList<>());

        StoredValueData svdContract = casperServiceMainnet.getStateItem(
                "09ac52260e370ed56bba5283a79b03d524b4f420bf964d7e629b0819dd1be09d",
                "hash-d2469afeb99130f0be7c9ce230a84149e6d756e306ef8cf5b8a49d5182e41676", new ArrayList<>());

        // StoredValueData svdContractPackage = casperServiceMainnet.getStateItem(
        // "09ac52260e370ed56bba5283a79b03d524b4f420bf964d7e629b0819dd1be09d",
        // "contract-package-wasmd63c44078a1931b5dc4b80a7a0ec586164fd0470ce9f8b23f6d93b9e86c5944d",
        // new ArrayList<>());

        assertTrue(stateRootHashData instanceof StateRootHashData);
    }

}
