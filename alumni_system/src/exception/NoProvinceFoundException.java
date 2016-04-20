package exception;

/**
 * Created by meranote on 4/6/2016 AD.
 */
public class NoProvinceFoundException extends RuntimeException {

    public NoProvinceFoundException() {
        super("No province found");
    }

}
