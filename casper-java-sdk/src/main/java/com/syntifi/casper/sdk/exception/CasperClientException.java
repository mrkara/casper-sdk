package com.syntifi.casper.sdk.exception;

public class CasperClientException extends RuntimeException {

    public CasperClientException(CasperClientErrorData error) {
        super(String.format("%s (code: %d)", error.getMessage(), error.getCode()));
    }

    public CasperClientException(String message, Throwable cause) {
        super(message, cause);
    }
}
