package com.ejmc.common.application.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor(access =  AccessLevel.PRIVATE)
@Getter
public class CommonApiResponse<T> {

    private final boolean success;
    private final LocalDateTime timestamp;
    private final T body;
    private final ErrorBody error;

    public static <T> CommonApiResponse<T> success(T body) {
        return new CommonApiResponse<>(true, LocalDateTime.now(), body, ErrorBody.empty());
    }

    public static CommonApiResponse<Void> error(String message) {
        return error(message, null);
    }

    public static CommonApiResponse<Void> error(String message, List<ErrorBody.ErrorField> fields) {
        return new CommonApiResponse<>(false, LocalDateTime.now(), null, new ErrorBody(message, fields));
    }
}
