package com.syntifi.casper.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Response<T> {
    @JsonProperty("jsonrpc")
    public String jsonrpc;

    @JsonProperty("id")
    public int id;

    @JsonProperty("result")
    public T result;

    @JsonProperty("error")
    public Error error;
}
