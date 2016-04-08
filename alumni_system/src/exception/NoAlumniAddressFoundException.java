package exception;

/**
 * Created by meranote on 4/6/2016 AD.
 */
public class NoAlumniAddressFoundException extends RuntimeException {

    public NoAlumniAddressFoundException() {
        super("No alumni address found");
    }

}
