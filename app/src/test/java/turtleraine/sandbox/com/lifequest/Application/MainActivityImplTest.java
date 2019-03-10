package turtleraine.sandbox.com.lifequest.Application;

import android.content.Intent;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import turtleraine.sandbox.com.lifequest.Factories.IntentFactory;
import turtleraine.sandbox.com.lifequest.components.MainMenu.MainMenuQtn;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class MainActivityImplTest extends DaggerTest {



    @Mock
    MainActivity mockQtn;

    @Mock
    Intent mockMainMenuIntent;

    MainActivityImpl subject;

    IntentFactory mockIntentFactory;

    @Before
    public void setup() {
        daggerSetup();
        initMocks(this);

        mockIntentFactory = testAppModule.makeIntentFactory();

        subject = new MainActivityImpl();

        subject.intentFactory = mockIntentFactory;

        when(mockIntentFactory.makeIntent(mockQtn, MainMenuQtn.class)).thenReturn(mockMainMenuIntent);
    }

    @Test
    public void mainActivityStartsMainMenu(){
        subject.onCreate(null, mockQtn);

        verify(mockQtn).startActivity(mockMainMenuIntent);
    }
}