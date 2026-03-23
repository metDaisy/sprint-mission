package com.sprint.mission.discodeit.common.exception.advice;

import com.sprint.mission.discodeit.common.exception.code.ErrorCode;
import com.sprint.mission.discodeit.common.exception.custom.APIException;
import com.sprint.mission.discodeit.common.exception.model.APIExceptionModel;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class APIExceptionAdvice {

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
}
