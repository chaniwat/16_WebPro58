package exception;

/**
 * Created by meranote on 4/5/2016 AD.
 */
public class NoUserFoundException extends RuntimeException {

    public NoUserFoundException() {
        super("No user found");
    }

}
