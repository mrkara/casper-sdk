package com.syntifi.casper.sdk.exception;

import lombok.Data;

@Data
public class CasperClientErrorData {
    private int code;
    private String message;
    private Object data;
}
