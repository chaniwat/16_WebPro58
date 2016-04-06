package exception;

/**
 * Created by meranote on 4/6/2016 AD.
 */
public class NoTrackFoundException extends RuntimeException {

    public NoTrackFoundException() {
        super("No track found");
    }

}
