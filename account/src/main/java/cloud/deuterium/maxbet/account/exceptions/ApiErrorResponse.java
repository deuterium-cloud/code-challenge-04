package cloud.deuterium.maxbet.account.exceptions;

public record ApiErrorResponse(
        int errorCode,
        String description) {
}
