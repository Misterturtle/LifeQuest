package helpers.error_handling;

public class Success<T> implements TryResult<T>{

    T functionResult;

    public Success(T functionResult) {
        this.functionResult = functionResult;
    }

    public T getResult() {
        return functionResult;
    }
}
