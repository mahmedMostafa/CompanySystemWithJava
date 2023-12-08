package com.salama.company.Multinational.Company.errors;


import com.salama.company.Multinational.Company.errors.exceptions.EmailAlreadyTakenException;
import com.salama.company.Multinational.Company.errors.exceptions.EmployeeNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Path;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Set;

@ControllerAdvice
public class DefaultExceptionHandler {

    @ExceptionHandler(EmployeeNotFoundException.class)
    public ResponseEntity<ApiError> handleException(EmployeeNotFoundException exception, HttpServletRequest request) {

        ApiError apiError = new ApiError(
                request.getRequestURI(),
                exception.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now(),
                null
        );

        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmailAlreadyTakenException.class)
    public ResponseEntity<ApiError> handleException(EmailAlreadyTakenException exception, HttpServletRequest request) {

        ApiError apiError = new ApiError(
                request.getRequestURI(),
                exception.getMessage(),
                HttpStatus.NOT_FOUND.value(),
                LocalDateTime.now(),
                null
        );

        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InsufficientAuthenticationException.class)
    public ResponseEntity<ApiError> handleException(InsufficientAuthenticationException exception, HttpServletRequest request) {

        ApiError apiError = new ApiError(
                request.getRequestURI(),
                "User is not authenticated to access this URL",
                HttpStatus.FORBIDDEN.value(),
                LocalDateTime.now(),
                null
        );

        return new ResponseEntity<>(apiError, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiError> handleException(BadCredentialsException exception, HttpServletRequest request) {

        ApiError apiError = new ApiError(
                request.getRequestURI(),
                "Username or password is wrong",
                HttpStatus.UNAUTHORIZED.value(),
                LocalDateTime.now(),
                null
        );

        return new ResponseEntity<>(apiError, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiError> handleException(AuthenticationException exception, HttpServletRequest request) {

        ApiError apiError = new ApiError(
                request.getRequestURI(),
                exception.getMessage(),
                HttpStatus.UNAUTHORIZED.value(),
                LocalDateTime.now(),
                null
        );

        return new ResponseEntity<>(apiError, HttpStatus.UNAUTHORIZED);
    }

    /**
     * called when a validation fails for request params or path params
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiError> handleException(ConstraintViolationException exception, HttpServletRequest request) {

        ApiError apiError = new ApiError(
                request.getRequestURI(),
                exception.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now(),
                null
        );

        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    /**
     * This is triggered when we pass wrong data when we do some validation in the controller or when we try to
     * insert new data into the table and the validation fails
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleException(MethodArgumentNotValidException exception, HttpServletRequest request) {
        /*
         * We either count on spring to get the field name and then append the error message we put,
         * or we just populate the error message right away and guarantee that we include the field in the message itself.
         * I'll go with the first option and in error messages we don't need to add field name in the message
         */
        ObjectError objectError = exception.getBindingResult().getAllErrors().get(0);
        String fieldName = ((FieldError) objectError).getField();
        String errorMessage = objectError.getDefaultMessage();
        ApiError apiError = new ApiError(
                request.getRequestURI(),
                String.format("%s %s", fieldName, errorMessage),
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now(),
                null
        );

        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }
//
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<ApiError> handleException(Exception exception, HttpServletRequest request) {
//
//        ApiError apiError = new ApiError(
//                request.getRequestURI(),
//                "YOU FUCKED UP",
//                HttpStatus.INTERNAL_SERVER_ERROR.value(),
//                LocalDateTime.now(),
//                Arrays.toString(exception.getStackTrace())
//        );
//
//        return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
//    }
}
