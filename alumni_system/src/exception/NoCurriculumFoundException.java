package exception;

/**
 * Created by meranote on 4/12/2016 AD.
 */
public class NoCurriculumFoundException extends RuntimeException {

    public NoCurriculumFoundException() {
        super("No curriculum found");
    }

}
