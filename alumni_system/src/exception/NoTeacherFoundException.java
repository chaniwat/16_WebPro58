package exception;

/**
 * Created by meranote on 4/11/2016 AD.
 */
public class NoTeacherFoundException extends RuntimeException {

    public NoTeacherFoundException() {
        super("No teacher found");
    }

}
