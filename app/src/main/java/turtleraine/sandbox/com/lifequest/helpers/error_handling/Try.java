package turtleraine.sandbox.com.lifequest.helpers.error_handling;

import java.util.function.Supplier;

public class Try<T> {

    public TryResult<T> Try(Supplier<T> functionToTry) {

        try {
            T functionResult = functionToTry.get();
            Success<T> success = new Success<>(functionResult);

            return success;
        } catch (Exception e) {
            return new Failure(e);
        }
    }
}
