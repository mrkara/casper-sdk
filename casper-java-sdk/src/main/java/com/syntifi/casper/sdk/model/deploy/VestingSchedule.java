package com.syntifi.casper.sdk.model.deploy;

import java.math.BigInteger;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class VestingSchedule {
    
    @JsonProperty("initial_release_timestamp_millis")
    private BigInteger initialReleaseTimeStampMillis;

    @JsonProperty("locked_amounts")
    private List<BigInteger> lockedAmounts;
    
}
