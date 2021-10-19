package com.syntifi.casper.sdk.identifier.dictionary;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class URefDictionaryIdentifier implements DictionaryIdentifier {
    @JsonProperty("URef")
    private URefSeed uref;
}
