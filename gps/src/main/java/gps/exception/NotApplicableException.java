package gps.exception;

public class NotApplicableException extends Exception {

	private static final long serialVersionUID = 1L;

    public NotApplicableException() { super(); }
    public NotApplicableException(String message, Throwable innerException) {
        super(message, innerException);
    }

}