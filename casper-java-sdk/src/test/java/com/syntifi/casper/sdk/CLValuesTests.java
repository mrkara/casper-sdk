package com.syntifi.casper.sdk;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
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
import com.syntifi.casper.sdk.model.clvalue.CLValueList;
import com.syntifi.casper.sdk.model.clvalue.CLValueMap;
import com.syntifi.casper.sdk.model.clvalue.CLValueOption;
import com.syntifi.casper.sdk.model.clvalue.CLValuePublicKey;
import com.syntifi.casper.sdk.model.clvalue.CLValueResult;
import com.syntifi.casper.sdk.model.clvalue.CLValueString;
import com.syntifi.casper.sdk.model.clvalue.CLValueTuple1;
import com.syntifi.casper.sdk.model.clvalue.CLValueTuple2;
import com.syntifi.casper.sdk.model.clvalue.CLValueTuple3;
import com.syntifi.casper.sdk.model.clvalue.CLValueU512;
import com.syntifi.casper.sdk.model.clvalue.CLValueU8;
import com.syntifi.casper.sdk.model.clvalue.CLValueURef;
import com.syntifi.casper.sdk.model.clvalue.CLValueUnit;
import com.syntifi.casper.sdk.model.clvalue.Result;
import com.syntifi.casper.sdk.model.clvalue.encdec.CLValueDecoder;
import com.syntifi.casper.sdk.model.clvalue.encdec.CLValueEncoder;
import com.syntifi.casper.sdk.model.clvalue.encdec.StringByteHelper;
import com.syntifi.casper.sdk.model.contract.Contract;
import com.syntifi.casper.sdk.model.deploy.Deploy;
import com.syntifi.casper.sdk.model.deploy.DeployData;
import com.syntifi.casper.sdk.model.deploy.JsonExecutionResult;
import com.syntifi.casper.sdk.model.key.Algorithm;
import com.syntifi.casper.sdk.model.key.PublicKey;
import com.syntifi.casper.sdk.model.storedvalue.StoredValueCLValue;
import com.syntifi.casper.sdk.model.storedvalue.StoredValueData;
import com.syntifi.casper.sdk.model.uref.URef;
import com.syntifi.casper.sdk.model.uref.URefAccessRight;

import org.javatuples.Pair;
import org.javatuples.Triplet;
import org.javatuples.Unit;
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
public class CLValuesTests {

    private static final Logger LOGGER = LoggerFactory.getLogger(CLValuesTests.class);

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    // @Test
    // void test_instance_types() throws DynamicInstanceException,
    // NoSuchTypeException {
    // for (CLTypeData clTypeData : CLTypeData.values()) {
    // // Warn if there are any missing implementation
    // if (clTypeData.getClazz() == null) {
    // LOGGER.warn("CLType {} does not have an implementation!", clTypeData);
    // continue;
    // }

    // CLValue<?, ?> clValue = CLTypeData.createCLValueFromCLTypeData(clTypeData);

    // // Correct instance type
    // assertEquals(clTypeData.getClazz(), clValue.getClass());

    // // Check if the correct CLType is set
    // assertEquals(clTypeData, clValue.getClType().getClTypeData());
    // }
    // }

    @Test
    void test_u8_clvalue_mapping()
            throws IOException, CLValueDecodeException, DynamicInstanceException, NoSuchTypeException {
        String inputJson = getPrettyJson(loadJsonFromFile("stored-value-samples/stored-value-u8.json"));

        LOGGER.debug("Original JSON: {}", inputJson);

        StoredValueData sv = OBJECT_MAPPER.readValue(inputJson, StoredValueData.class);
        try (CLValueDecoder clvd = new CLValueDecoder(
                ((StoredValueCLValue) sv.getStoredValue()).getValue().getBytes())) {
            ((StoredValueCLValue) sv.getStoredValue()).getValue().decode(clvd);
        }
        // Should be CLValueU8
        assertTrue(sv.getStoredValue().getValue() instanceof CLValueU8);
        CLValueU8 expected = new CLValueU8((byte) 1);
        expected.setParsed("");
        ;
        try (CLValueEncoder clve = new CLValueEncoder()) {
            expected.encode(clve);
        }
        assertEquals(expected, sv.getStoredValue().getValue());

        String reserializedJson = getPrettyJson(sv);

        LOGGER.debug("Serialized JSON: {}", reserializedJson);

        assertEquals(inputJson, reserializedJson);
    }

    @Test
    void test_string_clvalue_mapping()
            throws IOException, CLValueDecodeException, DynamicInstanceException, NoSuchTypeException {
        String inputJson = getPrettyJson(loadJsonFromFile("stored-value-samples/stored-value-string.json"));

        LOGGER.debug("Original JSON: {}", inputJson);

        StoredValueData sv = OBJECT_MAPPER.readValue(inputJson, StoredValueData.class);
        try (CLValueDecoder clvd = new CLValueDecoder(
                ((StoredValueCLValue) sv.getStoredValue()).getValue().getBytes())) {
            ((StoredValueCLValue) sv.getStoredValue()).getValue().decode(clvd);
        }
        // Should be string
        assertTrue(sv.getStoredValue().getValue() instanceof CLValueString);
        CLValueString expected = new CLValueString("the string");
        try (CLValueEncoder clve = new CLValueEncoder()) {
            expected.encode(clve);
        }
        assertEquals(expected, sv.getStoredValue().getValue());

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
        assertEquals(expected, sv.getStoredValue().getValue());

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
        assertEquals(expected, sv.getStoredValue().getValue());

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
        assertEquals(expected, sv.getStoredValue().getValue());

        String reserializedJson = getPrettyJson(sv);

        LOGGER.debug("Serialized JSON: {}", reserializedJson);

        assertEquals(inputJson, reserializedJson);
    }

    @Test
    void test_tuple3_i32_string_bool_clvalue_mapping() throws IOException, CLValueEncodeException,
            DynamicInstanceException, CLValueDecodeException, NoSuchTypeException {
        String inputJson = getPrettyJson(
                loadJsonFromFile("stored-value-samples/stored-value-tuple3-i32-string-bool.json"));

        LOGGER.debug("Original JSON: {}", inputJson);

        StoredValueData sv = OBJECT_MAPPER.readValue(inputJson, StoredValueData.class);
        try (CLValueDecoder clvd = new CLValueDecoder(
                ((StoredValueCLValue) sv.getStoredValue()).getValue().getBytes())) {
            ((StoredValueCLValue) sv.getStoredValue()).getValue().decode(clvd);
        }
        // Should be CLValueTuple3
        assertTrue(sv.getStoredValue().getValue() instanceof CLValueTuple3);
        CLValueTuple3 expected = new CLValueTuple3(
                new Triplet<>(new CLValueI32(1), new CLValueString("Hello, World!"), new CLValueBool(true)));
        try (CLValueEncoder clve = new CLValueEncoder()) {
            expected.encode(clve);
        }
        assertEquals(expected, sv.getStoredValue().getValue());

        String reserializedJson = getPrettyJson(sv);

        LOGGER.debug("Serialized JSON: {}", reserializedJson);

        assertEquals(inputJson, reserializedJson);
    }

    @Test
    void test_tuple3_tuple1_bool_string_bool_clvalue_mapping() throws IOException, CLValueEncodeException,
            DynamicInstanceException, CLValueDecodeException, NoSuchTypeException {
        String inputJson = getPrettyJson(
                loadJsonFromFile("stored-value-samples/stored-value-tuple3-tuple1-bool-string-bool.json"));

        LOGGER.debug("Original JSON: {}", inputJson);

        StoredValueData sv = OBJECT_MAPPER.readValue(inputJson, StoredValueData.class);
        try (CLValueDecoder clvd = new CLValueDecoder(
                ((StoredValueCLValue) sv.getStoredValue()).getValue().getBytes())) {
            ((StoredValueCLValue) sv.getStoredValue()).getValue().decode(clvd);
        }
        // Should be CLValueTuple3
        assertTrue(sv.getStoredValue().getValue() instanceof CLValueTuple3);
        CLValueTuple3 expected = new CLValueTuple3(new Triplet<CLValueTuple1, CLValueString, CLValueBool>(
                new CLValueTuple1(new Unit<CLValueBool>(new CLValueBool(true))), new CLValueString("Hello, World!"),
                new CLValueBool(true)));
        try (CLValueEncoder clve = new CLValueEncoder()) {
            expected.encode(clve);
        }
        assertEquals(expected, sv.getStoredValue().getValue());

        String reserializedJson = getPrettyJson(sv);

        LOGGER.debug("Serialized JSON: {}", reserializedJson);

        assertEquals(inputJson, reserializedJson);
    }

    @Test
    void test_tuple3_tuple1_u512_string_bool_clvalue_mapping() throws IOException, CLValueEncodeException,
            DynamicInstanceException, CLValueDecodeException, NoSuchTypeException {
        String inputJson = getPrettyJson(
                loadJsonFromFile("stored-value-samples/stored-value-tuple3-tuple1-u512-string-bool.json"));

        LOGGER.debug("Original JSON: {}", inputJson);

        StoredValueData sv = OBJECT_MAPPER.readValue(inputJson, StoredValueData.class);
        try (CLValueDecoder clvd = new CLValueDecoder(
                ((StoredValueCLValue) sv.getStoredValue()).getValue().getBytes())) {
            ((StoredValueCLValue) sv.getStoredValue()).getValue().decode(clvd);
        }
        // Should be CLValueTuple3
        assertTrue(sv.getStoredValue().getValue() instanceof CLValueTuple3);
        CLValueTuple3 expected = new CLValueTuple3(new Triplet<CLValueTuple1, CLValueString, CLValueBool>(
                new CLValueTuple1(new Unit<CLValueU512>(new CLValueU512(new BigInteger("123456789101112131415", 10)))),
                new CLValueString("Hello, World!"), new CLValueBool(true)));
        try (CLValueEncoder clve = new CLValueEncoder()) {
            expected.encode(clve);
        }
        assertEquals(expected, sv.getStoredValue().getValue());

        String reserializedJson = getPrettyJson(sv);
        String serializedExpected = getPrettyJson(expected);

        LOGGER.debug("Serialized JSON: {}", reserializedJson);
        LOGGER.debug("Serialized Expected JSON: {}", serializedExpected);

        assertEquals(inputJson, reserializedJson);
        // assertEquals(serializedExpected, reserializedJson);
    }

    @Test
    void test_list_i32_clvalue_mapping()
            throws IOException, CLValueEncodeException, DynamicInstanceException, NoSuchTypeException {
        String inputJson = getPrettyJson(loadJsonFromFile("stored-value-samples/stored-value-list-i32.json"));

        LOGGER.debug("Original JSON: {}", inputJson);

        StoredValueData sv = OBJECT_MAPPER.readValue(inputJson, StoredValueData.class);
        // Should be CLValueList
        assertTrue(sv.getStoredValue().getValue() instanceof CLValueList);
        CLValueList expected = new CLValueList(Arrays.asList(new CLValueI32(1), new CLValueI32(2), new CLValueI32(3)));
        try (CLValueEncoder clve = new CLValueEncoder()) {
            expected.encode(clve);
        }
        assertEquals(expected, sv.getStoredValue().getValue());

        String reserializedJson = getPrettyJson(sv);

        LOGGER.debug("Serialized JSON: {}", reserializedJson);

        assertEquals(inputJson, reserializedJson);
    }

    @Test
    void test_map_string_i32_clvalue_mapping()
            throws IOException, CLValueEncodeException, DynamicInstanceException, NoSuchTypeException {
        String inputJson = getPrettyJson(loadJsonFromFile("stored-value-samples/stored-value-map-string-i32.json"));

        LOGGER.debug("Original JSON: {}", inputJson);

        StoredValueData sv = OBJECT_MAPPER.readValue(inputJson, StoredValueData.class);
        // Should be CLValueMap
        assertTrue(sv.getStoredValue().getValue() instanceof CLValueMap);
        Map<CLValueString, CLValueI32> map = new LinkedHashMap<>();
        map.put(new CLValueString("ABC"), new CLValueI32(10));
        CLValueMap expected = new CLValueMap(map);
        try (CLValueEncoder clve = new CLValueEncoder()) {
            expected.encode(clve);
        }
        assertEquals(expected, sv.getStoredValue().getValue());

        String reserializedJson = getPrettyJson(sv);

        LOGGER.debug("Serialized JSON: {}", reserializedJson);

        assertEquals(inputJson, reserializedJson);
    }

    @Test
    void test_result_i32_string_clvalue_mapping()
            throws IOException, CLValueEncodeException, DynamicInstanceException, NoSuchTypeException {
        String inputJson = getPrettyJson(loadJsonFromFile("stored-value-samples/stored-value-result-i32-string.json"));

        LOGGER.debug("Original JSON: {}", inputJson);

        StoredValueData sv = OBJECT_MAPPER.readValue(inputJson, StoredValueData.class);
        // Should be CLValueResult
        assertTrue(sv.getStoredValue().getValue() instanceof CLValueResult);
        Result result = new Result();
        result.setOk(new CLValueI32(10));
        result.setErr(new CLValueString("Uh oh"));
        CLValueResult expected = new CLValueResult(result);
        try (CLValueEncoder clve = new CLValueEncoder()) {
            expected.encode(clve);
        }
        assertEquals(expected, sv.getStoredValue().getValue());

        String reserializedJson = getPrettyJson(sv);

        LOGGER.debug("Serialized JSON: {}", reserializedJson);

        assertEquals(inputJson, reserializedJson);
    }

    @Test
    void test_option_empty_clvalue_mapping()
            throws IOException, CLValueEncodeException, DynamicInstanceException, NoSuchTypeException {
        String inputJson = getPrettyJson(loadJsonFromFile("stored-value-samples/stored-value-option-empty.json"));

        LOGGER.debug("Original JSON: {}", inputJson);

        StoredValueData sv = OBJECT_MAPPER.readValue(inputJson, StoredValueData.class);
        // Should be CLValueOption
        assertTrue(sv.getStoredValue().getValue() instanceof CLValueOption);
        CLValueOption expected = new CLValueOption(Optional.ofNullable(null));
        try (CLValueEncoder clve = new CLValueEncoder()) {
            expected.encode(clve);
        }
        assertEquals(expected, sv.getStoredValue().getValue());

        String reserializedJson = getPrettyJson(sv);

        LOGGER.debug("Serialized JSON: {}", reserializedJson);

        assertEquals(inputJson, reserializedJson);
    }

    @Test
    void test_option_bool_clvalue_mapping()
            throws IOException, CLValueEncodeException, DynamicInstanceException, NoSuchTypeException {
        String inputJson = getPrettyJson(loadJsonFromFile("stored-value-samples/stored-value-option-bool.json"));

        LOGGER.debug("Original JSON: {}", inputJson);

        StoredValueData sv = OBJECT_MAPPER.readValue(inputJson, StoredValueData.class);
        // Should be CLValueOption
        assertTrue(sv.getStoredValue().getValue() instanceof CLValueOption);
        CLValueOption expected = new CLValueOption(Optional.of(new CLValueBool(true)));
        try (CLValueEncoder clve = new CLValueEncoder()) {
            expected.encode(clve);
        }
        assertEquals(expected, sv.getStoredValue().getValue());

        String reserializedJson = getPrettyJson(sv);

        LOGGER.debug("Serialized JSON: {}", reserializedJson);

        assertEquals(inputJson, reserializedJson);
    }

    @Test
    void test_option_i32_clvalue_mapping()
            throws IOException, CLValueEncodeException, DynamicInstanceException, NoSuchTypeException {
        String inputJson = getPrettyJson(loadJsonFromFile("stored-value-samples/stored-value-option-i32.json"));

        LOGGER.debug("Original JSON: {}", inputJson);

        StoredValueData sv = OBJECT_MAPPER.readValue(inputJson, StoredValueData.class);
        // Should be CLValueOption
        assertTrue(sv.getStoredValue().getValue() instanceof CLValueOption);
        CLValueOption expected = new CLValueOption(Optional.of(new CLValueI32(10)));
        try (CLValueEncoder clve = new CLValueEncoder()) {
            expected.encode(clve);
        }
        assertEquals(expected, sv.getStoredValue().getValue());

        String reserializedJson = getPrettyJson(sv);

        LOGGER.debug("Serialized JSON: {}", reserializedJson);

        assertEquals(inputJson, reserializedJson);
    }

    @Test
    void test_unit_clvalue_mapping() throws IOException, CLValueEncodeException, DynamicInstanceException {
        String inputJson = getPrettyJson(loadJsonFromFile("stored-value-samples/stored-value-unit.json"));

        LOGGER.debug("Original JSON: {}", inputJson);

        StoredValueData sv = OBJECT_MAPPER.readValue(inputJson, StoredValueData.class);
        // Should be CLValueUnit
        assertTrue(sv.getStoredValue().getValue() instanceof CLValueUnit);
        CLValueUnit expected = new CLValueUnit();
        try (CLValueEncoder clve = new CLValueEncoder()) {
            expected.encode(clve);
        }
        assertEquals(expected, sv.getStoredValue().getValue());

        String reserializedJson = getPrettyJson(sv);

        LOGGER.debug("Serialized JSON: {}", reserializedJson);

        assertEquals(inputJson, reserializedJson);
    }

    @Test
    void test_uref_clvalue_mapping() throws IOException {
        String inputJson = getPrettyJson(loadJsonFromFile("stored-value-samples/stored-value-uref.json"));

        LOGGER.debug("Original JSON: {}", inputJson);

        StoredValueData sv = OBJECT_MAPPER.readValue(inputJson, StoredValueData.class);
        // Should be CLValuURef
        assertTrue(sv.getStoredValue().getValue() instanceof CLValueURef);
        CLValueURef expected = new CLValueURef(new URef(
                StringByteHelper
                        .hexStringToByteArray("2a2a2a2a2a2a2a2a2a2a2a2a2a2a2a2a2a2a2a2a2a2a2a2a2a2a2a2a2a2a2a2a"),
                URefAccessRight.READ_ADD_WRITE));
        try (CLValueEncoder clve = new CLValueEncoder()) {
            expected.encode(clve);
        }
        assertEquals(expected, sv.getStoredValue().getValue());

        String reserializedJson = getPrettyJson(sv);

        LOGGER.debug("Serialized JSON: {}", reserializedJson);

        assertEquals(inputJson, reserializedJson);
    }

    @Test
    void test_public_key_clvalue_mapping() throws IOException {
        String inputJson = getPrettyJson(loadJsonFromFile("stored-value-samples/stored-value-publickey.json"));

        LOGGER.debug("Original JSON: {}", inputJson);

        StoredValueData sv = OBJECT_MAPPER.readValue(inputJson, StoredValueData.class);
        // Should be CLValuePublicKey
        assertTrue(sv.getStoredValue().getValue() instanceof CLValuePublicKey);
        PublicKey pk = new PublicKey();
        pk.setAlgorithm(Algorithm.ED25519);
        pk.setKey(StringByteHelper
                .hexStringToByteArray("2a2a2a2a2a2a2a2a2a2a2a2a2a2a2a2a2a2a2a2a2a2a2a2a2a2a2a2a2a2a2a2a"));
        CLValuePublicKey expected = new CLValuePublicKey(pk);
        try (CLValueEncoder clve = new CLValueEncoder()) {
            expected.encode(clve);
        }
        assertEquals(expected, sv.getStoredValue().getValue());

        String reserializedJson = getPrettyJson(sv);

        LOGGER.debug("Serialized JSON: {}", reserializedJson);

        assertEquals(inputJson, reserializedJson);
    }

    @Test
    void test_byte_array() throws IOException, CLValueEncodeException, DynamicInstanceException {
        String inputJson = getPrettyJson(loadJsonFromFile("stored-value-samples/stored-value-bytearray.json"));

        LOGGER.debug("Original JSON: {}", inputJson);

        StoredValueData sv = OBJECT_MAPPER.readValue(inputJson, StoredValueData.class);
        // Should be CLValueByteArray
        assertTrue(sv.getStoredValue().getValue() instanceof CLValueByteArray);
        CLValueByteArray expected = new CLValueByteArray(new byte[] { 122, -50, 107, 117, -83, -99, 95, 64, -35, 5, 34,
                44, 108, -122, 69, -78, 28, -20, 71, 119, 98, 48, -34, 0, 111, -53, -39, 107, -38, 124, 73, -75 });
        try (CLValueEncoder clve = new CLValueEncoder()) {
            expected.encode(clve);
        }

        sv.getStoredValue().getValue().equals(expected);

        assertEquals(expected, sv.getStoredValue().getValue());

        String reserializedJson = getPrettyJson(sv);

        LOGGER.debug("Serialized JSON: {}", reserializedJson);

        assertEquals(inputJson, reserializedJson);
    }

    // @Test
    // void test_contract_mapping() throws JsonMappingException, JsonProcessingException, IOException {
    //     String inputJson = getPrettyJson(loadJsonFromFile("stored-value-samples/stored-value-contract.json"));

    //     LOGGER.debug("Original JSON: {}", inputJson);

    //     StoredValueData sv = OBJECT_MAPPER.readValue(inputJson, StoredValueData.class);

    //     assertTrue(sv.getStoredValue().getValue() instanceof Contract);

    //     String reserializedJson = getPrettyJson(sv);

    //     LOGGER.debug("Serialized JSON: {}", reserializedJson);

    //     assertEquals(inputJson, reserializedJson);

    // }

    // @Test
    // void test_deploy_mapping_1() throws JsonMappingException, JsonProcessingException, IOException {
    //     String inputJson = getPrettyJson(loadJsonFromFile("stored-value-samples/stored-value-deploy-v1.json"));

    //     LOGGER.debug("Original JSON: {}", inputJson);

    //     DeployData dd = OBJECT_MAPPER.readValue(inputJson, DeployData.class);
    //     // try (CLValueEncoder clve = new CLValueEncoder()) {
    //     // dd.getDeploy().encode(clve);
    //     // }

    //     assertTrue(dd.getDeploy() instanceof Deploy);
    //     assertTrue(dd.getExecutionResults().get(0) instanceof JsonExecutionResult);

    //     String reserializedJson = getPrettyJson(dd);

    //     LOGGER.debug("Serialized JSON: {}", reserializedJson);

    //     assertEquals(inputJson, reserializedJson);
    // }

    // @Test
    // void test_deploy_mapping_2() throws JsonMappingException, JsonProcessingException, IOException {
    //     String inputJson = getPrettyJson(loadJsonFromFile("stored-value-samples/stored-value-deploy-v2.json"));

    //     LOGGER.debug("Original JSON: {}", inputJson);

    //     DeployData dd = OBJECT_MAPPER.readValue(inputJson, DeployData.class);

    //     assertTrue(dd.getDeploy() instanceof Deploy);

    //     String reserializedJson = getPrettyJson(dd);

    //     LOGGER.debug("Serialized JSON: {}", reserializedJson);

    //     assertEquals(inputJson, reserializedJson);
    // }

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