package com.syntifi.casper.sdk.model.bid;

import java.math.BigInteger;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * Vesting schedule.
 *
 * @author Alexandre Carvalho
 * @author Andre Bertolace
 * @since 0.0.1
 */
@Data
public class VestingSchedule {
   
    /**
     * release time in miliseconds
     */
    @JsonProperty("initial_release_timestamp_millis")
    private BigInteger initialReleaseTimeStampMillis;

    /**
     * amount locked
     */
    @JsonProperty("locked_amounts")
    private List<BigInteger> lockedAmounts;
    
}
