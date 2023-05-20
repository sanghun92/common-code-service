package com.ejmc.common.exception;

import com.ejmc.common.application.dto.ErrorBody;
import com.ejmc.common.application.dto.CommonApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class AppExceptionHandler extends ResponseEntityExceptionHandler  {

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        String message = "요청 내용이 잘못되었습니다.";
        logger.error(message, ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(CommonApiResponse.error(message));
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        String message = "데이터 바인딩에 실패하였습니다.";
        logger.error(message, ex);
        List<ErrorBody.ErrorField> errorField = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> new ErrorBody.ErrorField(
                        error.getField(),
                        error.getRejectedValue() == null ? "" : error.getRejectedValue().toString(),
                        error.getDefaultMessage()
                ))
                .sorted()
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(CommonApiResponse.error(message, errorField));
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
                                                                          HttpHeaders headers,
                                                                          HttpStatus status,
                                                                          WebRequest request) {
        String message = String.format("'%s' 쿼리 파라미터는 필수값입니다.", ex.getParameterName());
        logger.error(message, ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(CommonApiResponse.error(message));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<CommonApiResponse<Void>> handleIllegalArgumentException(IllegalArgumentException ex) {
        String message = "유효하지 않은 인수가 넘겨졌습니다.";
        logger.error(message, ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(CommonApiResponse.error(message));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<CommonApiResponse<Void>> handleEntityNotFoundException(EntityNotFoundException ex) {
        String message = "엔티티 조회에 실패하였습니다.";
        logger.error(message, ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(CommonApiResponse.error(message));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CommonApiResponse<Void>> handleGlobalException(Exception ex) {
        String message = "알수 없는 에러가 발생하였습니다.";
        logger.error(message, ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(CommonApiResponse.error(message));
    }
}
