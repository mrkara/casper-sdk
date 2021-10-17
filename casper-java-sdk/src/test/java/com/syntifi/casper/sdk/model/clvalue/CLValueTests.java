package com.syntifi.casper.sdk.model.clvalue;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.syntifi.casper.sdk.exception.DynamicInstanceException;
import com.syntifi.casper.sdk.exception.NoSuchTypeException;
import com.syntifi.casper.sdk.model.clvalue.cltype.CLTypeAny;
import com.syntifi.casper.sdk.model.clvalue.cltype.CLTypeData;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CLValueTests {
    private static final Logger LOGGER = LoggerFactory.getLogger(CLValueTests.class);

    @Test
    void test_instance_types_mapping() throws DynamicInstanceException, NoSuchTypeException {
        for (CLTypeData clTypeData : CLTypeData.values()) {
            // Warn if there are any missing implementation
            if (clTypeData.getClazz() == null) {
                LOGGER.warn("CLType {} does not have an implementation!", clTypeData);
                continue;
            }

            AbstractCLValue<?, ?> clValue = CLTypeData.createCLValueFromCLTypeData(clTypeData);

            // Correct instance type
            assertEquals(clTypeData.getClazz(), clValue.getClass());

            // Check if the correct CLType is set
            assertEquals(clTypeData, clValue.getClType().getClTypeData());
        }
    }

    @Test
    void test_get_type_by_serialization_tag() throws DynamicInstanceException, NoSuchTypeException {
        assertEquals(CLTypeData.ANY, CLTypeData.getTypeBySerializationTag(CLTypeData.ANY.getSerializationTag()));
    }

    @Test
    void test_create_cltype_from_cltype_data() throws DynamicInstanceException, NoSuchTypeException {
        assertTrue(CLTypeData.createCLTypeFromCLTypeData(CLTypeData.ANY) instanceof CLTypeAny);
    }

    @Test
    void test_create_clvalue_from_cl_type_name() throws DynamicInstanceException, NoSuchTypeException {
        assertTrue(CLTypeData.createCLValueFromCLTypeName(CLTypeData.ANY.getClTypeName()) instanceof CLValueAny);
    }

    @Test
    void test_create_clvalue_from_cl_type_name_throws_no_such_type_exception() {
        assertThrows(NoSuchTypeException.class, () -> CLTypeData.createCLValueFromCLTypeName("NE"));
    }

    @Test
    void test_get_class_by_name_throws_no_such_type_exception() {
        assertThrows(NoSuchTypeException.class, () -> CLTypeData.getClassByName("NE"));
    }

    @Test
    void test_get_cl_type_class_by_name_throws_no_such_type_exception() {
        assertThrows(NoSuchTypeException.class, () -> CLTypeData.getCLTypeClassByName("NE"));
    }

    @Test
    void test_get_type_by_name_throws_no_such_type_exception() {
        assertThrows(NoSuchTypeException.class, () -> CLTypeData.getTypeByName("NE"));
    }
}
