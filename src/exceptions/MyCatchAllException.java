package exceptions;

public class MyCatchAllException extends Exception {
    public MyCatchAllException() {
        super();
    }

    public MyCatchAllException(String message) {
        super(message);
    }

    public MyCatchAllException(String message, Throwable cause) {
        super(message, cause);
    }

    public MyCatchAllException(Throwable cause) {
        super(cause);
    }

    protected MyCatchAllException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
