package com.syntifi.casper.sdk.model.deploy;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.syntifi.casper.sdk.model.storedvalue.clvalue.AbstractCLValue;

import lombok.Data;

/**
 * Named arguments to a contract
 * 
 * @author Alexandre Carvalho
 * @author Andre Bertolace
 * @since 0.0.1
 */
@Data
@JsonFormat(shape=JsonFormat.Shape.ARRAY)
@JsonPropertyOrder({ "type", "clValue" }) 
public class NamedArg<T>{
    
    /**
     * The first value in the array is the type of the arg 
     */
   private String type;

   /**
    * The second value in the array is a CLValue type 
    */
   private AbstractCLValue<T> clValue;
}
