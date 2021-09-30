package com.syntifi.casper.sdk.model.clvalue;

import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.syntifi.casper.sdk.exception.CLValueDecodeException;
import com.syntifi.casper.sdk.exception.CLValueEncodeException;
import com.syntifi.casper.sdk.exception.DynamicInstanceException;
import com.syntifi.casper.sdk.model.clvalue.encdec.CLValueDecoder;
import com.syntifi.casper.sdk.model.clvalue.encdec.CLValueEncoder;
import com.syntifi.casper.sdk.model.clvalue.type.CLTypeTuple3;

import org.javatuples.Triplet;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Casper Tuple3 CLValue implementation
 * 
 * @author Alexandre Carvalho
 * @author Andre Bertolace
 * @see CLValue
 * @since 0.0.1
 */
@Data
@NoArgsConstructor
public class CLValueTuple3 extends
        CLValue<Triplet<? extends CLValue<?, ?>, ? extends CLValue<?, ?>, ? extends CLValue<?, ?>>, CLTypeTuple3> {
    @JsonProperty("cl_type")
    private CLTypeTuple3 clType;

    // @Override
    // public void setClType(CLType value) {
    // this.clType = (CLTypeTuple3) value;
    // }

    public CLValueTuple3(Triplet<? extends CLValue<?, ?>, ? extends CLValue<?, ?>, ? extends CLValue<?, ?>> value) {
        this.setValue(value);
    }

    @Override
    public void encode(CLValueEncoder clve) throws IOException, CLValueEncodeException, DynamicInstanceException {
        getValue().getValue0().encode(clve);
        getValue().getValue1().encode(clve);
        getValue().getValue2().encode(clve);

        setBytes(getValue().getValue0().getBytes() + getValue().getValue1().getBytes()
                + getValue().getValue2().getBytes());
    }

    @Override
    public void decode(CLValueDecoder clvd) throws IOException, CLValueDecodeException, DynamicInstanceException {
        // CLType childTypeData1 = getClType().getChildTypes().get(0);
        // CLType childTypeData2 = getClType().getChildTypes().get(1);
        // CLType childTypeData3 = getClType().getChildTypes().get(2);

        // CLValue<?, ?> child1 =
        // CLTypeData.createCLValueFromCLTypeData(childTypeData1.getClTypeData());
        // child1.setClType(childTypeData1);
        // child1.decode(clvd);

        // CLValue<?, ?> child2 =
        // CLTypeData.createCLValueFromCLTypeData(childTypeData2.getClTypeData());
        // child2.setClType(childTypeData2);
        // child2.decode(clvd);

        // CLValue<?, ?> child3 =
        // CLTypeData.createCLValueFromCLTypeData(childTypeData3.getClTypeData());
        // child3.setClType(childTypeData3);
        // child3.decode(clvd);

        // setValue(new Triplet<>(child1, child2, child3));
        // setBytes(getValue().getValue0().getBytes() +
        // getValue().getValue1().getBytes()
        // + getValue().getValue2().getBytes());
    }
}
