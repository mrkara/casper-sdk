package com.syntifi.casper.sdk;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.syntifi.casper.sdk.model.transfer.Transfer;
import com.syntifi.casper.sdk.model.transfer.TransferData;

import org.junit.jupiter.api.BeforeAll;
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

    @BeforeAll
    public static void init() {
        // OBJECT_MAPPER.configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true);
        // OBJECT_MAPPER.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS,
        // true);
    }

    @Test
    void test_era_end_block() throws JsonMappingException, JsonProcessingException, IOException {
        //curl -X POST -H 'Content-Type: application/json' -d '{"id":"0","jsonrpc":"2.0","method":"chain_get_block_transfers","params":{"block_identifier":{"Height":198148}}}'  http://195.201.142.76:7777/rpc
        String inputJson = getPrettyJson(loadJsonFromFile("transfer-samples/transfer.json"));

        LOGGER.debug("Original JSON: {}", inputJson);

        TransferData transfer = OBJECT_MAPPER.readValue(inputJson, TransferData.class);

        assertTrue(transfer.getTransfers().get(0) instanceof Transfer);

        String reserializedJson = getPrettyJson(transfer);

        LOGGER.debug("Serialized JSON: {}", reserializedJson);

        assertEquals(inputJson, reserializedJson);
    }
}