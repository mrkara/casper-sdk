package com.syntifi.casper.sdk.model.clvalue;

import java.io.IOException;
import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.syntifi.casper.sdk.exception.CLValueDecodeException;
import com.syntifi.casper.sdk.exception.CLValueEncodeException;
import com.syntifi.casper.sdk.exception.DynamicInstanceException;
import com.syntifi.casper.sdk.model.clvalue.encdec.CLValueDecoder;
import com.syntifi.casper.sdk.model.clvalue.encdec.CLValueEncoder;
import com.syntifi.casper.sdk.model.clvalue.type.CLTypeChildren;
import com.syntifi.casper.sdk.model.clvalue.type.CLTypeData;
import com.syntifi.casper.sdk.model.clvalue.type.CLTypeTuple2;

import org.javatuples.Pair;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Casper Tuple2 CLValue implementation
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
public class CLValueTuple2 extends CLValueChildren<Pair<? extends CLValue<?, ?>, ? extends CLValue<?, ?>>, CLTypeTuple2> {
    @JsonProperty("cl_type")
    private CLTypeTuple2 clType = new CLTypeTuple2();

    public CLValueTuple2(Pair<? extends CLValue<?, ?>, ? extends CLValue<?, ?>> value) {
        this.setValue(value);
        setChildTypes();
    }

    @Override
    public void encode(CLValueEncoder clve) throws IOException, CLValueEncodeException, DynamicInstanceException {
        setChildTypes();

        getValue().getValue0().encode(clve);
        getValue().getValue1().encode(clve);

        setBytes(getValue().getValue0().getBytes() + getValue().getValue1().getBytes());
    }

    @Override
    public void decode(CLValueDecoder clvd) throws IOException, CLValueDecodeException, DynamicInstanceException {
        CLTypeData childTypeData1 = clType.getChildClTypeData(0);
        CLTypeData childTypeData2 = clType.getChildClTypeData(1);

        CLValue<?, ?> child1 = CLTypeData.createCLValueFromCLTypeData(childTypeData1);
        if (child1.getClType() instanceof CLTypeChildren) {
            ((CLTypeChildren) child1.getClType()).getChildTypes()
                    .addAll(((CLTypeChildren) clType.getChildTypes().get(0)).getChildTypes());
        }
        child1.decode(clvd);

        CLValue<?, ?> child2 = CLTypeData.createCLValueFromCLTypeData(childTypeData2);
        if (child2.getClType() instanceof CLTypeChildren) {
            ((CLTypeChildren) child2.getClType()).getChildTypes()
                    .addAll(((CLTypeChildren) clType.getChildTypes().get(1)).getChildTypes());
        }
        child2.decode(clvd);

        setValue(new Pair<>(child1, child2));
        setBytes(getValue().getValue0().getBytes() + getValue().getValue1().getBytes());

        setChildTypes();
    }

    @Override
    protected void setChildTypes() {
        clType.setChildTypes(Arrays.asList(getValue().getValue0().getClType(), getValue().getValue1().getClType()));
    }
}
