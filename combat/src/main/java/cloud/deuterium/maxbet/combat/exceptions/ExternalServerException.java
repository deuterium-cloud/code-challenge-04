package cloud.deuterium.maxbet.combat.exceptions;

/**
 * Created by Milan Stojkovic 06-Apr-2024
 */
public class ExternalServerException extends RuntimeException{
    public ExternalServerException(String message) {
        super(message);
    }
}
