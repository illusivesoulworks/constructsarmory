package c4.conarm.lib;

public class ConArmAPIException extends RuntimeException {

    public ConArmAPIException(String message) {
        super("[ConArm API] " + message);
    }
}
