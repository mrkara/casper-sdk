package com.syntifi.casper.sdk.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.googlecode.jsonrpc4j.ExceptionResolver;
import com.syntifi.casper.sdk.service.CasperObjectMapper;

public class CasperClientExceptionResolver implements ExceptionResolver {
    private static ObjectMapper objectMapper = new CasperObjectMapper();

    @Override
    public Throwable resolveException(ObjectNode response) {
        try {
            JsonNode errorNode = response.get("error");
            CasperClientErrorData error = objectMapper.treeToValue(errorNode, CasperClientErrorData.class);
            return new CasperClientException(String.format("%s (code: %d)", error.getMessage(), error.getCode()));
        } catch (JsonProcessingException | IllegalArgumentException e) {
            return new CasperClientException(String.format("Could not extract error, response: %s", response), e);
        }
    }
}
