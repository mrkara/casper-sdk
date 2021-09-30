package com.syntifi.casper.sdk.model.clvalue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.syntifi.casper.sdk.exception.CLValueDecodeException;
import com.syntifi.casper.sdk.exception.CLValueEncodeException;
import com.syntifi.casper.sdk.exception.DynamicInstanceException;
import com.syntifi.casper.sdk.model.clvalue.encdec.CLValueDecoder;
import com.syntifi.casper.sdk.model.clvalue.encdec.CLValueEncoder;
import com.syntifi.casper.sdk.model.clvalue.type.CLTypeData;
import com.syntifi.casper.sdk.model.clvalue.type.CLTypeOption;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * Casper Option CLValue implementation
 * 
 * @author Alexandre Carvalho
 * @author Andre Bertolace
 * @see CLValue
 * @since 0.0.1
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class CLValueOption extends CLValue<Optional<? extends CLValue<?, ?>>, CLTypeOption> {
    @JsonProperty("cl_type")
    private CLTypeOption clType = new CLTypeOption();

    public CLValueOption() {
        this(Optional.ofNullable(null));
    }

    public CLValueOption(Optional<? extends CLValue<?, ?>> value) {
        this.setValue(value);
        setChildTypes();
    }

    @Override
    public void encode(CLValueEncoder clve) throws IOException, CLValueEncodeException, DynamicInstanceException {
        setChildTypes();

        CLValueBool isPresent = new CLValueBool(getValue().isPresent());
        clve.writeBool(isPresent);
        setBytes(isPresent.getBytes());

        Optional<? extends CLValue<?, ?>> child = getValue();
        if (child.isPresent()) {
            child.get().encode(clve);
            setBytes(getBytes() + child.get().getBytes());
        }
    }

    @Override
    public void decode(CLValueDecoder clvd) throws IOException, CLValueDecodeException, DynamicInstanceException {
        CLValueBool isPresent = new CLValueBool();
        isPresent.decode(clvd);
        setBytes(isPresent.getBytes());

        if (Boolean.TRUE.equals(isPresent.getValue())) {
            CLTypeData childTypeData = getClType().getChildClTypeData();

            CLValue<?, ?> child = CLTypeData.createCLValueFromCLTypeData(childTypeData);
            child.decode(clvd);

            setValue(Optional.of(child));
            setBytes(getBytes() + child.getBytes());
        } else {
            setValue(Optional.ofNullable(null));
        }

        setChildTypes();
    }

    @Override
    protected void setChildTypes() {
        clType.setChildTypes(getValue().isPresent() && getValue().get() == null ? new ArrayList<>()
                : getCLTypeDataOfChildren(getValue()));
    }

    protected List<String> getCLTypeDataOfChildren(Optional<? extends CLValue<?, ?>> value) {
        if (value.isPresent()) {
            return Arrays.asList(value.get().getClType().getTypeName());
        } else {
            return new ArrayList<>();
        }
    }
}
