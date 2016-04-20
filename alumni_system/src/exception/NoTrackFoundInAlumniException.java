package exception;

/**
 * Created by meranote on 4/11/2016 AD.
 */
public class NoTrackFoundInAlumniException extends RuntimeException {

    public NoTrackFoundInAlumniException() {
        super("No track found in alumni");
    }

}
