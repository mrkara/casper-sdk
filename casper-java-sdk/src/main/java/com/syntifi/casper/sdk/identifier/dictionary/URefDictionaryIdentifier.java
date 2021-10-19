package com.syntifi.casper.sdk.identifier.dictionary;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;

/**
 * Identifier class passed to service
 * {@link CasperService#getDictionaryItem(String, URefDictionaryIdentifier)} to Lookup a
 * dictionary item via its seed URef
 *
 * @author Alexandre Carvalho
 * @author Andre Bertolace
 * @since 0.0.1
 */
@Data
@Builder
public class URefDictionaryIdentifier implements DictionaryIdentifier {
    @JsonProperty("URef")
    private URefSeed uref;
}
