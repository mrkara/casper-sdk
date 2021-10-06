package com.syntifi.casper.sdk.identifier.block;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Identifier class passed to service
 * {@link com.syntifi.casper.sdk.service.CasperService#getBlock(BlockIdentifierByHeigh)}
 * to identify and retrieve the block given its height.
 * 
 * @author Alexandre Carvalho
 * @author Andre Bertolace
 * @since 0.0.1
 */
@Data
@Builder
@NoArgsConstructor //(access = AccessLevel.PRIVATE)
@AllArgsConstructor //(access = AccessLevel.PRIVATE)
public class BlockIdentifierByHeight {

    /**
     * Block height
     */
    @JsonProperty("Height")
    private long height;
}
