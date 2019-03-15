package turtleraine.sandbox.com.lifequest.common.exceptions;

public class FirestoreGetFailure extends Exception {

    String documentPath;

    public FirestoreGetFailure(String documentPath) {
        this.documentPath = documentPath;
    }
}
