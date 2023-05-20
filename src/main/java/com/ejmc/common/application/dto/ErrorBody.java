package com.ejmc.common.application.dto;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ErrorBody {

    private final String message;

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

        private final String field;

        private final String value;

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
