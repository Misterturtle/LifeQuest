package turtleraine.sandbox.com.lifequest.utils;

import android.view.View;

import org.mockito.stubbing.Stubber;

import java.util.function.Consumer;

import static org.mockito.Mockito.doAnswer;

public class FnHelper {
    public static Stubber callThroughConsumerWith(Object paramterValue){
        return doAnswer(invocation -> {
            invocation.getArgumentAt(0, Consumer.class).accept(paramterValue);
            return null;
        });
    }

    public static Stubber callThroughFocusChangeListenerWith(View view, boolean hasFocus){
        return doAnswer(invocation -> {
            invocation.getArgumentAt(0, View.OnFocusChangeListener.class).onFocusChange(view, hasFocus);
            return null;
        });
    }
}
