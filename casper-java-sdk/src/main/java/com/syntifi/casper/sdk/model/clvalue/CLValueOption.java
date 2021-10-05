package com.syntifi.casper.sdk.model.clvalue;

import java.io.IOException;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.syntifi.casper.sdk.exception.CLValueDecodeException;
import com.syntifi.casper.sdk.exception.CLValueEncodeException;
import com.syntifi.casper.sdk.exception.DynamicInstanceException;
import com.syntifi.casper.sdk.exception.NoSuchTypeException;
import com.syntifi.casper.sdk.model.clvalue.encdec.CLValueDecoder;
import com.syntifi.casper.sdk.model.clvalue.encdec.CLValueEncoder;
import com.syntifi.casper.sdk.model.clvalue.type.CLTypeData;
import com.syntifi.casper.sdk.model.clvalue.type.CLTypeOption;
import com.syntifi.casper.sdk.model.clvalue.type.CLTypeWithChildren;

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
public class CLValueOption extends CLValue<Optional<CLValue<?, ?>>, CLTypeOption> {
    @JsonProperty("cl_type")
    private CLTypeOption clType = new CLTypeOption();

    public CLValueOption() {
        this(Optional.of(new CLValueAny(null)));
    }

    public CLValueOption(Optional<CLValue<?, ?>> value) {
        this.setValue(value);
        this.clType.setChildType(value.isPresent() ? value.get().getClType() : null);
    }

    @Override
    public void encode(CLValueEncoder clve)
            throws IOException, CLValueEncodeException, DynamicInstanceException, NoSuchTypeException {
        CLValueBool isPresent = new CLValueBool(getValue().isPresent() && getValue().get().getValue() != null);
        isPresent.encode(clve);
        setBytes(isPresent.getBytes());

        Optional<CLValue<?, ?>> child = getValue();

        if (child.isPresent() && child.get().getClType() instanceof CLTypeWithChildren) {
            ((CLTypeWithChildren) child.get().getClType())
                    .setChildTypes(((CLTypeWithChildren) clType.getChildType()).getChildTypes());
        }
        if (child.isPresent() && isPresent.getValue().equals(Boolean.TRUE)) {
            child.get().encode(clve);
            setBytes(getBytes() + child.get().getBytes());
        }
    }

    @Override
    public void decode(CLValueDecoder clvd)
            throws IOException, CLValueDecodeException, DynamicInstanceException, NoSuchTypeException {
        CLValueBool isPresent = new CLValueBool();
        isPresent.decode(clvd);
        setBytes(isPresent.getBytes());

        CLTypeData childTypeData = clType.getChildType().getClTypeData();

        CLValue<?, ?> child = CLTypeData.createCLValueFromCLTypeData(childTypeData);

        if (child.getClType() instanceof CLTypeWithChildren) {
            ((CLTypeWithChildren) child.getClType())
                    .setChildTypes(((CLTypeWithChildren) clType.getChildType()).getChildTypes());
        }

        setValue(Optional.of(child));

        if (isPresent.getValue().equals(Boolean.TRUE)) {
            child.decode(clvd);

            setBytes(getBytes() + child.getBytes());
        }
    }
}
