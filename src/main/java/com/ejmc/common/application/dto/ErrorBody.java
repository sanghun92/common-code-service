package com.ejmc.common.application.dto;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ErrorBody {

    @Schema(description = "에러 메시지")
    @JsonSetter(nulls = Nulls.AS_EMPTY)
    private final String message;

    @Schema(description = "유효하지 않은 필드 목록")
    private final List<ErrorField> fields;

    public ErrorBody(String message, List<ErrorField> fields) {
        this.message = message;
        this.fields = fields;
    }

    public static ErrorBody empty() {
        return new ErrorBody("", new ArrayList<>());
    }

    @Getter
    public static final class ErrorField implements Comparable<ErrorField> {

        @Schema(description = "유효하지 않은 필드명")
        private final String field;

        @Schema(description = "유효하지 않은 필드 요청값")
        private final String value;

        @Schema(description = "유효하지 않은 필드 에러 메시지")
        private final String message;

        public ErrorField(String field, String value, String message) {
            this.field = field;
            this.value = value;
            this.message = message;
        }

        @Override
        public int compareTo(ErrorField o) {
            return this.field.compareTo(o.field);
        }
    }
}
