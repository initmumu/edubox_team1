package com.gamja.edubox_team1.common.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CommonResponse<T> {
    private Meta meta;
    private T payload;

    public static <T> CommonResponse<T> success(T payload) {
        return new CommonResponse<>(new Meta(2000, "OK"), payload);
    }

    public static <T> CommonResponse<T> error(int code, String message) {
        return new CommonResponse<>(new Meta(code, message),null);
    }

    @Getter
    @Setter
    public static class Meta {
        private int code;
        private String message;

        public Meta(int code, String message) {
            this.code = code;
            this.message = message;
        }
    }
}
