package com.syntifi.casper.sdk.model.clvalue;

import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.syntifi.casper.sdk.exception.CLValueDecodeException;
import com.syntifi.casper.sdk.exception.CLValueEncodeException;
import com.syntifi.casper.sdk.exception.DynamicInstanceException;
import com.syntifi.casper.sdk.model.clvalue.encdec.CLValueDecoder;
import com.syntifi.casper.sdk.model.clvalue.encdec.CLValueEncoder;
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
public class CLValueResult extends CLValue<Result, CLTypeResult> {
    @JsonProperty("cl_type")
    private CLTypeResult clType;

    public CLValueResult(Result value) {
        this.setValue(value);
    }

    @Override
    public void encode(CLValueEncoder clve) throws IOException, CLValueEncodeException, DynamicInstanceException {
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
    public void decode(CLValueDecoder clvd) throws IOException, CLValueDecodeException, DynamicInstanceException {
        // Result result = new Result();

        // for (int i = 0; i < 2; i++) {
        // CLValueBool bool = new CLValueBool();
        // bool.decode(clvd);

        // CLType type =
        // getClType().getChildTypes().get(Boolean.TRUE.equals(bool.getValue()) ? 0 :
        // 1);
        // CLValue<?> okErr =
        // CLTypeData.createCLValueFromCLTypeData(type.getClTypeData());
        // okErr.setClType(type);
        // okErr.decode(clvd);

        // if (Boolean.TRUE.equals(bool.getValue())) {
        // result.setOk(okErr);
        // } else {
        // result.setErr(okErr);
        // }
        // }

        // setValue(result);
    }

    @Override
    protected void setChildTypes() {
        // TODO Auto-generated method stub

    }
}