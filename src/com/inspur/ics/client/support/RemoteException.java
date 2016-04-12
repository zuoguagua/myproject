package com.inspur.ics.client.support;

/**
 * IVirtual异常类.
 */
public class RemoteException extends RuntimeException {
    /**
     * Constructor.
     */
    public RemoteException() {

    }

    /**
     * Constructor with message.
     * @param message
     *            exception message
     */
    public RemoteException(String message) {
        super(message);
    }

    /**
     * Constructor with cause.
     * @param t the cause
     */
    public RemoteException(Throwable t) {
        super(t);
    }

    /**
     * Constructor with message and cause.
     * @param message error detail
     * @param t the cause
     */
    public RemoteException(String message, Throwable t) {
        super(message, t);
    }
}
