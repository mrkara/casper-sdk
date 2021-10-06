package com.syntifi.casper.sdk.model.transfer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.syntifi.casper.sdk.model.AbstractJsonTests;
import com.syntifi.casper.sdk.model.status.StatusTests;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Unit tests for {@link TransferData}
 * 
 * @author Alexandre Carvalho
 * @author Andre Bertolace
 * @since 0.0.1
 */
public class TransferTest extends AbstractJsonTests {

    private static final Logger LOGGER = LoggerFactory.getLogger(StatusTests.class);

    @Test
    void test_era_end_block() throws JsonMappingException, JsonProcessingException, IOException {
        // curl -X POST -H 'Content-Type: application/json' -d
        // '{"id":"0","jsonrpc":"2.0","method":"chain_get_block_transfers","params":{"block_identifier":{"Height":198148}}}'
        // http://195.201.142.76:7777/rpc
        String inputJson = getPrettyJson(loadJsonFromFile("transfer-samples/transfer.json"));

        LOGGER.debug("Original JSON: {}", inputJson);

        TransferData transfer = OBJECT_MAPPER.readValue(inputJson, TransferData.class);

        assertTrue(transfer.getTransfers().get(0) instanceof Transfer);

        String reserializedJson = getPrettyJson(transfer);

        LOGGER.debug("Serialized JSON: {}", reserializedJson);

        assertEquals(inputJson, reserializedJson);
    }
}