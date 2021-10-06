package com.syntifi.casper.sdk.model.status;

import java.math.BigInteger;
import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * The first era to which the associated protocol version applies
 * 
 * @author Alexandre Carvalho
 * @author Andre Bertolace
 * @since 0.0.1
 */
@Data
public class ActivationPoint {

    /**
     * Era ID 
     */
    @JsonIgnore
    private BigInteger eraId;

    @JsonProperty("era_id")
    protected String getBigInteger() {
        return this.eraId.toString(10);
    }

    @JsonProperty("era_id")
    protected void setBigInteger(String value) {
        this.eraId = new BigInteger(value, 10);
    }

    /**
     * Timestamp formatted as per RFC 3339
     */
    @JsonProperty("timestamp")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private Date timeStamp;
}
