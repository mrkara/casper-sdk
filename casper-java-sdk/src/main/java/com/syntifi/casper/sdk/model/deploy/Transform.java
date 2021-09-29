package com.syntifi.casper.sdk.model.deploy;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * Abstract Transform containing the actual transformation performed while executing a deploy. 
 * It can be any of the following types: 
 * @see Failure 
 * @see Success 
 * 
 * @author Alexandre Carvalho
 * @author Andre Bertolace
 * @since 0.0.1
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.WRAPPER_OBJECT)
@JsonSubTypes({
    @JsonSubTypes.Type(value = TransformWriteContract.class, name = "Identity"),
    @JsonSubTypes.Type(value = TransformWriteCLValue.class, name = "WriteCLValue"),
    @JsonSubTypes.Type(value = TransformWriteAccount.class, name = "WriteAccount"),
    @JsonSubTypes.Type(value = TransformWriteDeployInfo.class, name = "WriteDeployInfo"),
    @JsonSubTypes.Type(value = TransformWriteBid.class, name = "WriteBid"),
    @JsonSubTypes.Type(value = TransformTransfer.class, name = "Transfer"),
    @JsonSubTypes.Type(value = TransformWriteWithdraw.class, name = "WriteWithdraw")
})
public abstract class Transform {
}
