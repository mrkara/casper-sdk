package com.syntifi.casper.sdk.model.storedvalue;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.syntifi.casper.sdk.exception.CLValueDecodeException;
import com.syntifi.casper.sdk.exception.CLValueEncodeException;
import com.syntifi.casper.sdk.exception.DynamicInstanceException;
import com.syntifi.casper.sdk.exception.InvalidByteStringException;
import com.syntifi.casper.sdk.exception.NoSuchTypeException;
import com.syntifi.casper.sdk.model.AbstractJsonTests;
import com.syntifi.casper.sdk.model.account.Account;
import com.syntifi.casper.sdk.model.clvalue.CLValueAny;
import com.syntifi.casper.sdk.model.clvalue.CLValueBool;
import com.syntifi.casper.sdk.model.clvalue.CLValueByteArray;
import com.syntifi.casper.sdk.model.clvalue.CLValueFixedList;
import com.syntifi.casper.sdk.model.clvalue.CLValueI32;
import com.syntifi.casper.sdk.model.clvalue.CLValueI64;
import com.syntifi.casper.sdk.model.clvalue.CLValueKey;
import com.syntifi.casper.sdk.model.clvalue.CLValueList;
import com.syntifi.casper.sdk.model.clvalue.CLValueMap;
import com.syntifi.casper.sdk.model.clvalue.CLValueOption;
import com.syntifi.casper.sdk.model.clvalue.CLValuePublicKey;
import com.syntifi.casper.sdk.model.clvalue.CLValueResult;
import com.syntifi.casper.sdk.model.clvalue.CLValueString;
import com.syntifi.casper.sdk.model.clvalue.CLValueTuple1;
import com.syntifi.casper.sdk.model.clvalue.CLValueTuple2;
import com.syntifi.casper.sdk.model.clvalue.CLValueTuple3;
import com.syntifi.casper.sdk.model.clvalue.CLValueU128;
import com.syntifi.casper.sdk.model.clvalue.CLValueU256;
import com.syntifi.casper.sdk.model.clvalue.CLValueU32;
import com.syntifi.casper.sdk.model.clvalue.CLValueU512;
import com.syntifi.casper.sdk.model.clvalue.CLValueU64;
import com.syntifi.casper.sdk.model.clvalue.CLValueU8;
import com.syntifi.casper.sdk.model.clvalue.CLValueURef;
import com.syntifi.casper.sdk.model.clvalue.CLValueUnit;
import com.syntifi.casper.sdk.model.clvalue.encdec.CLValueEncoder;
import com.syntifi.casper.sdk.model.clvalue.encdec.StringByteHelper;
import com.syntifi.casper.sdk.model.contract.Contract;
import com.syntifi.casper.sdk.model.key.AlgorithmTag;
import com.syntifi.casper.sdk.model.key.Key;
import com.syntifi.casper.sdk.model.key.KeyTag;
import com.syntifi.casper.sdk.model.key.PublicKey;
import com.syntifi.casper.sdk.model.transfer.Transfer;
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
public class StoredValueTests extends AbstractJsonTests {

    private static final Logger LOGGER = LoggerFactory.getLogger(StoredValueTests.class);

    private static final String API_VERSION = "1.3.2";
    private static final String MERKLE_PROOF = "-- erased --";

    @Test
    void test_any_clvalue_mapping()
            throws IOException, CLValueDecodeException, DynamicInstanceException, NoSuchTypeException {
        String inputJson = getPrettyJson(loadJsonFromFile("stored-value-samples/stored-value-any.json"));

        LOGGER.debug("Original JSON: {}", inputJson);

        StoredValueData sv = OBJECT_MAPPER.readValue(inputJson, StoredValueData.class);
        // Should be CLValueAny
        assertTrue(sv.getStoredValue().getValue() instanceof CLValueAny);
        CLValueAny expectedClValue = new CLValueAny("Any Object Test");

        StoredValueData expected = new StoredValueData();
        expected.setApiVersion(API_VERSION);
        expected.setMerkleProof(MERKLE_PROOF);
        StoredValueCLValue svClValue = new StoredValueCLValue();
        svClValue.setValue(expectedClValue);
        expected.setStoredValue(svClValue);

        // This is done here to account for the missing encode call made by jackson
        // serializer
        try (CLValueEncoder clve = new CLValueEncoder()) {
            expectedClValue.encode(clve);
        }
        assertEquals(expected, sv);

        String expectedJson = getPrettyJson(expected);

        LOGGER.debug("Serialized JSON: {}", expectedJson);

        assertEquals(inputJson, expectedJson);
    }

    @Test
    void test_u8_clvalue_mapping()
            throws IOException, CLValueDecodeException, DynamicInstanceException, NoSuchTypeException {
        String inputJson = getPrettyJson(loadJsonFromFile("stored-value-samples/stored-value-u8.json"));

        LOGGER.debug("Original JSON: {}", inputJson);

        StoredValueData sv = OBJECT_MAPPER.readValue(inputJson, StoredValueData.class);
        // Should be CLValueU8
        assertTrue(sv.getStoredValue().getValue() instanceof CLValueU8);
        CLValueU8 expectedClValue = new CLValueU8((byte) 1);

        StoredValueData expected = new StoredValueData();
        expected.setApiVersion(API_VERSION);
        expected.setMerkleProof(MERKLE_PROOF);
        StoredValueCLValue svClValue = new StoredValueCLValue();
        svClValue.setValue(expectedClValue);
        expected.setStoredValue(svClValue);

        // This is done here to account for the missing encode call made by jackson
        // serializer
        try (CLValueEncoder clve = new CLValueEncoder()) {
            expectedClValue.encode(clve);
        }
        assertEquals(expected, sv);

        String expectedJson = getPrettyJson(expected);

        LOGGER.debug("Serialized JSON: {}", expectedJson);

        assertEquals(inputJson, expectedJson);
    }

    @Test
    void test_u32_clvalue_mapping() throws IOException, CLValueDecodeException, DynamicInstanceException,
            NoSuchTypeException, CLValueEncodeException {
        String inputJson = getPrettyJson(loadJsonFromFile("stored-value-samples/stored-value-u32.json"));

        LOGGER.debug("Original JSON: {}", inputJson);

        StoredValueData sv = OBJECT_MAPPER.readValue(inputJson, StoredValueData.class);
        // Should be CLValueU32
        assertTrue(sv.getStoredValue().getValue() instanceof CLValueU32);
        CLValueU32 expectedClValue = new CLValueU32(4294967295L);
        expectedClValue.setParsed(expectedClValue.getValue().toString());

        StoredValueData expected = new StoredValueData();
        expected.setApiVersion(API_VERSION);
        expected.setMerkleProof(MERKLE_PROOF);
        StoredValueCLValue svClValue = new StoredValueCLValue();
        svClValue.setValue(expectedClValue);
        expected.setStoredValue(svClValue);

        // This is done here to account for the missing encode call made by jackson
        // serializer
        try (CLValueEncoder clve = new CLValueEncoder()) {
            expectedClValue.encode(clve);
        }
        assertEquals(expected, sv);

        String expectedJson = getPrettyJson(expected);

        LOGGER.debug("Serialized JSON: {}", expectedJson);

        assertEquals(inputJson, expectedJson);
    }

    @Test
    void test_u64_clvalue_mapping() throws IOException, CLValueDecodeException, DynamicInstanceException,
            NoSuchTypeException, CLValueEncodeException {
        String inputJson = getPrettyJson(loadJsonFromFile("stored-value-samples/stored-value-u64.json"));

        LOGGER.debug("Original JSON: {}", inputJson);

        StoredValueData sv = OBJECT_MAPPER.readValue(inputJson, StoredValueData.class);
        // Should be CLValueU64
        assertTrue(sv.getStoredValue().getValue() instanceof CLValueU64);
        CLValueU64 expectedClValue = new CLValueU64(new BigInteger("18446744073709551615", 10));
        expectedClValue.setParsed(expectedClValue.getValue().toString(10));

        StoredValueData expected = new StoredValueData();
        expected.setApiVersion(API_VERSION);
        expected.setMerkleProof(MERKLE_PROOF);
        StoredValueCLValue svClValue = new StoredValueCLValue();
        svClValue.setValue(expectedClValue);
        expected.setStoredValue(svClValue);

        // This is done here to account for the missing encode call made by jackson
        // serializer
        try (CLValueEncoder clve = new CLValueEncoder()) {
            expectedClValue.encode(clve);
        }
        assertEquals(expected, sv);

        String expectedJson = getPrettyJson(expected);

        LOGGER.debug("Serialized JSON: {}", expectedJson);

        assertEquals(inputJson, expectedJson);
    }

    @Test
    void test_u128_clvalue_mapping() throws IOException, CLValueDecodeException, DynamicInstanceException,
            NoSuchTypeException, CLValueEncodeException {
        String inputJson = getPrettyJson(loadJsonFromFile("stored-value-samples/stored-value-u128.json"));

        LOGGER.debug("Original JSON: {}", inputJson);

        StoredValueData sv = OBJECT_MAPPER.readValue(inputJson, StoredValueData.class);
        // Should be CLValueU128
        assertTrue(sv.getStoredValue().getValue() instanceof CLValueU128);
        CLValueU128 expectedClValue = new CLValueU128(new BigInteger("340282366920938463463374607431768211455", 10));
        expectedClValue.setParsed(expectedClValue.getValue().toString(10));

        StoredValueData expected = new StoredValueData();
        expected.setApiVersion(API_VERSION);
        expected.setMerkleProof(MERKLE_PROOF);
        StoredValueCLValue svClValue = new StoredValueCLValue();
        svClValue.setValue(expectedClValue);
        expected.setStoredValue(svClValue);

        // This is done here to account for the missing encode call made by jackson
        // serializer
        try (CLValueEncoder clve = new CLValueEncoder()) {
            expectedClValue.encode(clve);
        }
        assertEquals(expected, sv);

        String expectedJson = getPrettyJson(expected);

        LOGGER.debug("Serialized JSON: {}", expectedJson);

        assertEquals(inputJson, expectedJson);
    }

    @Test
    void test_u256_clvalue_mapping() throws IOException, CLValueDecodeException, DynamicInstanceException,
            NoSuchTypeException, CLValueEncodeException {
        String inputJson = getPrettyJson(loadJsonFromFile("stored-value-samples/stored-value-u256.json"));

        LOGGER.debug("Original JSON: {}", inputJson);

        StoredValueData sv = OBJECT_MAPPER.readValue(inputJson, StoredValueData.class);
        // Should be CLValueU256
        assertTrue(sv.getStoredValue().getValue() instanceof CLValueU256);
        CLValueU256 expectedClValue = new CLValueU256(
                new BigInteger("115792089237316195423570985008687907853269984665640564039457584007913129639935", 10));
        expectedClValue.setParsed(expectedClValue.getValue().toString(10));

        StoredValueData expected = new StoredValueData();
        expected.setApiVersion(API_VERSION);
        expected.setMerkleProof(MERKLE_PROOF);
        StoredValueCLValue svClValue = new StoredValueCLValue();
        svClValue.setValue(expectedClValue);
        expected.setStoredValue(svClValue);

        // This is done here to account for the missing encode call made by jackson
        // serializer
        try (CLValueEncoder clve = new CLValueEncoder()) {
            expectedClValue.encode(clve);
        }
        assertEquals(expected, sv);

        String expectedJson = getPrettyJson(expected);

        LOGGER.debug("Serialized JSON: {}", expectedJson);

        assertEquals(inputJson, expectedJson);
    }

    @Test
    void test_i64_clvalue_mapping() throws IOException, CLValueDecodeException, DynamicInstanceException,
            NoSuchTypeException, CLValueEncodeException {
        String inputJson = getPrettyJson(loadJsonFromFile("stored-value-samples/stored-value-i64.json"));

        LOGGER.debug("Original JSON: {}", inputJson);

        StoredValueData sv = OBJECT_MAPPER.readValue(inputJson, StoredValueData.class);
        // Should be CLValueI64
        assertTrue(sv.getStoredValue().getValue() instanceof CLValueI64);
        CLValueI64 expectedClValue = new CLValueI64(9223372036854775807L);
        expectedClValue.setParsed(expectedClValue.getValue().toString());

        StoredValueData expected = new StoredValueData();
        expected.setApiVersion(API_VERSION);
        expected.setMerkleProof(MERKLE_PROOF);
        StoredValueCLValue svClValue = new StoredValueCLValue();
        svClValue.setValue(expectedClValue);
        expected.setStoredValue(svClValue);

        // This is done here to account for the missing encode call made by jackson
        // serializer
        try (CLValueEncoder clve = new CLValueEncoder()) {
            expectedClValue.encode(clve);
        }
        assertEquals(expected, sv);

        String expectedJson = getPrettyJson(expected);

        LOGGER.debug("Serialized JSON: {}", expectedJson);

        assertEquals(inputJson, expectedJson);
    }

    @Test
    void test_string_clvalue_mapping()
            throws IOException, CLValueDecodeException, DynamicInstanceException, NoSuchTypeException {
        String inputJson = getPrettyJson(loadJsonFromFile("stored-value-samples/stored-value-string.json"));

        LOGGER.debug("Original JSON: {}", inputJson);

        StoredValueData sv = OBJECT_MAPPER.readValue(inputJson, StoredValueData.class);
        // Should be string
        assertTrue(sv.getStoredValue().getValue() instanceof CLValueString);
        CLValueString expectedClValue = new CLValueString("the string");

        StoredValueData expected = new StoredValueData();
        expected.setApiVersion(API_VERSION);
        expected.setMerkleProof(MERKLE_PROOF);
        StoredValueCLValue svClValue = new StoredValueCLValue();
        svClValue.setValue(expectedClValue);
        expected.setStoredValue(svClValue);

        // This is done here to account for the missing encode call made by jackson
        // serializer
        try (CLValueEncoder clve = new CLValueEncoder()) {
            expectedClValue.encode(clve);
        }
        assertEquals(expected, sv);

        String expectedJson = getPrettyJson(expected);

        LOGGER.debug("Serialized JSON: {}", expectedJson);

        assertEquals(inputJson, expectedJson);
    }

    @Test
    void test_tuple1_bool_clvalue_mapping() throws IOException, CLValueEncodeException, DynamicInstanceException,
            CLValueDecodeException, NoSuchTypeException {
        String inputJson = getPrettyJson(loadJsonFromFile("stored-value-samples/stored-value-tuple1-bool.json"));

        LOGGER.debug("Original JSON: {}", inputJson);

        StoredValueData sv = OBJECT_MAPPER.readValue(inputJson, StoredValueData.class);
        // Should be CLValueTuple1
        assertTrue(sv.getStoredValue().getValue() instanceof CLValueTuple1);
        CLValueTuple1 expectedClValue = new CLValueTuple1(new Unit<>(new CLValueBool(true)));

        StoredValueData expected = new StoredValueData();
        expected.setApiVersion(API_VERSION);
        expected.setMerkleProof(MERKLE_PROOF);
        StoredValueCLValue svClValue = new StoredValueCLValue();
        svClValue.setValue(expectedClValue);
        expected.setStoredValue(svClValue);

        // This is done here to account for the missing encode call made by jackson
        // serializer
        try (CLValueEncoder clve = new CLValueEncoder()) {
            expectedClValue.encode(clve);
        }
        assertEquals(expected, sv);

        String expectedJson = getPrettyJson(expected);

        LOGGER.debug("Serialized JSON: {}", expectedJson);

        assertEquals(inputJson, expectedJson);
    }

    @Test
    void test_tuple2_i32_string_clvalue_mapping() throws IOException, CLValueEncodeException, DynamicInstanceException,
            CLValueDecodeException, NoSuchTypeException {
        String inputJson = getPrettyJson(loadJsonFromFile("stored-value-samples/stored-value-tuple2-i32-string.json"));

        LOGGER.debug("Original JSON: {}", inputJson);

        StoredValueData sv = OBJECT_MAPPER.readValue(inputJson, StoredValueData.class);
        // Should be CLValueTuple2
        assertTrue(sv.getStoredValue().getValue() instanceof CLValueTuple2);
        CLValueTuple2 expectedClValue = new CLValueTuple2(
                new Pair<>(new CLValueI32(1), new CLValueString("Hello, World!")));

        StoredValueData expected = new StoredValueData();
        expected.setApiVersion(API_VERSION);
        expected.setMerkleProof(MERKLE_PROOF);
        StoredValueCLValue svClValue = new StoredValueCLValue();
        svClValue.setValue(expectedClValue);
        expected.setStoredValue(svClValue);

        // This is done here to account for the missing encode call made by jackson
        // serializer
        try (CLValueEncoder clve = new CLValueEncoder()) {
            expectedClValue.encode(clve);
        }
        assertEquals(expected, sv);

        String expectedJson = getPrettyJson(expected);

        LOGGER.debug("Serialized JSON: {}", expectedJson);

        assertEquals(inputJson, expectedJson);
    }

    @Test
    void test_tuple3_u8_string_bool_clvalue_mapping() throws IOException, CLValueEncodeException,
            DynamicInstanceException, CLValueDecodeException, NoSuchTypeException {
        String inputJson = getPrettyJson(
                loadJsonFromFile("stored-value-samples/stored-value-tuple3-u8-string-bool.json"));

        LOGGER.debug("Original JSON: {}", inputJson);

        StoredValueData sv = OBJECT_MAPPER.readValue(inputJson, StoredValueData.class);
        // Should be CLValueTuple3
        assertTrue(sv.getStoredValue().getValue() instanceof CLValueTuple3);
        CLValueTuple3 expectedClValue = new CLValueTuple3(
                new Triplet<>(new CLValueU8((byte) 1), new CLValueString("Hello, World!"), new CLValueBool(true)));

        StoredValueData expected = new StoredValueData();
        expected.setApiVersion(API_VERSION);
        expected.setMerkleProof(MERKLE_PROOF);
        StoredValueCLValue svClValue = new StoredValueCLValue();
        svClValue.setValue(expectedClValue);
        expected.setStoredValue(svClValue);

        // This is done here to account for the missing encode call made by jackson
        // serializer
        try (CLValueEncoder clve = new CLValueEncoder()) {
            expectedClValue.encode(clve);
        }
        assertEquals(expected, sv);

        String expectedJson = getPrettyJson(expected);

        LOGGER.debug("Serialized JSON: {}", expectedJson);

        assertEquals(inputJson, expectedJson);
    }

    @Test
    void test_tuple3_i32_string_bool_clvalue_mapping() throws IOException, CLValueEncodeException,
            DynamicInstanceException, CLValueDecodeException, NoSuchTypeException {
        String inputJson = getPrettyJson(
                loadJsonFromFile("stored-value-samples/stored-value-tuple3-i32-string-bool.json"));

        LOGGER.debug("Original JSON: {}", inputJson);

        StoredValueData sv = OBJECT_MAPPER.readValue(inputJson, StoredValueData.class);
        // Should be CLValueTuple3
        assertTrue(sv.getStoredValue().getValue() instanceof CLValueTuple3);
        CLValueTuple3 expectedClValue = new CLValueTuple3(
                new Triplet<>(new CLValueI32(1), new CLValueString("Hello, World!"), new CLValueBool(true)));

        StoredValueData expected = new StoredValueData();
        expected.setApiVersion(API_VERSION);
        expected.setMerkleProof(MERKLE_PROOF);
        StoredValueCLValue svClValue = new StoredValueCLValue();
        svClValue.setValue(expectedClValue);
        expected.setStoredValue(svClValue);

        // This is done here to account for the missing encode call made by jackson
        // serializer
        try (CLValueEncoder clve = new CLValueEncoder()) {
            expectedClValue.encode(clve);
        }
        assertEquals(expected, sv);

        String expectedJson = getPrettyJson(expected);

        LOGGER.debug("Serialized JSON: {}", expectedJson);

        assertEquals(inputJson, expectedJson);
    }

    @Test
    void test_tuple3_tuple1_bool_string_bool_clvalue_mapping() throws IOException, CLValueEncodeException,
            DynamicInstanceException, CLValueDecodeException, NoSuchTypeException {
        String inputJson = getPrettyJson(
                loadJsonFromFile("stored-value-samples/stored-value-tuple3-tuple1-bool-string-bool.json"));

        LOGGER.debug("Original JSON: {}", inputJson);

        StoredValueData sv = OBJECT_MAPPER.readValue(inputJson, StoredValueData.class);
        // Should be CLValueTuple3
        assertTrue(sv.getStoredValue().getValue() instanceof CLValueTuple3);
        CLValueTuple3 expectedClValue = new CLValueTuple3(new Triplet<CLValueTuple1, CLValueString, CLValueBool>(
                new CLValueTuple1(new Unit<CLValueBool>(new CLValueBool(true))), new CLValueString("Hello, World!"),
                new CLValueBool(true)));

        StoredValueData expected = new StoredValueData();
        expected.setApiVersion(API_VERSION);
        expected.setMerkleProof(MERKLE_PROOF);
        StoredValueCLValue svClValue = new StoredValueCLValue();
        svClValue.setValue(expectedClValue);
        expected.setStoredValue(svClValue);

        // This is done here to account for the missing encode call made by jackson
        // serializer
        try (CLValueEncoder clve = new CLValueEncoder()) {
            expectedClValue.encode(clve);
        }
        assertEquals(expected, sv);

        String expectedJson = getPrettyJson(expected);

        LOGGER.debug("Serialized JSON: {}", expectedJson);

        assertEquals(inputJson, expectedJson);
    }

    @Test
    void test_tuple3_tuple2_tuple1_u512_u512_tuple1_string_tuple1_bool_clvalue_mapping() throws IOException,
            CLValueEncodeException, DynamicInstanceException, CLValueDecodeException, NoSuchTypeException {
        String inputJson = getPrettyJson(loadJsonFromFile(
                "stored-value-samples/stored-value-tuple3-tuple2-tuple1-u512-u512-tuple1-string-tuple1-bool.json"));

        LOGGER.debug("Original JSON: {}", inputJson);

        StoredValueData sv = OBJECT_MAPPER.readValue(inputJson, StoredValueData.class);
        // Should be CLValueTuple3
        assertTrue(sv.getStoredValue().getValue() instanceof CLValueTuple3);
        CLValueTuple3 expectedClValue = new CLValueTuple3(new Triplet<CLValueTuple2, CLValueTuple1, CLValueTuple1>(
                new CLValueTuple2(new Pair<CLValueTuple1, CLValueU512>(
                        new CLValueTuple1(new Unit<CLValueU512>(new CLValueU512(BigInteger.valueOf(2)))),
                        new CLValueU512(BigInteger.valueOf(2)))),
                new CLValueTuple1(new Unit<CLValueString>(new CLValueString("Hello, World!"))),
                new CLValueTuple1(new Unit<CLValueBool>(new CLValueBool(true)))));

        StoredValueData expected = new StoredValueData();
        expected.setApiVersion(API_VERSION);
        expected.setMerkleProof(MERKLE_PROOF);
        StoredValueCLValue svClValue = new StoredValueCLValue();
        svClValue.setValue(expectedClValue);
        expected.setStoredValue(svClValue);

        // This is done here to account for the missing encode call made by jackson
        // serializer
        try (CLValueEncoder clve = new CLValueEncoder()) {
            expectedClValue.encode(clve);
        }
        assertEquals(expected, sv);

        String expectedJson = getPrettyJson(expected);

        LOGGER.debug("Serialized JSON: {}", expectedJson);

        assertEquals(inputJson, expectedJson);
    }

    @Test
    void test_tuple3_tuple1_u512_string_bool_clvalue_mapping() throws IOException, CLValueEncodeException,
            DynamicInstanceException, CLValueDecodeException, NoSuchTypeException {
        String inputJson = getPrettyJson(
                loadJsonFromFile("stored-value-samples/stored-value-tuple3-tuple1-u512-string-bool.json"));

        LOGGER.debug("Original JSON: {}", inputJson);

        StoredValueData sv = OBJECT_MAPPER.readValue(inputJson, StoredValueData.class);
        // Should be CLValueTuple3
        assertTrue(sv.getStoredValue().getValue() instanceof CLValueTuple3);
        CLValueTuple3 expectedClValue = new CLValueTuple3(new Triplet<CLValueTuple1, CLValueString, CLValueBool>(
                new CLValueTuple1(new Unit<CLValueU512>(new CLValueU512(new BigInteger("123456789101112131415", 10)))),
                new CLValueString("Hello, World!"), new CLValueBool(true)));

        StoredValueData expected = new StoredValueData();
        expected.setApiVersion(API_VERSION);
        expected.setMerkleProof(MERKLE_PROOF);
        StoredValueCLValue svClValue = new StoredValueCLValue();
        svClValue.setValue(expectedClValue);
        expected.setStoredValue(svClValue);

        // This is done here to account for the missing encode call made by jackson
        // serializer
        try (CLValueEncoder clve = new CLValueEncoder()) {
            expectedClValue.encode(clve);
        }
        assertEquals(expected, sv);

        String expectedJson = getPrettyJson(expected);
        String serializedExpected = getPrettyJson(expected);

        LOGGER.debug("Serialized JSON: {}", expectedJson);
        LOGGER.debug("Serialized Expected JSON: {}", serializedExpected);

        assertEquals(inputJson, expectedJson);
        // assertEquals(serializedExpected, expectedJson);
    }

    @Test
    void test_list_i32_clvalue_mapping() throws IOException, CLValueEncodeException, DynamicInstanceException,
            NoSuchTypeException, CLValueDecodeException {
        String inputJson = getPrettyJson(loadJsonFromFile("stored-value-samples/stored-value-list-i32.json"));

        LOGGER.debug("Original JSON: {}", inputJson);

        StoredValueData sv = OBJECT_MAPPER.readValue(inputJson, StoredValueData.class);
        // Should be CLValueList
        assertTrue(sv.getStoredValue().getValue() instanceof CLValueList);
        CLValueList expectedClValue = new CLValueList(
                Arrays.asList(new CLValueI32(1), new CLValueI32(2), new CLValueI32(3)));

        StoredValueData expected = new StoredValueData();
        expected.setApiVersion(API_VERSION);
        expected.setMerkleProof(MERKLE_PROOF);
        StoredValueCLValue svClValue = new StoredValueCLValue();
        svClValue.setValue(expectedClValue);
        expected.setStoredValue(svClValue);

        // This is done here to account for the missing encode call made by jackson
        // serializer
        try (CLValueEncoder clve = new CLValueEncoder()) {
            expectedClValue.encode(clve);
        }
        assertEquals(expected, sv);

        String expectedJson = getPrettyJson(expected);

        LOGGER.debug("Serialized JSON: {}", expectedJson);

        assertEquals(inputJson, expectedJson);
    }

    @Test
    void test_fixedlist_i32_clvalue_mapping() throws IOException, CLValueEncodeException, DynamicInstanceException,
            NoSuchTypeException, CLValueDecodeException {
        String inputJson = getPrettyJson(loadJsonFromFile("stored-value-samples/stored-value-fixedlist-i32.json"));

        LOGGER.debug("Original JSON: {}", inputJson);

        StoredValueData sv = OBJECT_MAPPER.readValue(inputJson, StoredValueData.class);
        // Should be CLValueList
        assertTrue(sv.getStoredValue().getValue() instanceof CLValueFixedList);
        CLValueFixedList expectedClValue = new CLValueFixedList(
                Arrays.asList(new CLValueI32(1), new CLValueI32(2), new CLValueI32(3)));

        StoredValueData expected = new StoredValueData();
        expected.setApiVersion(API_VERSION);
        expected.setMerkleProof(MERKLE_PROOF);
        StoredValueCLValue svClValue = new StoredValueCLValue();
        svClValue.setValue(expectedClValue);
        expected.setStoredValue(svClValue);

        // This is done here to account for the missing encode call made by jackson
        // serializer
        try (CLValueEncoder clve = new CLValueEncoder()) {
            expectedClValue.encode(clve);
        }
        assertEquals(expected, sv);

        String expectedJson = getPrettyJson(expected);

        LOGGER.debug("Serialized JSON: {}", expectedJson);

        assertEquals(inputJson, expectedJson);
    }

    @Test
    void test_fixedlist_tuple1_i32_clvalue_mapping() throws IOException, CLValueEncodeException,
            DynamicInstanceException, NoSuchTypeException, CLValueDecodeException {
        String inputJson = getPrettyJson(
                loadJsonFromFile("stored-value-samples/stored-value-fixedlist-tuple1-i32.json"));

        LOGGER.debug("Original JSON: {}", inputJson);

        StoredValueData sv = OBJECT_MAPPER.readValue(inputJson, StoredValueData.class);
        // Should be CLValueList
        assertTrue(sv.getStoredValue().getValue() instanceof CLValueFixedList);
        CLValueFixedList expectedClValue = new CLValueFixedList(Arrays.asList(
                new CLValueTuple1(new Unit<>(new CLValueI32(1))), new CLValueTuple1(new Unit<>(new CLValueI32(2))),
                new CLValueTuple1(new Unit<>(new CLValueI32(3)))));

        StoredValueData expected = new StoredValueData();
        expected.setApiVersion(API_VERSION);
        expected.setMerkleProof(MERKLE_PROOF);
        StoredValueCLValue svClValue = new StoredValueCLValue();
        svClValue.setValue(expectedClValue);
        expected.setStoredValue(svClValue);

        // This is done here to account for the missing encode call made by jackson
        // serializer
        try (CLValueEncoder clve = new CLValueEncoder()) {
            expectedClValue.encode(clve);
        }
        assertEquals(expected, sv);

        String expectedJson = getPrettyJson(expected);

        LOGGER.debug("Serialized JSON: {}", expectedJson);

        assertEquals(inputJson, expectedJson);
    }

    @Test
    void test_fixedlist_i32_odd_byte_length_clvalue_mapping() throws IOException, CLValueEncodeException,
            DynamicInstanceException, NoSuchTypeException, CLValueDecodeException {
        String inputJson = getPrettyJson(
                loadJsonFromFile("stored-value-samples/stored-value-fixedlist-i32-odd-byte-length.json"));

        LOGGER.debug("Original JSON: {}", inputJson);

        Throwable exception = assertThrows(JsonMappingException.class,
                () -> OBJECT_MAPPER.readValue(inputJson, StoredValueData.class));
        assertEquals(InvalidByteStringException.class, exception.getCause().getClass());
    }

    @Test
    void test_fixedlist_i32_wrong_byte_length_clvalue_mapping() throws IOException, CLValueEncodeException,
            DynamicInstanceException, NoSuchTypeException, CLValueDecodeException {
        String inputJson = getPrettyJson(
                loadJsonFromFile("stored-value-samples/stored-value-fixedlist-i32-wrong-byte-length.json"));

        LOGGER.debug("Original JSON: {}", inputJson);

        Throwable exception = assertThrows(JsonMappingException.class,
                () -> OBJECT_MAPPER.readValue(inputJson, StoredValueData.class));
        assertEquals(CLValueDecodeException.class, exception.getCause().getClass());
    }

    @Test
    void test_list_tuple_i32_i32_clvalue_mapping() throws IOException, CLValueEncodeException, DynamicInstanceException,
            NoSuchTypeException, CLValueDecodeException {
        String inputJson = getPrettyJson(loadJsonFromFile("stored-value-samples/stored-value-list-tuple-i32-i32.json"));

        LOGGER.debug("Original JSON: {}", inputJson);

        StoredValueData sv = OBJECT_MAPPER.readValue(inputJson, StoredValueData.class);
        // Should be CLValueList
        assertTrue(sv.getStoredValue().getValue() instanceof CLValueList);
        CLValueList expectedClValue = new CLValueList(
                Arrays.asList(new CLValueTuple2(new Pair<>(new CLValueI32(1), new CLValueI32(1))),
                        new CLValueTuple2(new Pair<>(new CLValueI32(2), new CLValueI32(2))),
                        new CLValueTuple2(new Pair<>(new CLValueI32(3), new CLValueI32(3)))));

        StoredValueData expected = new StoredValueData();
        expected.setApiVersion(API_VERSION);
        expected.setMerkleProof(MERKLE_PROOF);
        StoredValueCLValue svClValue = new StoredValueCLValue();
        svClValue.setValue(expectedClValue);
        expected.setStoredValue(svClValue);

        // This is done here to account for the missing encode call made by jackson
        // serializer
        try (CLValueEncoder clve = new CLValueEncoder()) {
            expectedClValue.encode(clve);
        }
        assertEquals(expected, sv);

        String expectedJson = getPrettyJson(expected);

        LOGGER.debug("Serialized JSON: {}", expectedJson);

        assertEquals(inputJson, expectedJson);
    }

    @Test
    void test_map_string_i32_clvalue_mapping() throws IOException, CLValueEncodeException, DynamicInstanceException,
            NoSuchTypeException, CLValueDecodeException {
        String inputJson = getPrettyJson(loadJsonFromFile("stored-value-samples/stored-value-map-string-i32.json"));

        LOGGER.debug("Original JSON: {}", inputJson);

        StoredValueData sv = OBJECT_MAPPER.readValue(inputJson, StoredValueData.class);
        // Should be CLValueMap
        assertTrue(sv.getStoredValue().getValue() instanceof CLValueMap);
        Map<CLValueString, CLValueI32> map = new LinkedHashMap<>();
        map.put(new CLValueString("ABC"), new CLValueI32(10));
        CLValueMap expectedClValue = new CLValueMap(map);

        StoredValueData expected = new StoredValueData();
        expected.setApiVersion(API_VERSION);
        expected.setMerkleProof(MERKLE_PROOF);
        StoredValueCLValue svClValue = new StoredValueCLValue();
        svClValue.setValue(expectedClValue);
        expected.setStoredValue(svClValue);

        // This is done here to account for the missing encode call made by jackson
        // serializer
        try (CLValueEncoder clve = new CLValueEncoder()) {
            expectedClValue.encode(clve);
        }
        assertEquals(expected, sv);

        String expectedJson = getPrettyJson(expected);

        LOGGER.debug("Serialized JSON: {}", expectedJson);

        assertEquals(inputJson, expectedJson);
    }

    @Test
    void test_map_string_tuple1_i32_clvalue_mapping() throws IOException, CLValueEncodeException,
            DynamicInstanceException, NoSuchTypeException, CLValueDecodeException {
        String inputJson = getPrettyJson(
                loadJsonFromFile("stored-value-samples/stored-value-map-string-tuple1-i32.json"));

        LOGGER.debug("Original JSON: {}", inputJson);

        StoredValueData sv = OBJECT_MAPPER.readValue(inputJson, StoredValueData.class);
        // Should be CLValueMap
        assertTrue(sv.getStoredValue().getValue() instanceof CLValueMap);
        Map<CLValueString, CLValueTuple1> map = new LinkedHashMap<>();
        map.put(new CLValueString("ABC"), new CLValueTuple1(new Unit<CLValueI32>(new CLValueI32(10))));
        CLValueMap expectedClValue = new CLValueMap(map);

        StoredValueData expected = new StoredValueData();
        expected.setApiVersion(API_VERSION);
        expected.setMerkleProof(MERKLE_PROOF);
        StoredValueCLValue svClValue = new StoredValueCLValue();
        svClValue.setValue(expectedClValue);
        expected.setStoredValue(svClValue);

        // This is done here to account for the missing encode call made by jackson
        // serializer
        try (CLValueEncoder clve = new CLValueEncoder()) {
            expectedClValue.encode(clve);
        }
        assertEquals(expected, sv);

        String expectedJson = getPrettyJson(expected);

        LOGGER.debug("Serialized JSON: {}", expectedJson);

        assertEquals(inputJson, expectedJson);
    }

    @Test
    void test_result_i32_string_clvalue_mapping() throws IOException, CLValueEncodeException, DynamicInstanceException,
            NoSuchTypeException, CLValueDecodeException {
        String inputJson = getPrettyJson(loadJsonFromFile("stored-value-samples/stored-value-result-i32-string.json"));

        LOGGER.debug("Original JSON: {}", inputJson);

        StoredValueData sv = OBJECT_MAPPER.readValue(inputJson, StoredValueData.class);
        // Should be CLValueResult
        assertTrue(sv.getStoredValue().getValue() instanceof CLValueResult);
        CLValueResult expectedClValue = new CLValueResult(new CLValueI32(10), new CLValueString("Uh oh"));

        StoredValueData expected = new StoredValueData();
        expected.setApiVersion(API_VERSION);
        expected.setMerkleProof(MERKLE_PROOF);
        StoredValueCLValue svClValue = new StoredValueCLValue();
        svClValue.setValue(expectedClValue);
        expected.setStoredValue(svClValue);

        // This is done here to account for the missing encode call made by jackson
        // serializer
        try (CLValueEncoder clve = new CLValueEncoder()) {
            expectedClValue.encode(clve);
        }
        assertEquals(expected, sv);

        String expectedJson = getPrettyJson(expected);

        LOGGER.debug("Serialized JSON: {}", expectedJson);

        assertEquals(inputJson, expectedJson);
    }

    @Test
    void test_result_i32_tuple1_string_clvalue_mapping() throws IOException, CLValueEncodeException,
            DynamicInstanceException, NoSuchTypeException, CLValueDecodeException {
        String inputJson = getPrettyJson(
                loadJsonFromFile("stored-value-samples/stored-value-result-i32-tuple1-string.json"));

        LOGGER.debug("Original JSON: {}", inputJson);

        StoredValueData sv = OBJECT_MAPPER.readValue(inputJson, StoredValueData.class);
        // Should be CLValueResult
        assertTrue(sv.getStoredValue().getValue() instanceof CLValueResult);
        CLValueResult expectedClValue = new CLValueResult(new CLValueI32(10),
                new CLValueTuple1(new Unit<>(new CLValueString("Uh oh"))));

        StoredValueData expected = new StoredValueData();
        expected.setApiVersion(API_VERSION);
        expected.setMerkleProof(MERKLE_PROOF);
        StoredValueCLValue svClValue = new StoredValueCLValue();
        svClValue.setValue(expectedClValue);
        expected.setStoredValue(svClValue);

        // This is done here to account for the missing encode call made by jackson
        // serializer
        try (CLValueEncoder clve = new CLValueEncoder()) {
            expectedClValue.encode(clve);
        }
        assertEquals(expected, sv);

        String expectedJson = getPrettyJson(expected);

        LOGGER.debug("Serialized JSON: {}", expectedJson);

        assertEquals(inputJson, expectedJson);
    }

    @Test
    void test_option_empty_clvalue_mapping() throws IOException, CLValueEncodeException, DynamicInstanceException,
            NoSuchTypeException, CLValueDecodeException {
        String inputJson = getPrettyJson(loadJsonFromFile("stored-value-samples/stored-value-option-empty.json"));

        LOGGER.debug("Original JSON: {}", inputJson);

        StoredValueData sv = OBJECT_MAPPER.readValue(inputJson, StoredValueData.class);
        // Should be CLValueOption
        assertTrue(sv.getStoredValue().getValue() instanceof CLValueOption);
        CLValueOption expectedClValue = new CLValueOption(Optional.of(new CLValueBool(null)));

        StoredValueData expected = new StoredValueData();
        expected.setApiVersion(API_VERSION);
        expected.setMerkleProof(MERKLE_PROOF);
        StoredValueCLValue svClValue = new StoredValueCLValue();
        svClValue.setValue(expectedClValue);
        expected.setStoredValue(svClValue);

        // This is done here to account for the missing encode call made by jackson
        // serializer
        try (CLValueEncoder clve = new CLValueEncoder()) {
            expectedClValue.encode(clve);
        }
        assertEquals(expected, sv);

        String expectedJson = getPrettyJson(expected);

        LOGGER.debug("Serialized JSON: {}", expectedJson);

        assertEquals(inputJson, expectedJson);
    }

    @Test
    void test_option_bool_clvalue_mapping() throws IOException, CLValueEncodeException, DynamicInstanceException,
            NoSuchTypeException, CLValueDecodeException {
        String inputJson = getPrettyJson(loadJsonFromFile("stored-value-samples/stored-value-option-bool.json"));

        LOGGER.debug("Original JSON: {}", inputJson);

        StoredValueData sv = OBJECT_MAPPER.readValue(inputJson, StoredValueData.class);
        // Should be CLValueOption
        assertTrue(sv.getStoredValue().getValue() instanceof CLValueOption);
        CLValueOption expectedClValue = new CLValueOption(Optional.of(new CLValueBool(true)));

        StoredValueData expected = new StoredValueData();
        expected.setApiVersion(API_VERSION);
        expected.setMerkleProof(MERKLE_PROOF);
        StoredValueCLValue svClValue = new StoredValueCLValue();
        svClValue.setValue(expectedClValue);
        expected.setStoredValue(svClValue);

        // This is done here to account for the missing encode call made by jackson
        // serializer
        try (CLValueEncoder clve = new CLValueEncoder()) {
            expectedClValue.encode(clve);
        }
        assertEquals(expected, sv);

        String expectedJson = getPrettyJson(expected);

        LOGGER.debug("Serialized JSON: {}", expectedJson);

        assertEquals(inputJson, expectedJson);
    }

    @Test
    void test_option_i32_clvalue_mapping() throws IOException, CLValueEncodeException, DynamicInstanceException,
            NoSuchTypeException, CLValueDecodeException {
        String inputJson = getPrettyJson(loadJsonFromFile("stored-value-samples/stored-value-option-i32.json"));

        LOGGER.debug("Original JSON: {}", inputJson);

        StoredValueData sv = OBJECT_MAPPER.readValue(inputJson, StoredValueData.class);
        // Should be CLValueOption
        assertTrue(sv.getStoredValue().getValue() instanceof CLValueOption);
        CLValueOption expectedClValue = new CLValueOption(Optional.of(new CLValueI32(10)));

        StoredValueData expected = new StoredValueData();
        expected.setApiVersion(API_VERSION);
        expected.setMerkleProof(MERKLE_PROOF);
        StoredValueCLValue svClValue = new StoredValueCLValue();
        svClValue.setValue(expectedClValue);
        expected.setStoredValue(svClValue);

        // This is done here to account for the missing encode call made by jackson
        // serializer
        try (CLValueEncoder clve = new CLValueEncoder()) {
            expectedClValue.encode(clve);
        }
        assertEquals(expected, sv);

        String expectedJson = getPrettyJson(expected);

        LOGGER.debug("Serialized JSON: {}", expectedJson);

        assertEquals(inputJson, expectedJson);
    }

    @Test
    void test_option_tuple2_clvalue_mapping() throws IOException, CLValueEncodeException, DynamicInstanceException,
            NoSuchTypeException, CLValueDecodeException {
        String inputJson = getPrettyJson(
                loadJsonFromFile("stored-value-samples/stored-value-option-tuple2-i32-string.json"));

        LOGGER.debug("Original JSON: {}", inputJson);

        StoredValueData sv = OBJECT_MAPPER.readValue(inputJson, StoredValueData.class);
        // Should be CLValueOption
        assertTrue(sv.getStoredValue().getValue() instanceof CLValueOption);
        CLValueOption expectedClValue = new CLValueOption(
                Optional.of(new CLValueTuple2(new Pair<>(new CLValueI32(1), new CLValueString("Hello, World!")))));

        StoredValueData expected = new StoredValueData();
        expected.setApiVersion(API_VERSION);
        expected.setMerkleProof(MERKLE_PROOF);
        StoredValueCLValue svClValue = new StoredValueCLValue();
        svClValue.setValue(expectedClValue);
        expected.setStoredValue(svClValue);

        // This is done here to account for the missing encode call made by jackson
        // serializer
        try (CLValueEncoder clve = new CLValueEncoder()) {
            expectedClValue.encode(clve);
        }
        assertEquals(expected, sv);

        String expectedJson = getPrettyJson(expected);

        LOGGER.debug("Serialized JSON: {}", expectedJson);

        assertEquals(inputJson, expectedJson);
    }

    @Test
    void test_unit_clvalue_mapping() throws IOException, CLValueEncodeException, DynamicInstanceException,
            CLValueDecodeException, NoSuchTypeException {
        String inputJson = getPrettyJson(loadJsonFromFile("stored-value-samples/stored-value-unit.json"));

        LOGGER.debug("Original JSON: {}", inputJson);

        StoredValueData sv = OBJECT_MAPPER.readValue(inputJson, StoredValueData.class);
        // Should be CLValueUnit
        assertTrue(sv.getStoredValue().getValue() instanceof CLValueUnit);
        CLValueUnit expectedClValue = new CLValueUnit();

        StoredValueData expected = new StoredValueData();
        expected.setApiVersion(API_VERSION);
        expected.setMerkleProof(MERKLE_PROOF);
        StoredValueCLValue svClValue = new StoredValueCLValue();
        svClValue.setValue(expectedClValue);
        expected.setStoredValue(svClValue);

        // This is done here to account for the missing encode call made by jackson
        // serializer
        try (CLValueEncoder clve = new CLValueEncoder()) {
            expectedClValue.encode(clve);
        }
        assertEquals(expected, sv);

        String expectedJson = getPrettyJson(expected);

        LOGGER.debug("Serialized JSON: {}", expectedJson);

        assertEquals(inputJson, expectedJson);
    }

    @Test
    void test_uref_clvalue_mapping()
            throws IOException, CLValueDecodeException, DynamicInstanceException, NoSuchTypeException {
        String inputJson = getPrettyJson(loadJsonFromFile("stored-value-samples/stored-value-uref.json"));

        LOGGER.debug("Original JSON: {}", inputJson);

        StoredValueData sv = OBJECT_MAPPER.readValue(inputJson, StoredValueData.class);
        // Should be CLValuURef
        assertTrue(sv.getStoredValue().getValue() instanceof CLValueURef);
        CLValueURef expectedClValue = new CLValueURef(new URef(
                StringByteHelper
                        .hexStringToByteArray("2a2a2a2a2a2a2a2a2a2a2a2a2a2a2a2a2a2a2a2a2a2a2a2a2a2a2a2a2a2a2a2a"),
                URefAccessRight.READ_ADD_WRITE));
        expectedClValue.setParsed("the uref");

        StoredValueData expected = new StoredValueData();
        expected.setApiVersion(API_VERSION);
        expected.setMerkleProof(MERKLE_PROOF);
        StoredValueCLValue svClValue = new StoredValueCLValue();
        svClValue.setValue(expectedClValue);
        expected.setStoredValue(svClValue);

        // This is done here to account for the missing encode call made by jackson
        // serializer
        try (CLValueEncoder clve = new CLValueEncoder()) {
            expectedClValue.encode(clve);
        }
        // TODO: FIX EQUALS
        assertEquals(expected, sv);

        String expectedJson = getPrettyJson(expected);

        LOGGER.debug("Serialized JSON: {}", expectedJson);

        assertEquals(inputJson, expectedJson);
    }

    @Test
    void test_key_account_clvalue_mapping()
            throws IOException, CLValueDecodeException, DynamicInstanceException, NoSuchTypeException {
        String inputJson = getPrettyJson(loadJsonFromFile("stored-value-samples/stored-value-key-account.json"));

        LOGGER.debug("Original JSON: {}", inputJson);

        StoredValueData sv = OBJECT_MAPPER.readValue(inputJson, StoredValueData.class);
        // Should be CLValueKey
        assertTrue(sv.getStoredValue().getValue() instanceof CLValueKey);
        Key key = new Key();
        key.setTag(KeyTag.ACCOUNT);
        key.setKey(StringByteHelper
                .hexStringToByteArray("2a2a2a2a2a2a2a2a2a2a2a2a2a2a2a2a2a2a2a2a2a2a2a2a2a2a2a2a2a2a2a2a"));
        CLValueKey expectedClValue = new CLValueKey(key);

        StoredValueData expected = new StoredValueData();
        expected.setApiVersion(API_VERSION);
        expected.setMerkleProof(MERKLE_PROOF);
        StoredValueCLValue svClValue = new StoredValueCLValue();
        svClValue.setValue(expectedClValue);
        expected.setStoredValue(svClValue);

        // This is done here to account for the missing encode call made by jackson
        // serializer
        try (CLValueEncoder clve = new CLValueEncoder()) {
            expectedClValue.encode(clve);
        }
        assertEquals(expected, sv);

        String expectedJson = getPrettyJson(expected);

        LOGGER.debug("Serialized JSON: {}", expectedJson);

        assertEquals(inputJson, expectedJson);
    }

    @Test
    void test_key_hash_clvalue_mapping()
            throws IOException, CLValueDecodeException, DynamicInstanceException, NoSuchTypeException {
        String inputJson = getPrettyJson(loadJsonFromFile("stored-value-samples/stored-value-key-hash.json"));

        LOGGER.debug("Original JSON: {}", inputJson);

        StoredValueData sv = OBJECT_MAPPER.readValue(inputJson, StoredValueData.class);
        // Should be CLValueKey
        assertTrue(sv.getStoredValue().getValue() instanceof CLValueKey);
        Key key = new Key();
        key.setTag(KeyTag.HASH);
        key.setKey(StringByteHelper
                .hexStringToByteArray("2a2a2a2a2a2a2a2a2a2a2a2a2a2a2a2a2a2a2a2a2a2a2a2a2a2a2a2a2a2a2a2a"));
        CLValueKey expectedClValue = new CLValueKey(key);

        StoredValueData expected = new StoredValueData();
        expected.setApiVersion(API_VERSION);
        expected.setMerkleProof(MERKLE_PROOF);
        StoredValueCLValue svClValue = new StoredValueCLValue();
        svClValue.setValue(expectedClValue);
        expected.setStoredValue(svClValue);

        // This is done here to account for the missing encode call made by jackson
        // serializer
        try (CLValueEncoder clve = new CLValueEncoder()) {
            expectedClValue.encode(clve);
        }
        assertEquals(expected, sv);

        String expectedJson = getPrettyJson(expected);

        LOGGER.debug("Serialized JSON: {}", expectedJson);

        assertEquals(inputJson, expectedJson);
    }

    @Test
    void test_public_key_clvalue_mapping()
            throws IOException, CLValueDecodeException, DynamicInstanceException, NoSuchTypeException {
        String inputJson = getPrettyJson(loadJsonFromFile("stored-value-samples/stored-value-publickey.json"));

        LOGGER.debug("Original JSON: {}", inputJson);

        StoredValueData sv = OBJECT_MAPPER.readValue(inputJson, StoredValueData.class);
        // Should be CLValuePublicKey
        assertTrue(sv.getStoredValue().getValue() instanceof CLValuePublicKey);
        PublicKey pk = new PublicKey();
        pk.setTag(AlgorithmTag.ED25519);
        pk.setKey(StringByteHelper
                .hexStringToByteArray("2a2a2a2a2a2a2a2a2a2a2a2a2a2a2a2a2a2a2a2a2a2a2a2a2a2a2a2a2a2a2a2a"));
        CLValuePublicKey expectedClValue = new CLValuePublicKey(pk);

        StoredValueData expected = new StoredValueData();
        expected.setApiVersion(API_VERSION);
        expected.setMerkleProof(MERKLE_PROOF);
        StoredValueCLValue svClValue = new StoredValueCLValue();
        svClValue.setValue(expectedClValue);
        expected.setStoredValue(svClValue);

        // This is done here to account for the missing encode call made by jackson
        // serializer
        try (CLValueEncoder clve = new CLValueEncoder()) {
            expectedClValue.encode(clve);
        }
        assertEquals(expected, sv);

        String expectedJson = getPrettyJson(expected);

        LOGGER.debug("Serialized JSON: {}", expectedJson);

        assertEquals(inputJson, expectedJson);
    }

    @Test
    void test_byte_array() throws IOException, CLValueEncodeException, DynamicInstanceException, CLValueDecodeException,
            NoSuchTypeException {
        String inputJson = getPrettyJson(loadJsonFromFile("stored-value-samples/stored-value-bytearray.json"));

        LOGGER.debug("Original JSON: {}", inputJson);

        StoredValueData sv = OBJECT_MAPPER.readValue(inputJson, StoredValueData.class);
        // Should be CLValueByteArray
        assertTrue(sv.getStoredValue().getValue() instanceof CLValueByteArray);
        CLValueByteArray expectedClValue = new CLValueByteArray(
                new byte[] { 122, -50, 107, 117, -83, -99, 95, 64, -35, 5, 34, 44, 108, -122, 69, -78, 28, -20, 71, 119,
                        98, 48, -34, 0, 111, -53, -39, 107, -38, 124, 73, -75 });

        StoredValueData expected = new StoredValueData();
        expected.setApiVersion(API_VERSION);
        expected.setMerkleProof(MERKLE_PROOF);
        StoredValueCLValue svClValue = new StoredValueCLValue();
        svClValue.setValue(expectedClValue);
        expected.setStoredValue(svClValue);

        // This is done here to account for the missing encode call made by jackson
        // serializer
        try (CLValueEncoder clve = new CLValueEncoder()) {
            expectedClValue.encode(clve);
        }

        sv.getStoredValue().getValue().equals(expected);

        assertEquals(expected, sv);

        String expectedJson = getPrettyJson(expected);

        LOGGER.debug("Serialized JSON: {}", expectedJson);

        assertEquals(inputJson, expectedJson);
    }

    @Test
    void test_account_mapping() throws JsonMappingException, JsonProcessingException, IOException {
        /*
         * curl -X POST -H 'Content-Type: application/json' -d
         * '{"jsonrpc":"2.0","id":"1","method":"state_get_item",
         * "params":{"state_root_hash":
         * "09ac52260e370ed56bba5283a79b03d524b4f420bf964d7e629b0819dd1be09d", "key":
         * "account-hash-e1431ecb9f20f2a6e6571886b1e2f9dec49ebc6b2d3d640a53530abafba9bfa1"}
         * }' http://195.201.142.76:7777/rpc | json_pp
         */
        String inputJson = getPrettyJson(loadJsonFromFile("stored-value-samples/stored-value-account.json"));

        LOGGER.debug("Original JSON: {}", inputJson);

        StoredValueData sv = OBJECT_MAPPER.readValue(inputJson, StoredValueData.class);

        assertTrue(sv.getStoredValue().getValue() instanceof Account);

        String expectedJson = getPrettyJson(sv);

        LOGGER.debug("Serialized JSON: {}", expectedJson);

        assertEquals(inputJson, expectedJson);
    }

    @Test
    void test_contract_mapping() throws JsonMappingException, JsonProcessingException, IOException {
        /*
         * curl -X POST -H 'Content-Type: application/json' -d
         * '{"jsonrpc":"2.0","id":"1","method":"state_get_item",
         * "params":{"state_root_hash":
         * "09ac52260e370ed56bba5283a79b03d524b4f420bf964d7e629b0819dd1be09d", "key":
         * "hash-d2469afeb99130f0be7c9ce230a84149e6d756e306ef8cf5b8a49d5182e41676"} }'
         * http://195.201.142.76:7777/rpc | json_pp
         */
        String inputJson = getPrettyJson(loadJsonFromFile("stored-value-samples/stored-value-contract.json"));

        LOGGER.debug("Original JSON: {}", inputJson);

        StoredValueData sv = OBJECT_MAPPER.readValue(inputJson, StoredValueData.class);

        assertTrue(sv.getStoredValue().getValue() instanceof Contract);

        String expectedJson = getPrettyJson(sv);

        LOGGER.debug("Serialized JSON: {}", expectedJson);

        assertEquals(inputJson, expectedJson);
    }

    @Test
    void test_transfer_mapping() throws JsonMappingException, JsonProcessingException, IOException {
        String inputJson = getPrettyJson(loadJsonFromFile("stored-value-samples/stored-value-transfer.json"));

        LOGGER.debug("Original JSON: {}", inputJson);

        StoredValueData sv = OBJECT_MAPPER.readValue(inputJson, StoredValueData.class);

        assertTrue(sv.getStoredValue().getValue() instanceof Transfer);

        String expectedJson = getPrettyJson(sv);

        LOGGER.debug("Serialized JSON: {}", expectedJson);

        assertEquals(inputJson, expectedJson);
    }
}