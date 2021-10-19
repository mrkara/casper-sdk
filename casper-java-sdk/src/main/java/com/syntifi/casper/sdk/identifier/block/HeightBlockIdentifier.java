package com.syntifi.casper.sdk.identifier.block;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.syntifi.casper.sdk.service.CasperService;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Identifier class passed to service
 * {@link CasperService#getBlock(BlockIdentifierByHeigh)} to identify and
 * retrieve the block given its height.
 * 
 * @author Alexandre Carvalho
 * @author Andre Bertolace
 * @since 0.0.1
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HeightBlockIdentifier implements BlockIdentifier {

    /**
     * Block height
     */
    @JsonProperty("Height")
    private long height;
}
