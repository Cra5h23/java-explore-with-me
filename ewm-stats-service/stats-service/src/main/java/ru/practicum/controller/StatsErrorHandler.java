package ru.practicum.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author Nikolay Radzivon
 * @Date 19.06.2024
 */
@ControllerAdvice
@Slf4j
public class StatsErrorHandler {

    @ExceptionHandler
    public ResponseEntity<?> handlerConstraintViolationException(ConstraintViolationException e, WebRequest webRequest) {
        log.info("Параметр не соответствует требованиям: ", e);
        return makeErrorResponse(webRequest, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<?> handlerMissingServletRequestParameterException(MissingServletRequestParameterException e,
                                                                            WebRequest webRequest) {
        log.info("Не указан параметр запроса: ", e);
        return makeErrorResponse(webRequest, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<?> handlerBindException(BindException e, WebRequest webRequest) {
        log.info("Не указан параметр запроса: ", e);
        return makeErrorResponse(webRequest, HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<Map<String, Object>> makeErrorResponse(
            WebRequest webRequest, HttpStatus status) {
        Map<String, Object> response = new LinkedHashMap<>();

        response.put("reason", status.getReasonPhrase());
        response.put("status", status);
        String string = Objects.requireNonNull(webRequest.getAttribute(
                "org.springframework.boot.web.servlet.error.DefaultErrorAttributes.ERROR", 0)).toString();

        String substring = string.substring(string.indexOf(":") + 2);
        response.put("message", substring);
        response.put("timestamp", LocalDateTime.now());

        return ResponseEntity.status(status).body(response);
    }
}
