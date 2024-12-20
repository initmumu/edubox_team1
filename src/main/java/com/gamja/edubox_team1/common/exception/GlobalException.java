package com.gamja.edubox_team1.common.exception;

import lombok.Getter;

@Getter
public class GlobalException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private int httpStatus;
    private int code;

    public GlobalException(int httpStatus, int code, String message) {
        super(message);
        this.httpStatus = httpStatus;
        this.code = code;
    }
}
