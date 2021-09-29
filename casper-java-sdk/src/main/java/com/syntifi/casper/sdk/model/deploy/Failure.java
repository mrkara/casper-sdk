package com.syntifi.casper.sdk.model.deploy;

import java.math.BigInteger;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

import lombok.Data;

/**
 * Abstract Executable Result of type Failure containing the details of 
 * the contract execution. It shows the result of a failed execution
 * 
 * @author Alexandre Carvalho
 * @author Andre Bertolace
 * @since 0.0.1
 */
@Data
@JsonTypeName("Failure")
public class Failure extends ExecutionResult{
    /**
     * The cost of executing the deploy.
     */
    private BigInteger cost;

    /**
     * @see ExecutionEffect
     */
    private ExecutionEffect effect;

    /**
     * The error message associated with executing the deploy
     */
    @JsonProperty("error_message")
    private String errorMessage;

    /**
     * List of Hex-encoded transfer address.
     */
    private List<String> transfers;
}
