package com.syntifi.casper.sdk.model.clvalue;

import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.syntifi.casper.sdk.exception.CLValueDecodeException;
import com.syntifi.casper.sdk.exception.CLValueEncodeException;
import com.syntifi.casper.sdk.exception.DynamicInstanceException;
import com.syntifi.casper.sdk.model.clvalue.encdec.CLValueDecoder;
import com.syntifi.casper.sdk.model.clvalue.encdec.CLValueEncoder;
import com.syntifi.casper.sdk.model.clvalue.type.CLType;
import com.syntifi.casper.sdk.model.clvalue.type.CLTypeMap;
import com.syntifi.casper.sdk.model.clvalue.type.CLTypeTuple1;

import org.javatuples.Unit;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Casper Tuple1 CLValue implementation
 * 
 * @author Alexandre Carvalho
 * @author Andre Bertolace
 * @see CLValue
 * @since 0.0.1
 */
@Data
@NoArgsConstructor
public class CLValueTuple1 extends CLValue<Unit<? extends CLValue<?, ?>>, CLTypeTuple1> {
    @JsonProperty("cl_type")
    private CLTypeTuple1 clType;

    // @Override
    // public void setClType(CLType value) {
    //     this.clType = (CLTypeTuple1) value;
    // }

    public CLValueTuple1(Unit<? extends CLValue<?, ?>> value) {
        this.setValue(value);
    }

    @Override
    public void encode(CLValueEncoder clve) throws IOException, CLValueEncodeException, DynamicInstanceException {
        getValue().getValue0().encode(clve);

        setBytes(getValue().getValue0().getBytes());
    }

    @Override
    public void decode(CLValueDecoder clvd) throws IOException, CLValueDecodeException, DynamicInstanceException {
        // CLType childTypeData1 = getClType().getChildTypes().get(0);

        // CLValue<?, ?> child1 = CLTypeData.createCLValueFromCLTypeData(childTypeData1.getClTypeData());
        // child1.setClType(childTypeData1);
        // child1.decode(clvd);

        // setValue(new Unit<>(child1));
        // setBytes(getValue().getValue0().getBytes());
    }
}
