package com.syntifi.casper.sdk;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.syntifi.casper.sdk.model.stateroothash.StateRootHashData;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Unit tests for {@link StateRootHashData}
 * 
 * @author Alexandre Carvalho
 * @author Andre Bertolace
 * @since 0.0.1
 */
public class StateRootHashTest extends AbstractJsonTests {

    private static final Logger LOGGER = LoggerFactory.getLogger(StatusTests.class);

    @Test
    void test_era_end_block() throws JsonMappingException, JsonProcessingException, IOException {
        //curl -X POST -H 'Content-Type: application/json' -d '{"id":"1132050564","jsonrpc":"2.0","method":"chain_get_state_root_hash","params":{"block_identifier":{"Height":0}}}' http://nodeIP:7777/rpc 
        String inputJson = getPrettyJson(loadJsonFromFile("block-samples/state-root-hash-block0.json"));

        LOGGER.debug("Original JSON: {}", inputJson);

        StateRootHashData root = OBJECT_MAPPER.readValue(inputJson, StateRootHashData.class);

        String reserializedJson = getPrettyJson(root);

        LOGGER.debug("Serialized JSON: {}", reserializedJson);

        assertEquals(inputJson, reserializedJson);
    }
}