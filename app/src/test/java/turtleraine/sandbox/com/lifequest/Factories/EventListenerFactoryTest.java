package turtleraine.sandbox.com.lifequest.Factories;

import com.google.firebase.firestore.EventListener;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static junit.framework.TestCase.assertTrue;

public class EventListenerFactoryTest {

    EventListenerFactory subject;

    @Before
    public void setup() {
        subject = new EventListenerFactory();
    }
    @Test
    public void makeFutureBindedEventListener_completesTheGivenFutureOnSuccess(){
        List<Boolean> success = new ArrayList<>();
        CompletableFuture<String> someFuture = new CompletableFuture<>();
        someFuture.thenAccept(someString -> {
            success.add(true);
        });

        EventListener<String> result = subject.makeFutureBindedEventListener(someFuture);

        result.onEvent("complete", null);

        assertTrue(success.get(0));
    }

}