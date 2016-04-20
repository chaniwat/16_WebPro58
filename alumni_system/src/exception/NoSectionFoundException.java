package exception;

/**
 * Created by meranote on 4/11/2016 AD.
 */
public class NoSectionFoundException extends RuntimeException {

    public NoSectionFoundException() {
        super("No work_section found");
    }

}
