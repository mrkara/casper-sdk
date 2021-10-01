package com.syntifi.casper.sdk.model.clvalue.type;

import java.lang.reflect.InvocationTargetException;

import com.syntifi.casper.sdk.exception.DynamicInstanceException;
import com.syntifi.casper.sdk.exception.NoSuchTypeException;
import com.syntifi.casper.sdk.model.clvalue.CLValue;
import com.syntifi.casper.sdk.model.clvalue.CLValueAny;
import com.syntifi.casper.sdk.model.clvalue.CLValueBool;
import com.syntifi.casper.sdk.model.clvalue.CLValueByteArray;
import com.syntifi.casper.sdk.model.clvalue.CLValueI32;
import com.syntifi.casper.sdk.model.clvalue.CLValueI64;
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
import com.syntifi.casper.sdk.model.clvalue.StoredValue;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Casper CLType definitions and type mappings
 * 
 * All types must be listed and mapped here.
 * 
 * @author Alexandre Carvalho
 * @author Andre Bertolace
 * @see StoredValue
 * @since 0.0.1
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum CLTypeData {
    BOOL(CLType.BOOL, 0x0, CLValueBool.class, CLTypeBool.class), 
    I32(CLType.I32, 0x1, CLValueI32.class, CLTypeI32.class),
    I64(CLType.I64, 0x2, CLValueI64.class, CLTypeI64.class), 
    U8(CLType.U8, 0x3, CLValueU8.class, CLTypeU8.class), 
    U32(CLType.U32, 0x4, CLValueU32.class, CLTypeU32.class),
    U64(CLType.U64, 0x5, CLValueU64.class, CLTypeU64.class), 
    U128(CLType.U128, 0x6, CLValueU128.class, CLTypeU128.class),
    U256(CLType.U256, 0x7, CLValueU256.class, CLTypeU256.class), 
    U512(CLType.U512, 0x8, CLValueU512.class, CLTypeU512.class),
    UNIT(CLType.UNIT, 0x9, CLValueUnit.class, CLTypeUnit.class), 
    STRING(CLType.STRING, 0x10, CLValueString.class, CLTypeString.class),
    UREF(CLType.UREF, 0x11, CLValueURef.class, CLTypeURef.class),
    KEY(CLType.KEY, 0x12, null, null),
    OPTION(CLType.OPTION, 0x13, CLValueOption.class, CLTypeOption.class), 
    LIST(CLType.LIST, 0x14, CLValueList.class, CLTypeList.class),
    FIXED_LIST(CLType.FIXED_LIST, 0x15, null, null), 
    RESULT(CLType.RESULT, 0x16, CLValueResult.class, CLTypeResult.class),
    MAP(CLType.MAP, 0x17, CLValueMap.class, CLTypeBool.class), 
    TUPLE1(CLType.TUPLE1, 0x18, CLValueTuple1.class, CLTypeTuple1.class),
    TUPLE2(CLType.TUPLE2, 0x19, CLValueTuple2.class, CLTypeTuple2.class), 
    TUPLE3(CLType.TUPLE3, 0x20, CLValueTuple3.class, CLTypeTuple3.class),
    ANY(CLType.ANY, 0x21, CLValueAny.class, CLTypeBool.class), 
    PUBLIC_KEY(CLType.PUBLIC_KEY, 0x22, CLValuePublicKey.class, CLTypePublicKey.class),
    BYTE_ARRAY(CLType.BYTE_ARRAY, 0x23, CLValueByteArray.class, CLTypeByteArray.class);

    private final String clTypeName;
    private final int serializationTag;
    private final Class<? extends CLValue<?, ?>> clazz;
    private final Class<? extends CLType> clTypeClass;

    /**
     * Retrieve CLType by its serialization tag
     * 
     * @param serializationTag
     * @return
     * @throws NoSuchTypeException
     */
    public static CLTypeData getTypeBySerializationTag(byte serializationTag) throws NoSuchTypeException {
        for (CLTypeData clType : values()) {
            if (clType.serializationTag == serializationTag) {
                return clType;
            }
        }
        throw new NoSuchTypeException();
    }

    /**
     * Retrieve CLValue implementation class from CLType name
     * 
     * @param name
     * @return
     * @throws NoSuchTypeException
     */
    public static Class<?> getClassByName(String name) throws NoSuchTypeException {
        for (CLTypeData clType : values()) {
            if (clType.clTypeName.equals(name)) {
                return clType.getClazz();
            }
        }
        throw new NoSuchTypeException();
    }

    /**
     * Retrieve CLType from its name
     * 
     * @param name
     * @return
     * @throws NoSuchTypeException
     */
    public static CLTypeData getTypeByName(String name) throws NoSuchTypeException {
        for (CLTypeData clType : values()) {
            if (clType.clTypeName.equals(name)) {
                return clType;
            }
        }
        throw new NoSuchTypeException();
    }

    /**
     * Dynamically instantiate an AbstractCLValue when needed for decoding children objects
     * 
     * @param clTypeData the {@link CLTypeData} to instantiate
     * @return the desired CLValue implementation
     * @throws DynamicInstanceException
     */
    public static CLValue<?, ?> createCLValueFromCLTypeData(CLTypeData clTypeData) throws DynamicInstanceException {
        Class<?> clazz = clTypeData.getClazz();

        try {
            return (CLValue<?, ?>) clazz.getConstructor().newInstance();

        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
                | NoSuchMethodException | SecurityException e) {
            throw new DynamicInstanceException(String.format("Error while instantiating %s", clazz.getName()), e);
        }
    }
}
