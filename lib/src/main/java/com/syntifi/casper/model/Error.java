package com.syntifi.casper.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Error {
    @JsonProperty("message")
    public String message;

    @JsonProperty("data")
    public String data;

    @JsonProperty("code")
    public int code;
}
