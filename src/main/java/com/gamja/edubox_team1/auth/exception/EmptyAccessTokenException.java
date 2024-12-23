package com.gamja.edubox_team1.auth.exception;

import com.gamja.edubox_team1.common.exception.GlobalException;
import lombok.Getter;

@Getter
public class EmptyAccessTokenException extends GlobalException {
    public EmptyAccessTokenException() {
        super(401, 4013, "EMPTY_ACCESS_TOKEN");
    }
}
