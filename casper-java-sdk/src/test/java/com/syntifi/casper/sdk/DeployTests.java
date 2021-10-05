package com.syntifi.casper.sdk;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.syntifi.casper.sdk.model.status.MinimalBlockInfo;
import com.syntifi.casper.sdk.model.status.Status;
import com.syntifi.casper.sdk.model.storedvalue.StoredValueData;
import com.syntifi.casper.sdk.model.deploy.Deploy;
import com.syntifi.casper.sdk.model.deploy.DeployData;
import com.syntifi.casper.sdk.model.deploy.JsonExecutionResult;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Unit tests for {@link StoredValueData}
 * 
 * @author Alexandre Carvalho
 * @author Andre Bertolace
 * @since 0.0.1
 */
public class DeployTests extends AbstractJsonTests {

    private static final Logger LOGGER = LoggerFactory.getLogger(DeployTests.class);

    @BeforeAll
    public static void init() {
        // OBJECT_MAPPER.configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true);
        // OBJECT_MAPPER.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS,
        // true);
    }

    void test_deploy_mapping_1() throws JsonMappingException, JsonProcessingException, IOException {
        String inputJson = getPrettyJson(loadJsonFromFile("deploy-samples/deploy-v1.json"));

        LOGGER.debug("Original JSON: {}", inputJson);

        DeployData dd = OBJECT_MAPPER.readValue(inputJson, DeployData.class);

        assertTrue(dd.getDeploy() instanceof Deploy);
        assertTrue(dd.getExecutionResults().get(0) instanceof JsonExecutionResult);

        String reserializedJson = getPrettyJson(dd);

        LOGGER.debug("Serialized JSON: {}", reserializedJson);

        assertEquals(inputJson, reserializedJson);
    }

    @Test
    void test_deploy_mapping_2() throws JsonMappingException, JsonProcessingException, IOException {
        String inputJson = getPrettyJson(loadJsonFromFile("deploy-samples/deploy-v2.json"));

        LOGGER.debug("Original JSON: {}", inputJson);

        DeployData dd = OBJECT_MAPPER.readValue(inputJson, DeployData.class);

        assertTrue(dd.getDeploy() instanceof Deploy);

        String reserializedJson = getPrettyJson(dd);

        LOGGER.debug("Serialized JSON: {}", reserializedJson);

        assertEquals(inputJson, reserializedJson);
    }
}