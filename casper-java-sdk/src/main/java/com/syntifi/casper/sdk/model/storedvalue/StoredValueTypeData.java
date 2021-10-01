package com.syntifi.casper.sdk.model.storedvalue;

import com.syntifi.casper.sdk.exception.NoSuchTypeException;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Stored Value type data and class mapping
 * 
 * @author Alexandre Carvalho
 * @author Andre Bertolace
 * @see StoredValueCLValue
 * @see StoredValueContract
 * @since 0.0.1
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum StoredValueTypeData {
    STORED_VALUE_CLVALUE("CLValue", StoredValueCLValue.class),
    STORED_VALUE_CONTRACT("Contract", StoredValueContract.class);

    private final String name;
    private final Class<?> clazz;

    /**
     * Retrieve Transform implementation class from Transform name 
     * 
     * @param name
     * @return
     * @throws NoSuchCLTypeException
     */
    public static Class<?> getClassByName(String name) throws NoSuchTypeException {
        for (StoredValueTypeData t : values()) {
            if (t.name.equals(name)) {
                return t.getClazz();
            }
        }
        throw new NoSuchTypeException();
    }
}