package com.syntifi.casper.sdk.exception;

public class CasperClientException extends RuntimeException {
    
    public CasperClientException(String message) {
        super(message);
    }

    public CasperClientException(String message, Throwable cause) {
        super(message, cause);
    }
}
