package com.syntifi.casper.sdk.model.storedvalue;

import com.syntifi.casper.sdk.exception.NoSuchTypeException;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;


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