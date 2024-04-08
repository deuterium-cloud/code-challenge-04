package cloud.deuterium.maxbet.combat.exceptions;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.reactive.result.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ExternalServerException.class)
    protected ResponseEntity<ApiErrorResponse> handleExternalServerException(ExternalServerException ex) {
        return ResponseEntity
                .status(500)
                .body(new ApiErrorResponse(500, ex.getMessage()));
    }

    @ExceptionHandler(BadRequestException.class)
    protected ResponseEntity<ApiErrorResponse> handleBadRequestException(BadRequestException ex) {
        return ResponseEntity
                .status(400)
                .body(new ApiErrorResponse(400, ex.getMessage()));
    }

    @ExceptionHandler(ForbiddenException.class)
    protected ResponseEntity<ApiErrorResponse> handleForbiddenException(ForbiddenException ex) {
        return ResponseEntity
                .status(403)
                .body(new ApiErrorResponse(403, ex.getMessage()));
    }

    @ExceptionHandler(NotAuthorizedException.class)
    protected ResponseEntity<ApiErrorResponse> handleNotAuthorizedException(NotAuthorizedException ex) {
        return ResponseEntity
                .status(401)
                .body(new ApiErrorResponse(401, ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ApiErrorResponse> handleException(Exception ex) {
        return ResponseEntity
                .status(500)
                .body(new ApiErrorResponse(500, "Internal Server Error"));
    }
}
