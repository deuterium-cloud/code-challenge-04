package cloud.deuterium.maxbet.combat.exceptions;

public record ApiErrorResponse(
        int errorCode,
        String description) {
}
