package com.syntifi.casper.sdk.model.status;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.syntifi.casper.sdk.model.AbstractJsonTests;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Unit tests for {@link Status}
 * 
 * @author Alexandre Carvalho
 * @author Andre Bertolace
 * @since 0.0.1
 */
public class StatusTests extends AbstractJsonTests {

    private static final Logger LOGGER = LoggerFactory.getLogger(StatusTests.class);

    @Test
    void test_status() throws JsonMappingException, JsonProcessingException, IOException {
        String inputJson = getPrettyJson(loadJsonFromFile("status-samples/status-info.json"));

        LOGGER.debug("Original JSON: {}", inputJson);

        Status st = OBJECT_MAPPER.readValue(inputJson, Status.class);

        assertTrue(st.getLastAddedBlockInfo() instanceof MinimalBlockInfo);

        String reserializedJson = getPrettyJson(st);

        LOGGER.debug("Serialized JSON: {}", reserializedJson);

        assertEquals(inputJson, reserializedJson);
    }
}