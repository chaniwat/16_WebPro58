package exception;

/**
 * Created by meranote on 4/11/2016 AD.
 */
public class NoStaffFoundException extends RuntimeException {

    public NoStaffFoundException() {
        super("No staff found");
    }

}
