package com.syntifi.casper.sdk.exception;

public class BufferEndCLValueDecodeException extends CLValueDecodeException {
    public BufferEndCLValueDecodeException(String message) {
        super(message);
    }

    public BufferEndCLValueDecodeException(String message, Throwable cause) {
        super(message, cause);
    }    
}
