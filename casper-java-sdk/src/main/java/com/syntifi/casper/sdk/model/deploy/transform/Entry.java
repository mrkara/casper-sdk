package com.syntifi.casper.sdk.model.deploy.transform;

import lombok.Data;

/**
 * A transformation performed while executing a deploy.
 * 
 * @author Alexandre Carvalho
 * @author Andre Bertolace
 * @since 0.0.1
 */
@Data
public class Entry {
    /**
     * The formatted string of the `Key`
     */
    private String key;

    /**
     * @see Transform
     */
    private Transform transform;
   
}
