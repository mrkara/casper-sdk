package com.syntifi.casper.sdk;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.syntifi.casper.sdk.model.stateroothash.StateRootHashData;

import org.junit.jupiter.api.BeforeAll;
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

    @BeforeAll
    public static void init() {
        // OBJECT_MAPPER.configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true);
        // OBJECT_MAPPER.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS,
        // true);
    }

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