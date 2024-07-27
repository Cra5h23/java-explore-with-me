package ru.practicum.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolationException;

/**
 * @author Nikolay Radzivon
 * @Date 25.06.2024
 */
@ControllerAdvice
@Slf4j
public class ErrorHandler {

    @ExceptionHandler
    public ResponseEntity<?> handlerDataIntegrityViolationException(DataIntegrityViolationException e,
                                                                    WebRequest webRequest) {
        log.info("Ошибка работы с пользователями: ", e);

        return ErrorResponse.makeErrorResponse(webRequest, HttpStatus.CONFLICT);
    }

    @ExceptionHandler
    public ResponseEntity<?> handlerMethodArgumentNotValidException(MethodArgumentNotValidException e,
                                                                    WebRequest webRequest) {
        log.info("Ошибка составления запроса: ", e);

        return ErrorResponse.makeErrorResponse(webRequest, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<?> handlerMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e,
                                                                        WebRequest webRequest) {
        log.info("Ошибка параметра запроса: ", e);

        return ErrorResponse.makeErrorResponse(webRequest, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<?> handlerNotFoundUserException(NotFoundUserException e, WebRequest webRequest) {
        log.info("Ошибка получения пользователя: ", e);

        return ErrorResponse.makeErrorResponse(webRequest, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<?> handlerEventDateValidateException(EventDateValidateException e, WebRequest webRequest) {
        log.info("Неправильно введена дата начала события: ", e);

        return ErrorResponse.makeErrorResponse(webRequest, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<?> handlerMissingServletRequestParameterException(MissingServletRequestParameterException e,
                                                                            WebRequest webRequest) {
        log.info("Отсутствует параметр запроса: ", e);

        return ErrorResponse.makeErrorResponse(webRequest, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<?> handlerNotFoundEventException(NotFoundEventException e, WebRequest webRequest) {
        log.info("Ошибка работы с событиями: ", e);

        return ErrorResponse.makeErrorResponse(webRequest, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<?> handlerNotFoundCategoryException(NotFoundCategoryException e, WebRequest webRequest) {
        log.info("Ошибка работы с категориями ", e);

        return ErrorResponse.makeErrorResponse(webRequest, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<?> handlerConflictEventException(ConflictEventException e, WebRequest webRequest) {
        log.info("Ошибка работы с событиями: ", e);

        return ErrorResponse.makeErrorResponse(webRequest, HttpStatus.CONFLICT);
    }

    @ExceptionHandler
    public ResponseEntity<?> handlerConflictParticipationRequestException(ConflictParticipationRequestException e,
                                                                          WebRequest webRequest) {
        log.info("Ошибка работы с запросами на участие: ", e);

        return ErrorResponse.makeErrorResponse(webRequest, HttpStatus.CONFLICT);
    }

    @ExceptionHandler
    public ResponseEntity<?> handlerConstraintViolationException(ConstraintViolationException e, WebRequest webRequest) {
        log.info("Неправильный ввод данных: ", e);

        return ErrorResponse.makeErrorResponse(webRequest, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<?> handlerNotFoundLocationException(NotFoundLocationException e, WebRequest webRequest) {
        log.info("Ошибка работы с локациями: ", e);

        return ErrorResponse.makeErrorResponse(webRequest, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<?> handlerBindException(BindException e, WebRequest webRequest) {
        log.info("Ошибка ввода параметров запроса: ", e);

        return ErrorResponse.makeErrorResponse(webRequest, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<?> handlerBadRequestLocationException(BadRequestLocationException e, WebRequest webRequest) {
        log.info("Ошибка работы с локациями: ", e);

        return ErrorResponse.makeErrorResponse(webRequest, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<?> handlerConflictLocationException(ConflictLocationException e, WebRequest webRequest) {
        log.info("Ошибка работы с локациями: ", e);

        return ErrorResponse.makeErrorResponse(webRequest, HttpStatus.CONFLICT);
    }

    @ExceptionHandler
    public ResponseEntity<?> handlerNotFoundCompilationException(NotFoundCompilationException e, WebRequest webRequest) {
        log.info("Ошибка работы с подборками событий: ", e);

        return ErrorResponse.makeErrorResponse(webRequest, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<?> handlerNotFoundParticipationRequestException(NotFoundParticipationRequestException e, WebRequest webRequest) {
        log.info("Ошибка работы с подборками событий: ", e);

        return ErrorResponse.makeErrorResponse(webRequest, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<?> handlerStringToEventSortTypeConverterException(StringToEventSortTypeConverterException e, WebRequest webRequest) {
        log.info("Неправильно введён параметр запроса: ", e);

        return ErrorResponse.makeErrorResponse(webRequest, HttpStatus.BAD_REQUEST);
    }
}
