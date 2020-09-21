package com.pczech.taskmanager.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice()
public class ControllerAdviceComponent {
    @ExceptionHandler({BadDataException.class})
    public ResponseEntity<Object> badDataExceptionHandler(Exception e, WebRequest request) {
        return new ResponseEntity<>(createBody("Bad data", e.getMessage()), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler({AlreadyExistsException.class})
    public ResponseEntity<Object> alreadyExistsExceptionHandler(Exception e, WebRequest webRequest) {
        return new ResponseEntity<>(createBody("Already exists", e.getMessage()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<Object> notFoundException(Exception e, WebRequest webRequest) {
        return new ResponseEntity<>(createBody("Not found", e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({UnauthorizedException.class})
    public ResponseEntity<Object> unauthorizedExceptionHandler(Exception e, WebRequest webRequest) {
        return new ResponseEntity<>(createBody("Unauthorized", e.getMessage()), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler({BadRequestException.class})
    public ResponseEntity<Object> badRequestExceptionHandler(Exception e, WebRequest webRequest) {
        return new ResponseEntity<>(createBody("Bad request", e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ConstraintViolationException.class})
    public void constraintViolationExceptionHandler(Exception e, ServletWebRequest servletWebRequest) throws IOException {
        servletWebRequest.getResponse().sendError(HttpStatus.UNPROCESSABLE_ENTITY.value(), "Incorrect data");
    }

    private Map<String, String> createBody(String messageTitle, String message) {
        Map<String, String> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now().toString());
        body.put("message", messageTitle + ": " + message);
        return body;
    }
}
