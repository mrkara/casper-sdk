package com.syntifi.casper.sdk.model.clvalue;

import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.syntifi.casper.sdk.exception.CLValueDecodeException;
import com.syntifi.casper.sdk.exception.CLValueEncodeException;
import com.syntifi.casper.sdk.exception.DynamicInstanceException;
import com.syntifi.casper.sdk.exception.NoSuchTypeException;
import com.syntifi.casper.sdk.model.clvalue.encdec.CLValueDecoder;
import com.syntifi.casper.sdk.model.clvalue.encdec.CLValueEncoder;
import com.syntifi.casper.sdk.model.clvalue.type.CLTypeWithChildren;
import com.syntifi.casper.sdk.model.clvalue.type.CLTypeData;
import com.syntifi.casper.sdk.model.clvalue.type.CLTypeResult;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Casper Result CLValue implementation
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
public class CLValueResult extends CLValueWithChildren<Result, CLTypeResult> {
    @JsonProperty("cl_type")
    private CLTypeResult clType = new CLTypeResult();

    public CLValueResult(Result value) {
        this.setValue(value);
        setChildTypes();
    }

    @Override
    public void encode(CLValueEncoder clve)
            throws IOException, CLValueEncodeException, DynamicInstanceException, NoSuchTypeException {
        setChildTypes();

        CLValueBool clValueTrue = new CLValueBool(true);
        clValueTrue.encode(clve);

        getValue().getOk().encode(clve);

        CLValueBool clValueFalse = new CLValueBool(false);
        clValueFalse.encode(clve);

        getValue().getErr().encode(clve);

        setBytes(clValueTrue.getBytes() + getValue().getOk().getBytes() + clValueFalse.getBytes()
                + getValue().getErr().getBytes());
    }

    @Override
    public void decode(CLValueDecoder clvd)
            throws IOException, CLValueDecodeException, DynamicInstanceException, NoSuchTypeException {
        Result result = new Result();
        CLValueBool bool = new CLValueBool();
        bool.decode(clvd);
        CLTypeData typeOk = clType.getOkErrTypes().getOkType().getClTypeData();
        CLValue<?, ?> clValueOk = CLTypeData.createCLValueFromCLTypeData(typeOk);
        if (clValueOk.getClType() instanceof CLTypeWithChildren) {
            ((CLTypeWithChildren) clValueOk.getClType()).getChildTypes()
                    .addAll(((CLTypeWithChildren) clType.getOkErrTypes().getOkType()).getChildTypes());
        }
        clValueOk.decode(clvd);
        result.setOk(clValueOk);

        bool = new CLValueBool();
        bool.decode(clvd);
        CLTypeData typeErr = clType.getOkErrTypes().getErrType().getClTypeData();
        CLValue<?, ?> clValueErr = CLTypeData.createCLValueFromCLTypeData(typeErr);
        if (clValueErr.getClType() instanceof CLTypeWithChildren) {
            ((CLTypeWithChildren) clValueErr.getClType()).getChildTypes()
                    .addAll(((CLTypeWithChildren) clType.getOkErrTypes().getErrType()).getChildTypes());
        }
        clValueErr.decode(clvd);
        result.setErr(clValueErr);

        setValue(result);
    }

    @Override
    protected void setChildTypes() {
        clType.setOkErrTypes(clType.new CLTypeResultOkErrTypes(getValue().getOk().getClType().getTypeName(),
                getValue().getErr().getClType().getTypeName()));
    }
}