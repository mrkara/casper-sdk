package com.syntifi.casper.sdk.model.clvalue;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.syntifi.casper.sdk.exception.CLValueDecodeException;
import com.syntifi.casper.sdk.exception.CLValueEncodeException;
import com.syntifi.casper.sdk.exception.DynamicInstanceException;
import com.syntifi.casper.sdk.model.clvalue.encdec.CLValueDecoder;
import com.syntifi.casper.sdk.model.clvalue.encdec.CLValueEncoder;
import com.syntifi.casper.sdk.model.clvalue.type.CLTypeChildren;
import com.syntifi.casper.sdk.model.clvalue.type.CLTypeData;
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
public class CLValueMap extends CLValueChildren<Map<? extends CLValue<?, ?>, ? extends CLValue<?, ?>>, CLTypeMap> {
    @JsonProperty("cl_type")
    private CLTypeMap clType = new CLTypeMap();

    public CLValueMap(Map<? extends CLValue<?, ?>, ? extends CLValue<?, ?>> value) {
        this.setValue(value);
        setChildTypes();
    }

    @Override
    public void encode(CLValueEncoder clve) throws IOException, CLValueEncodeException, DynamicInstanceException {
        setChildTypes();

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
        CLTypeData keyType = clType.getChildClTypeData(0);
        CLTypeData valType = clType.getChildClTypeData(1);

        Map<CLValue<?, ?>, CLValue<?, ?>> map = new LinkedHashMap<>();
        CLValueI32 mapLength = new CLValueI32(0);
        clvd.readI32(mapLength);

        for (int i = 0; i < mapLength.getValue(); i++) {
            CLValue<?, ?> key = CLTypeData.createCLValueFromCLTypeData(keyType);
            if (key.getClType() instanceof CLTypeChildren) {
                ((CLTypeChildren) key.getClType()).getChildTypes()
                        .addAll(((CLTypeChildren) clType.getChildTypes().get(0)).getChildTypes());
            }
            key.decode(clvd);

            CLValue<?, ?> val = CLTypeData.createCLValueFromCLTypeData(valType);
            if (val.getClType() instanceof CLTypeChildren) {
                ((CLTypeChildren) val.getClType()).getChildTypes()
                        .addAll(((CLTypeChildren) clType.getChildTypes().get(0)).getChildTypes());
            }
            val.decode(clvd);

            map.put(key, val);
        }

        setValue(map);

        setChildTypes();
    }

    @Override
    protected void setChildTypes() {
        Entry<? extends CLValue<?, ?>, ? extends CLValue<?, ?>> entry = getValue().entrySet().iterator().next();

        clType.setChildTypes(
                Arrays.asList(entry.getKey().getClType(), entry.getValue().getClType()));
    }
}