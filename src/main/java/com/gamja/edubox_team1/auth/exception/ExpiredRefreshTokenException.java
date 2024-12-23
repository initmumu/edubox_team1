package com.gamja.edubox_team1.auth.exception;

import com.gamja.edubox_team1.common.exception.GlobalException;
import lombok.Getter;

@Getter
public class ExpiredRefreshTokenException extends GlobalException {
    public ExpiredRefreshTokenException() {
        super(401, 4011, "REFRESH_TOKEN_EXPIRED");
    }
}
