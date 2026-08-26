package com.example.demo.advisor;

import com.example.demo.exception.EntryAlreadyExistsException;
import com.example.demo.exception.EntryNotFoundException;
import com.example.demo.exception.LoginFailedException;
import com.example.demo.exception.TokenRefreshException;
import com.example.demo.util.StandardResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author : Manoj Vitharana <mvitharana@onepeterson.com>
 * @since : 25/03/2024
 **/

@RestControllerAdvice
@Slf4j
public class AppWideExceptionHandler {

    @ExceptionHandler({EntryNotFoundException.class})
    public ResponseEntity<StandardResponse> entryNotFoundHandler(EntryNotFoundException e) {
        log.error(e.getMessage());
        return new ResponseEntity<>(
                new StandardResponse("The requested entry was not found. Please check the provided details and try again", e.getMessage()),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({EntryAlreadyExistsException.class})
    public ResponseEntity<StandardResponse> entryAlreadyExistHandler(EntryAlreadyExistsException e) {
        log.error(e.getMessage());
        return new ResponseEntity<>(
                new StandardResponse("Provided data already registered. Please Check inputs", e.getMessage()),
                HttpStatus.CONFLICT);
    }

    @ExceptionHandler({LoginFailedException.class})
    public ResponseEntity<StandardResponse> loginFailedHandler(LoginFailedException e) {
        log.error(e.getMessage());
        return new ResponseEntity<>(
                new StandardResponse(e.getMessage(), null),
                HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler({TokenRefreshException.class})
    public ResponseEntity<StandardResponse> tokenRefreshFailedHandler(TokenRefreshException e) {
        log.error(e.getMessage());
        return new ResponseEntity<>(
                new StandardResponse(e.getMessage(), null),
                HttpStatus.UNAUTHORIZED);
    }
}
