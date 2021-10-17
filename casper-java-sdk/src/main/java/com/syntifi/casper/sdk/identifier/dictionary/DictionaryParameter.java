package com.syntifi.casper.sdk.identifier.dictionary;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.syntifi.casper.sdk.model.dictionary.Dictionary;
import com.syntifi.casper.sdk.service.CasperService;

import lombok.Builder;
import lombok.Data;

/**
 * Identifier class passed to service
 * {@link CasperService#getDictionaryItem(String, Dictionary)}
 * to Lookup a dictionary item via it's unique key 
 *
 * @author Alexandre Carvalho
 * @author Andre Bertolace
 * @since 0.0.1
 */
@Data
@Builder
public class DictionaryParameter {
    @JsonProperty("Dictionary")
    private String dictionary;
}



