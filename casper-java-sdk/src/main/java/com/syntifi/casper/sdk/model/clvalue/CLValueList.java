package com.syntifi.casper.sdk.model.clvalue;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.syntifi.casper.sdk.exception.CLValueDecodeException;
import com.syntifi.casper.sdk.exception.CLValueEncodeException;
import com.syntifi.casper.sdk.exception.DynamicInstanceException;
import com.syntifi.casper.sdk.model.clvalue.encdec.CLValueDecoder;
import com.syntifi.casper.sdk.model.clvalue.encdec.CLValueEncoder;
import com.syntifi.casper.sdk.model.clvalue.type.CLTypeList;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Casper List CLValue implementation
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
public class CLValueList extends CLValue<List<? extends CLValue<?, ?>>, CLTypeList> {
    @JsonProperty("cl_type")
    private CLTypeList clType;

    // @Override
    // public void setClType(CLType value) {
    // this.clType = (CLTypeList)value;
    // }

    public CLValueList(List<? extends CLValue<?, ?>> value) {
        this.setValue(value);
    }

    @Override
    public void encode(CLValueEncoder clve) throws IOException, CLValueEncodeException, DynamicInstanceException {
        // List length is written first
        CLValueI32 length = new CLValueI32(getValue().size());
        clve.writeI32(length);
        setBytes(length.getBytes());

        for (CLValue<?, ?> child : getValue()) {
            child.encode(clve);
            setBytes(getBytes() + child.getBytes());
        }
    }

    @Override
    public void decode(CLValueDecoder clvd) throws IOException, CLValueDecodeException, DynamicInstanceException {
        // CLType childrenType = getClType().getChildTypes().get(0);

        // // List length is sent first
        // CLValueI32 length = new CLValueI32(0);
        // clvd.readI32(length);
        // setBytes(length.getBytes());

        // List<CLValue<?>> list = new LinkedList<>();
        // for (int i = 0; i < length.getValue(); i++) {
        // CLValue<?> child =
        // CLTypeData.createCLValueFromCLTypeData(childrenType.getClTypeData());
        // child.setClType(childrenType);
        // child.decode(clvd);
        // setBytes(getBytes() + child.getBytes());
        // list.add(child);
        // }

        // setValue(list);
    }

    @Override
    protected void setChildTypes() {
        // TODO Auto-generated method stub

    }
}
