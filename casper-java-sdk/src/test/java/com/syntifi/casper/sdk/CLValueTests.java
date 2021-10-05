package com.syntifi.casper.sdk;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.syntifi.casper.sdk.exception.DynamicInstanceException;
import com.syntifi.casper.sdk.exception.NoSuchTypeException;
import com.syntifi.casper.sdk.model.clvalue.AbstractCLValue;
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

}
