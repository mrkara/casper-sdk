package com.syntifi.casper.sdk.model.account;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * Thresholds that have to be met when executing an action of a certain type.
 * 
 */
@Data
public class ActionThresholds {
    /**
     * deployment(Integer)
     */
    @JsonProperty("deployment")
    private int deployment;

    /**
     * key_management(Integer)
     */
    @JsonProperty("key_management")
    private int keyManagement;
}
