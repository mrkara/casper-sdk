package com.syntifi.casper.sdk.model.block;

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
 * Unit tests for {@link JsonBlockDataTest}
 * 
 * @author Alexandre Carvalho
 * @author Andre Bertolace
 * @since 0.0.1
 */
public class JsonBlockDataTest extends AbstractJsonTests {

    private static final Logger LOGGER = LoggerFactory.getLogger(StatusTests.class);

    @Test
    void test_era_end_block() throws JsonMappingException, JsonProcessingException, IOException {
        // curl -X POST -H 'Content-Type: application/json' -d
        // '{"id":"0","jsonrpc":"2.0","method":"chain_get_block",
        // "params":{"block_identifier":{"Height":"246762"}}}' http://nodeIP:7777/rpc
        String inputJson = getPrettyJson(loadJsonFromFile("block-samples/block-end-era.json"));

        LOGGER.debug("Original JSON: {}", inputJson);

        JsonBlockData block = OBJECT_MAPPER.readValue(inputJson, JsonBlockData.class);

        assertTrue(block.getBlock() instanceof JsonBlock);

        String reserializedJson = getPrettyJson(block);

        LOGGER.debug("Serialized JSON: {}", reserializedJson);

        assertEquals(inputJson, reserializedJson);
    }
}