package com.syntifi.casper.sdk.model.contract;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * Group class
 * 
 * @author Alexandre Carvalho
 * @author Andre Bertolace
 * @since 0.0.1
 */
@Data
public class Group {
    /**
     * group(string)
     */
    @JsonProperty("group")
    private String name;

    /**
     * keys(Array/string) Hex-encoded, formatted URef.
     */
    @JsonProperty("keys")
    private List<String> keys;
}
