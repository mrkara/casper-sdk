package com.syntifi.casper.sdk.model.clvalue;

import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.syntifi.casper.sdk.exception.CLValueDecodeException;
import com.syntifi.casper.sdk.model.clvalue.encdec.CLValueDecoder;
import com.syntifi.casper.sdk.model.clvalue.encdec.CLValueEncoder;
import com.syntifi.casper.sdk.model.clvalue.type.CLTypeAny;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Casper Object CLValue implementation
 * 
 * @author Alexandre Carvalho
 * @author Andre Bertolace
 * @see CLValue
 * @since 0.0.1
 */
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true, of = { "clType" })
public class CLValueAny extends CLValue<Object, CLTypeAny> {
    private CLTypeAny clType = new CLTypeAny();

    @JsonSetter("cl_type")
    public void setJsonClType(CLTypeAny clType) {
        this.clType = clType;
    }

    @JsonGetter("cl_type")
    public String getJsonClType() {
        return this.getClType().getTypeName();
    }

    public CLValueAny(Object value) {
        this.setValue(value);
    }

    @Override
    public void encode(CLValueEncoder clve) throws IOException {
        clve.writeAny(this);
    }

    @Override
    public void decode(CLValueDecoder clvd) throws IOException, CLValueDecodeException {
        clvd.readAny(this);
    }
}
