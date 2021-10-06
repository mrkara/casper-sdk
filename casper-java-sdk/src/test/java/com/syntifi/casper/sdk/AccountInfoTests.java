package com.syntifi.casper.sdk;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.syntifi.casper.sdk.model.account.Account;
import com.syntifi.casper.sdk.model.account.AccountData;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Unit tests for {@link AccountData}
 * 
 * @author Alexandre Carvalho
 * @author Andre Bertolace
 * @since 0.0.1
 */
public class AccountInfoTests extends AbstractJsonTests {

    private static final Logger LOGGER = LoggerFactory.getLogger(StatusTests.class);

    @Test
    void test_status() throws JsonMappingException, JsonProcessingException, IOException {
        String inputJson = getPrettyJson(loadJsonFromFile("account-info-samples/account-info.json"));

        LOGGER.debug("Original JSON: {}", inputJson);

        AccountData ad = OBJECT_MAPPER.readValue(inputJson, AccountData.class);

        assertTrue(ad.getAccount() instanceof Account);

        String reserializedJson = getPrettyJson(ad);

        LOGGER.debug("Serialized JSON: {}", reserializedJson);

        assertEquals(inputJson, reserializedJson);
    }
}