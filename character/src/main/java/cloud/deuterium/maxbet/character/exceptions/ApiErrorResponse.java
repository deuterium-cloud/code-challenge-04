package cloud.deuterium.maxbet.character.exceptions;

public record ApiErrorResponse(
        int errorCode,
        String description) {
}
