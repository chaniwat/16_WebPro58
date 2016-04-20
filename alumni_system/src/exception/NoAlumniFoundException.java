package exception;

/**
 * Created by meranote on 4/6/2016 AD.
 */
public class NoAlumniFoundException extends RuntimeException {

    public NoAlumniFoundException() {
        super("No alumni found");
    }

}
