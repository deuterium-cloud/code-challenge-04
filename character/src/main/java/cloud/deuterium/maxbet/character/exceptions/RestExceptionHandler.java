package cloud.deuterium.maxbet.character.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleNotFoundException(NotFoundException e) {
        return ResponseEntity
                .status(NOT_FOUND)
                .body(new ApiErrorResponse(NOT_FOUND.value(), e.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleRequestNotValidException(MethodArgumentNotValidException e) {

        List<String> errors = new ArrayList<>();
        e.getBindingResult()
                .getFieldErrors().forEach(error -> errors.add(error.getField() + ": " + error.getDefaultMessage()));
        e.getBindingResult()
                .getGlobalErrors() //Global errors are not associated with a specific field but are related to the entire object being validated.
                .forEach(error -> errors.add(error.getObjectName() + ": " + error.getDefaultMessage()));

        String message = "Validation of request failed: %s".formatted(String.join(", ", errors));
        return ResponseEntity
                .status(BAD_REQUEST)
                .body(new ApiErrorResponse(BAD_REQUEST.value(), message));
    }

    @ExceptionHandler(DuplicateException.class)
    public ResponseEntity<ApiErrorResponse> handleDuplicateException(DuplicateException e) {
        return ResponseEntity
                .status(CONFLICT)
                .body(new ApiErrorResponse(CONFLICT.value(), e.getMessage()));
    }

    @ExceptionHandler(NotAuthorizedException.class)
    public ResponseEntity<ApiErrorResponse> handleNotAuthorizedException(NotAuthorizedException e) {
        return ResponseEntity
                .status(UNAUTHORIZED)
                .body(new ApiErrorResponse(UNAUTHORIZED.value(), e.getMessage()));
    }

    @ExceptionHandler({AccessDeniedException.class, ForbiddenException.class})
    public ResponseEntity<ApiErrorResponse> handleForbiddenException(Exception e) {
        return ResponseEntity
                .status(FORBIDDEN)
                .body(new ApiErrorResponse(FORBIDDEN.value(), e.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleUnknownException(Exception e) {
        return ResponseEntity
                .status(INTERNAL_SERVER_ERROR)
                .body(new ApiErrorResponse(INTERNAL_SERVER_ERROR.value(), e.getMessage()));
    }

}
