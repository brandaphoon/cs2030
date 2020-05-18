import java.lang.Exception;

public class NoSuchRecordException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = -1671635748582390619L;

    NoSuchRecordException(String msg) {
        super(msg);
    }
    
}