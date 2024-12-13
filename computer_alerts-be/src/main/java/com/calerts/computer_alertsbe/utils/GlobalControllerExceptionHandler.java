package com.calerts.computer_alertsbe.utils;

import com.calerts.computer_alertsbe.utils.exceptions.BadRequestException;
import com.calerts.computer_alertsbe.utils.exceptions.InvalidCommentException;
import com.calerts.computer_alertsbe.utils.exceptions.InvalidInputException;
import com.calerts.computer_alertsbe.utils.exceptions.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalControllerExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleNotFoundException(NotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new HttpErrorInfo(HttpStatus.NOT_FOUND, "Not Found", ex.getMessage()));
    }

    @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<Object> handleInvalidInputException(InvalidInputException ex) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(new HttpErrorInfo(HttpStatus.UNPROCESSABLE_ENTITY, "Invalid Input", ex.getMessage()));
    }

    @ExceptionHandler(InvalidCommentException.class)
    public ResponseEntity<Object> handleInvalidCommentException(InvalidCommentException ex) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(new HttpErrorInfo(HttpStatus.UNPROCESSABLE_ENTITY, "Invalid Comment Input", ex.getMessage()));
    }

    //same structure for bad request
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Object> handleBadRequestException(BadRequestException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new HttpErrorInfo(HttpStatus.BAD_REQUEST, "Bad Request", ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGeneralException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new HttpErrorInfo(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error", ex.getMessage()));
    }
}
