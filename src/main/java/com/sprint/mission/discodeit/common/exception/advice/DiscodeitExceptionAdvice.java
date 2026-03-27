package com.sprint.mission.discodeit.common.exception.advice;

import com.sprint.mission.discodeit.common.exception.code.ErrorCode;
import com.sprint.mission.discodeit.common.exception.custom.APIException;
import com.sprint.mission.discodeit.common.exception.model.APIExceptionModel;
import com.sprint.mission.discodeit.exception.common.DiscodeitException;
import com.sprint.mission.discodeit.exception.dto.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class DiscodeitExceptionAdvice {

    @ExceptionHandler(APIException.class)
    private ProblemDetail handleAPIException(APIException exception, HttpServletRequest request) {
        ErrorCode errorCode = exception.getErrorCode();
        return APIExceptionModel.builder(errorCode.getStatus())
                .detail(exception.getDetail())
                .errorCode(errorCode.getCode())
                .method(request.getMethod())
                .uri(request.getRequestURI())
                .build();
    }

    // todo
    @ExceptionHandler(MethodArgumentNotValidException.class)
    private ProblemDetail handleIncorrectInput(MethodArgumentNotValidException exception, HttpServletRequest request) {
        return APIExceptionModel.builder(exception.getStatusCode().value())
                .detail(exception.getLocalizedMessage())
                .method(request.getMethod())
                .uri(request.getRequestURI())
                .build();
    }

    @ExceptionHandler(DiscodeitException.class)
    private ResponseEntity<ErrorResponse> handleDiscodeitException(DiscodeitException exception) {
        int status = exception.getStatus();
        ErrorResponse errorResponse = ErrorResponse.builder()
            .status(status)
            .exceptionType(exception.getExceptionType())
            .code(exception.getCode())
            .message(exception.getMessage())
            .details(exception.getDetails())
            .timestamp(exception.getTimestamp())
            .build();
        return ResponseEntity.status(status).body(errorResponse);
    }
}
