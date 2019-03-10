package helpers.error_handling;

public class Failure implements TryResult {

    Exception error;

    public Failure(Exception e) {
        this.error = e;
    }
}
