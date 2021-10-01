package com.syntifi.casper.sdk;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.syntifi.casper.sdk.exception.CLValueDecodeException;
import com.syntifi.casper.sdk.exception.CLValueEncodeException;
import com.syntifi.casper.sdk.exception.DynamicInstanceException;
import com.syntifi.casper.sdk.exception.NoSuchTypeException;
import com.syntifi.casper.sdk.model.clvalue.CLValueBool;
import com.syntifi.casper.sdk.model.clvalue.CLValueByteArray;
import com.syntifi.casper.sdk.model.clvalue.CLValueI32;
import com.syntifi.casper.sdk.model.clvalue.CLValueOption;
import com.syntifi.casper.sdk.model.clvalue.CLValueString;
import com.syntifi.casper.sdk.model.clvalue.CLValueTuple1;
import com.syntifi.casper.sdk.model.clvalue.CLValueTuple2;
import com.syntifi.casper.sdk.model.clvalue.CLValueTuple3;
import com.syntifi.casper.sdk.model.clvalue.CLValueU8;
import com.syntifi.casper.sdk.model.clvalue.encdec.CLValueDecoder;
import com.syntifi.casper.sdk.model.clvalue.encdec.CLValueEncoder;
import com.syntifi.casper.sdk.model.storedvalue.StoredValueCLValue;
import com.syntifi.casper.sdk.model.storedvalue.StoredValueData;

import org.javatuples.Pair;
import org.javatuples.Triplet;
import org.javatuples.Unit;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NewCLValueTests {
    private static final Logger LOGGER = LoggerFactory.getLogger(NewCLValueTests.class);

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Test
    void test_string_mapping() throws JsonMappingException, JsonProcessingException, IOException,
            CLValueDecodeException, DynamicInstanceException, NoSuchTypeException {
        String inputJson = getPrettyJson(loadJsonFromFile("stored-value-samples/stored-value-string.json"));

        LOGGER.debug("Original JSON: {}", inputJson);

        StoredValueData sv = OBJECT_MAPPER.readValue(inputJson, StoredValueData.class);
        try (CLValueDecoder clvd = new CLValueDecoder(
                ((StoredValueCLValue) sv.getStoredValue()).getValue().getBytes())) {
            ((StoredValueCLValue) sv.getStoredValue()).getValue().decode(clvd);
        }
        // Should be string
        // assertTrue(sv.getStoredValue().getValue() instanceof CLValueString);
        CLValueString expected = new CLValueString("the string");
        try (CLValueEncoder clve = new CLValueEncoder()) {
            expected.encode(clve);
        }
        //// assertEquals(expected, sv.getStoredValue().getValue());

        String reserializedJson = getPrettyJson(sv);

        LOGGER.debug("Serialized JSON: {}", reserializedJson);

        assertEquals(inputJson, reserializedJson);
    }

    @Test
    void test_bool_mapping() throws JsonMappingException, JsonProcessingException, IOException, CLValueDecodeException,
            DynamicInstanceException, NoSuchTypeException {
        String inputJson = getPrettyJson(loadJsonFromFile("stored-value-samples/stored-value-bool.json"));

        LOGGER.debug("Original JSON: {}", inputJson);

        StoredValueData sv = OBJECT_MAPPER.readValue(inputJson, StoredValueData.class);
        try (CLValueDecoder clvd = new CLValueDecoder(
                ((StoredValueCLValue) sv.getStoredValue()).getValue().getBytes())) {
            ((StoredValueCLValue) sv.getStoredValue()).getValue().decode(clvd);
        }
        // Should be string
        assertTrue(sv.getStoredValue().getValue() instanceof CLValueBool);
        CLValueBool expected = new CLValueBool(true);
        try (CLValueEncoder clve = new CLValueEncoder()) {
            expected.encode(clve);
        }
        // assertEquals(expected, sv.getStoredValue().getValue());

        String reserializedJson = getPrettyJson(sv);

        LOGGER.debug("Serialized JSON: {}", reserializedJson);

        assertEquals(inputJson, reserializedJson);
    }

    @Test
    void test_option_empty_clvalue_mapping() throws IOException, CLValueEncodeException, DynamicInstanceException,
            CLValueDecodeException, NoSuchTypeException {
        String inputJson = getPrettyJson(loadJsonFromFile("stored-value-samples/stored-value-option-empty.json"));

        LOGGER.debug("Original JSON: {}", inputJson);

        StoredValueData sv = OBJECT_MAPPER.readValue(inputJson, StoredValueData.class);
        try (CLValueDecoder clvd = new CLValueDecoder(
                ((StoredValueCLValue) sv.getStoredValue()).getValue().getBytes())) {
            ((StoredValueCLValue) sv.getStoredValue()).getValue().decode(clvd);
        }
        // Should be CLValueOption
        assertTrue(sv.getStoredValue().getValue() instanceof CLValueOption);
        CLValueOption expected = new CLValueOption(Optional.ofNullable(null));
        try (CLValueEncoder clve = new CLValueEncoder()) {
            expected.encode(clve);
        }
        // assertEquals(expected, sv.getStoredValue().getValue());

        String reserializedJson = getPrettyJson(sv);

        LOGGER.debug("Serialized JSON: {}", reserializedJson);

        assertEquals(inputJson, reserializedJson);
    }

    @Test
    void test_option_bool_clvalue_mapping() throws IOException, CLValueEncodeException, DynamicInstanceException,
            CLValueDecodeException, NoSuchTypeException {
        String inputJson = getPrettyJson(loadJsonFromFile("stored-value-samples/stored-value-option-bool.json"));

        LOGGER.debug("Original JSON: {}", inputJson);

        StoredValueData sv = OBJECT_MAPPER.readValue(inputJson, StoredValueData.class);
        try (CLValueDecoder clvd = new CLValueDecoder(
                ((StoredValueCLValue) sv.getStoredValue()).getValue().getBytes())) {
            ((StoredValueCLValue) sv.getStoredValue()).getValue().decode(clvd);
        }
        // Should be CLValueOption
        assertTrue(sv.getStoredValue().getValue() instanceof CLValueOption);
        CLValueOption expected = new CLValueOption(Optional.of(new CLValueBool(true)));
        try (CLValueEncoder clve = new CLValueEncoder()) {
            expected.encode(clve);
        }
        // assertEquals(expected, sv.getStoredValue().getValue());

        String reserializedJson = getPrettyJson(sv);

        LOGGER.debug("Serialized JSON: {}", reserializedJson);

        assertEquals(inputJson, reserializedJson);
    }

    @Test
    void test_option_i32_clvalue_mapping() throws IOException, CLValueEncodeException, DynamicInstanceException,
            CLValueDecodeException, NoSuchTypeException {
        String inputJson = getPrettyJson(loadJsonFromFile("stored-value-samples/stored-value-option-i32.json"));

        LOGGER.debug("Original JSON: {}", inputJson);

        StoredValueData sv = OBJECT_MAPPER.readValue(inputJson, StoredValueData.class);
        try (CLValueDecoder clvd = new CLValueDecoder(
                ((StoredValueCLValue) sv.getStoredValue()).getValue().getBytes())) {
            ((StoredValueCLValue) sv.getStoredValue()).getValue().decode(clvd);
        }
        // Should be CLValueOption
        assertTrue(sv.getStoredValue().getValue() instanceof CLValueOption);
        CLValueOption expected = new CLValueOption(Optional.of(new CLValueI32(10)));
        try (CLValueEncoder clve = new CLValueEncoder()) {
            expected.encode(clve);
        }
        // assertEquals(expected, sv.getStoredValue().getValue());

        String reserializedJson = getPrettyJson(sv);

        LOGGER.debug("Serialized JSON: {}", reserializedJson);

        assertEquals(inputJson, reserializedJson);
    }

    @Test
    void test_tuple1_bool_clvalue_mapping() throws IOException, CLValueEncodeException, DynamicInstanceException,
            CLValueDecodeException, NoSuchTypeException {
        String inputJson = getPrettyJson(loadJsonFromFile("stored-value-samples/stored-value-tuple1-bool.json"));

        LOGGER.debug("Original JSON: {}", inputJson);

        StoredValueData sv = OBJECT_MAPPER.readValue(inputJson, StoredValueData.class);
        try (CLValueDecoder clvd = new CLValueDecoder(
                ((StoredValueCLValue) sv.getStoredValue()).getValue().getBytes())) {
            ((StoredValueCLValue) sv.getStoredValue()).getValue().decode(clvd);
        }
        // Should be CLValueTuple1
        assertTrue(sv.getStoredValue().getValue() instanceof CLValueTuple1);
        CLValueTuple1 expected = new CLValueTuple1(new Unit<>(new CLValueBool(true)));
        try (CLValueEncoder clve = new CLValueEncoder()) {
            expected.encode(clve);
        }
        // assertEquals(expected, sv.getStoredValue().getValue());

        String reserializedJson = getPrettyJson(sv);

        LOGGER.debug("Serialized JSON: {}", reserializedJson);

        assertEquals(inputJson, reserializedJson);
    }

    @Test
    void test_tuple2_i32_string_clvalue_mapping() throws IOException, CLValueEncodeException, DynamicInstanceException,
            CLValueDecodeException, NoSuchTypeException {
        String inputJson = getPrettyJson(loadJsonFromFile("stored-value-samples/stored-value-tuple2-i32-string.json"));

        LOGGER.debug("Original JSON: {}", inputJson);

        StoredValueData sv = OBJECT_MAPPER.readValue(inputJson, StoredValueData.class);
        try (CLValueDecoder clvd = new CLValueDecoder(
                ((StoredValueCLValue) sv.getStoredValue()).getValue().getBytes())) {
            ((StoredValueCLValue) sv.getStoredValue()).getValue().decode(clvd);
        }
        // Should be CLValueTuple2
        assertTrue(sv.getStoredValue().getValue() instanceof CLValueTuple2);
        CLValueTuple2 expected = new CLValueTuple2(new Pair<>(new CLValueI32(1), new CLValueString("Hello, World!")));
        try (CLValueEncoder clve = new CLValueEncoder()) {
            expected.encode(clve);
        }
        // assertEquals(expected, sv.getStoredValue().getValue());

        String reserializedJson = getPrettyJson(sv);

        LOGGER.debug("Serialized JSON: {}", reserializedJson);

        assertEquals(inputJson, reserializedJson);
    }

    @Test
    void test_tuple3_u8_string_bool_clvalue_mapping() throws IOException, CLValueEncodeException,
            DynamicInstanceException, CLValueDecodeException, NoSuchTypeException {
        String inputJson = getPrettyJson(
                loadJsonFromFile("stored-value-samples/stored-value-tuple3-u8-string-bool.json"));

        LOGGER.debug("Original JSON: {}", inputJson);

        StoredValueData sv = OBJECT_MAPPER.readValue(inputJson, StoredValueData.class);
        try (CLValueDecoder clvd = new CLValueDecoder(
                ((StoredValueCLValue) sv.getStoredValue()).getValue().getBytes())) {
            ((StoredValueCLValue) sv.getStoredValue()).getValue().decode(clvd);
        }
        // Should be CLValueTuple3
        assertTrue(sv.getStoredValue().getValue() instanceof CLValueTuple3);
        CLValueTuple3 expected = new CLValueTuple3(
                new Triplet<>(new CLValueU8((byte) 1), new CLValueString("Hello, World!"), new CLValueBool(true)));
        try (CLValueEncoder clve = new CLValueEncoder()) {
            expected.encode(clve);
        }
        // assertEquals(expected, sv.getStoredValue().getValue());

        String reserializedJson = getPrettyJson(sv);

        LOGGER.debug("Serialized JSON: {}", reserializedJson);

        assertEquals(inputJson, reserializedJson);
    }

    @Test
    void test_bytearray_mapping() throws JsonMappingException, JsonProcessingException, IOException {
        String inputJson = getPrettyJson(loadJsonFromFile("stored-value-samples/stored-value-bytearray.json"));

        LOGGER.debug("Original JSON: {}", inputJson);

        StoredValueData sv = OBJECT_MAPPER.readValue(inputJson, StoredValueData.class);
        // Should be string
        // assertTrue(sv.getStoredValue().getValue() instanceof CLValueString);
        CLValueByteArray expected = new CLValueByteArray(new byte[] { 122, -50, 107, 117, -83, -99, 95, 64, -35, 5, 34,
                44, 108, -122, 69, -78, 28, -20, 71, 119, 98, 48, -34, 0, 111, -53, -39, 107, -38, 124, 73, -75 });
        try (CLValueEncoder clve = new CLValueEncoder()) {
            expected.encode(clve);
        }
        // assertEquals(expected, sv.getStoredValue().getValue());

        String reserializedJson = getPrettyJson(sv);

        LOGGER.debug("Serialized JSON: {}", reserializedJson);

        assertEquals(inputJson, reserializedJson);
    }

    /**
     * Loads test json from resources
     * 
     * @param filename
     * @return
     * @throws IOException
     */
    String loadJsonFromFile(String filename) throws IOException {
        String fileJson;
        try (InputStream is = getClass().getClassLoader().getResourceAsStream(filename)) {
            fileJson = new String(is.readAllBytes());
        }
        return fileJson;
    }

    /**
     * Prettifies json for assertion consistency
     * 
     * @param json json string to prettify
     * @return prettified json
     * @throws JsonMappingException
     * @throws JsonProcessingException
     */
    String getPrettyJson(String json) throws JsonMappingException, JsonProcessingException {
        Object jsonObject = OBJECT_MAPPER.readValue(json, Object.class);
        return getPrettyJson(jsonObject);
    }

    /**
     * Prettifies json for assertion consistency
     * 
     * @param jsonObject object to serialize and prettify
     * @return prettified json
     * @throws JsonMappingException
     * @throws JsonProcessingException
     */
    String getPrettyJson(Object jsonObject) throws JsonMappingException, JsonProcessingException {
        return OBJECT_MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(jsonObject);
    }
}
