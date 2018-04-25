package exceptions;

public class NaamTeKortException extends RuntimeException {

    public NaamTeKortException() {
    }
    
    public NaamTeKortException(String message, Throwable cause)
    {
        super(message, cause);
    }
    
    public NaamTeKortException(Throwable cause)
    {
        super(cause);
    }

    public NaamTeKortException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
    public NaamTeKortException(String msg) {
        super(msg);
    }
}
    


    