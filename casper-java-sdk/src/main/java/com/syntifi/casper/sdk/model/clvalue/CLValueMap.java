package com.syntifi.casper.sdk.model.clvalue;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.syntifi.casper.sdk.exception.CLValueDecodeException;
import com.syntifi.casper.sdk.exception.CLValueEncodeException;
import com.syntifi.casper.sdk.exception.DynamicInstanceException;
import com.syntifi.casper.sdk.model.clvalue.encdec.CLValueDecoder;
import com.syntifi.casper.sdk.model.clvalue.encdec.CLValueEncoder;
import com.syntifi.casper.sdk.model.clvalue.type.CLTypeMap;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Casper Map CLValue implementation
 * 
 * @author Alexandre Carvalho
 * @author Andre Bertolace
 * @see CLValue
 * @since 0.0.1
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class CLValueMap extends CLValue<Map<? extends CLValue<?, ?>, ? extends CLValue<?, ?>>, CLTypeMap> {
    @JsonProperty("cl_type")
    private CLTypeMap clType;

    // @Override
    // public void setClType(CLType value) {
    // this.clType = (CLTypeMap) value;
    // }

    public CLValueMap(Map<? extends CLValue<?, ?>, ? extends CLValue<?, ?>> value) {
        this.setValue(value);
    }

    @Override
    public void encode(CLValueEncoder clve) throws IOException, CLValueEncodeException, DynamicInstanceException {
        CLValueI32 mapLength = new CLValueI32(getValue().size());
        clve.writeI32(mapLength);
        setBytes(mapLength.getBytes());

        for (Entry<? extends CLValue<?, ?>, ? extends CLValue<?, ?>> entry : getValue().entrySet()) {
            entry.getKey().encode(clve);
            entry.getValue().encode(clve);
            setBytes(getBytes() + entry.getKey().getBytes() + entry.getValue().getBytes());
        }
    }

    @Override
    public void decode(CLValueDecoder clvd) throws IOException, CLValueDecodeException, DynamicInstanceException {
        // CLType keyType = getClType().getChildTypes().get(0);
        // CLType valType = getClType().getChildTypes().get(1);

        // Map<CLValue<?>, CLValue<?>> map = new LinkedHashMap<>();
        // CLValueI32 mapLength = new CLValueI32(0);
        // clvd.readI32(mapLength);

        // for (int i = 0; i < mapLength.getValue(); i++) {
        // CLValue<?> key =
        // CLTypeData.createCLValueFromCLTypeData(keyType.getClTypeData());
        // key.setClType(keyType);
        // key.decode(clvd);

        // CLValue<?> val =
        // CLTypeData.createCLValueFromCLTypeData(valType.getClTypeData());
        // val.setClType(valType);
        // val.decode(clvd);

        // map.put(key, val);
        // }

        // setValue(map);
    }

    @Override
    protected void setChildTypes() {
        // TODO Auto-generated method stub

    }

    // static List<CLType> getCLTypeDataOfChildren(Map<? extends CLValue<?>, ?
    // extends CLValue<?>> value) {
    // Entry<? extends CLValue<?>, ? extends CLValue<?>> entry =
    // value.entrySet().iterator().next();

    // return Arrays.asList(entry.getKey().getClType(),
    // entry.getValue().getClType());
    // }
}