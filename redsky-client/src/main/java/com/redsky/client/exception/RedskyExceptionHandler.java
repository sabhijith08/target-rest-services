package com.redsky.client.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.redsky.client.pojo.Response;

@ControllerAdvice
@RestController
public class RedskyExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ProductNotFoundException.class)
    public final ResponseEntity<Response> handleProductNotFoundException(final ProductNotFoundException ex,
            final WebRequest request) {
        final Response exceptionResponse = new Response(ex.getMessage(), ex.getCode());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PriceNotFoundException.class)
    public final ResponseEntity<Response> handlePriceNotFoundException(final PriceNotFoundException ex,
            final WebRequest request) {
        final Response exceptionResponse = new Response(ex.getMessage(), ex.getCode());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidProductIdException.class)
    public final ResponseEntity<Response> handleInvalidIdException(final InvalidProductIdException ex,
            final WebRequest request) {
        final Response exceptionResponse = new Response(ex.getMessage(), ex.getCode());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceValidationException.class)
    public final ResponseEntity<Response> handleInvalidRequestException(final ResourceValidationException ex,
            final WebRequest request) {
        final Response exceptionResponse = new Response(ex.getMessage(), ex.getCode());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(JsonParseException.class)
    public final ResponseEntity<Response> handleInvalidIdException(final JsonParseException ex,
            final WebRequest request) {
        final Response exceptionResponse = new Response(ex.getMessage(), 500);
        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Response> handleGenericException(final Exception ex, final WebRequest request) {
        final Response exceptionResponse = new Response(ex.getMessage(), 500);
        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
