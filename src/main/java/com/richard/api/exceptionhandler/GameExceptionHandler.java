package com.richard.api.exceptionhandler;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.richard.api.services.exception.NotFoundGameException;
import com.richard.api.services.exception.NotPlayerTurnException;
import com.richard.api.services.exception.PositionAlreadyFilledException;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ControllerAdvice
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class GameExceptionHandler extends ResponseEntityExceptionHandler {

    private final MessageSource messageSource;

    @ExceptionHandler(NotPlayerTurnException.class)
    protected ResponseEntity<Object> handleNotPlayerTurnException(NotPlayerTurnException ex, WebRequest request) {

        String message = messageSource.getMessage("message.notplayerturn", null, LocaleContextHolder.getLocale());
        MessageDto messageDto = MessageDto.builder()
                                    .msg(message)
                                    .build();

        return handleExceptionInternal(ex, messageDto, new HttpHeaders(), BAD_REQUEST, request);
    }

    @ExceptionHandler(NotFoundGameException.class)
    protected ResponseEntity<Object> handleNotFoundGameException(NotFoundGameException ex, WebRequest request) {

        String message = messageSource.getMessage("message.notfoundgame", null, LocaleContextHolder.getLocale());
        MessageDto messageDto = MessageDto.builder()
            .msg(message)
            .build();

        return handleExceptionInternal(ex, messageDto, new HttpHeaders(), BAD_REQUEST, request);
    }

    @ExceptionHandler(PositionAlreadyFilledException.class)
    protected ResponseEntity<Object> handlePositionAlreadyFilledException(PositionAlreadyFilledException ex, WebRequest request) {

        String message = messageSource.getMessage("message.positionalreadyfilled", null, LocaleContextHolder.getLocale());
        MessageDto messageDto = MessageDto.builder()
            .msg(message)
            .build();

        return handleExceptionInternal(ex, messageDto, new HttpHeaders(), BAD_REQUEST, request);
    }

    @Getter
    @Builder
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class MessageDto {
        private String msg;
    }
}
