package org.ballerinalang.net.grpc.exception;

/**
 * Thrown to indicate that the requested field type is not supported.
 *
 */
public class UnsupportedFieldTypeException extends RuntimeException {

    /**
     * Constructs an UnsupportedFieldTypeException with the specified detail message.
     *
     * @param message the detail message
     */
    public UnsupportedFieldTypeException(String message) {
        super(message);
    }

    /**
     * Constructs a new exception with the specified detail message and cause.
     *
     * @param  message the detail message
     * @param  cause the cause
     */
    public UnsupportedFieldTypeException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new exception with the specified cause.
     *
     * @param  cause the cause
     */
    public UnsupportedFieldTypeException(Throwable cause) {
        super(cause);
    }
}
