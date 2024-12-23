package com.gamja.edubox_team1.auth.exception;

import com.gamja.edubox_team1.common.exception.GlobalException;
import lombok.Getter;

@Getter
public class ExpiredAccessTokenException extends GlobalException {
    public ExpiredAccessTokenException() {
        super(401, 4010, "ACCESS_TOKEN_EXPIRED");
    }
}
