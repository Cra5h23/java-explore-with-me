package ru.practicum.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;


/**
 * @author Nikolay Radzivon
 * @Date 25.06.2024
 */
@Component
public class ErrorResponse {
    public static ResponseEntity<Map<String, Object>> makeErrorResponse(
            WebRequest webRequest, HttpStatus status) {
        Map<String, Object> response = new LinkedHashMap<>();

        response.put("reason", status.getReasonPhrase());
        response.put("status", status);
        String string = Objects.requireNonNull(webRequest.getAttribute(
                "org.springframework.boot.web.servlet.error.DefaultErrorAttributes.ERROR", 0)).toString();

        String substring = string.substring(string.indexOf(":") + 2);
        response.put("message", substring);
        response.put("timestamp", LocalDateTime.now());

        response.put("path", Objects.requireNonNull(webRequest.getAttribute(
                "org.springframework.web.util.ServletRequestPathUtils.PATH", 0)).toString());

        return ResponseEntity.status(status)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }
}
