package com.ejmc.common.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor(access =  AccessLevel.PRIVATE)
@Getter
public class CommonApiResponse<T> {

    @Schema(description = "응답 성공 여부")
    private final boolean success;

    @Schema(description = "응답 일자 및 시간")
    private final LocalDateTime timestamp;

    @Schema(description = "응답 데이터")
    private final T body;

    @Schema(description = "에러")
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
