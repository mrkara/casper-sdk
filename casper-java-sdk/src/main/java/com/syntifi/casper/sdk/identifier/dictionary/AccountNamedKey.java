package com.syntifi.casper.sdk.identifier.dictionary;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.syntifi.casper.sdk.service.CasperService;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Identifier class passed to service
 * {@link CasperService#getDictionaryItem(String, AccountNamedKey)}
 * to Lookup a dictionary item via an Account's named keys
 *
 * @author Alexandre Carvalho
 * @author Andre Bertolace
 * @since 0.0.1
 */
@Data
@Builder
@NoArgsConstructor //(access = AccessLevel.PRIVATE)
@AllArgsConstructor //(access = AccessLevel.PRIVATE)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.WRAPPER_OBJECT)
public class AccountNamedKey {

    /**
     *  The dictionary item key formatted as a string
     */
    @JsonProperty("dictionary_item_key")
    private String dictionaryItemKey;

    /**
     * The dictionary item key formatted as a string
     */
    @JsonProperty("dictionary_name")
    private String dictionaryName;

    /**
     * The account key as a formatted string whose named keys contains dictionary_name.
     */
    private String key;

    public String getKey() {
        return key;
    }
}

