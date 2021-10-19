package com.syntifi.casper.sdk.identifier.dictionary;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;

/**
 * Identifier class passed to service
 * {@link CasperService#getDictionaryItem(String, AccountNamedKeyDictionaryIdentifier)}
 * to Lookup a dictionary item via an Account named key
 *
 * @author Alexandre Carvalho
 * @author Andre Bertolace
 * @since 0.0.1
 */
@Data
@Builder
public class AccountNamedKeyDictionaryIdentifier implements DictionaryIdentifier {
    @JsonProperty("AccountNamedKey")
    private AccountNamedKey accountNamedKey;
}
