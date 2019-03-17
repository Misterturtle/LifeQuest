package turtleraine.sandbox.com.lifequest.state_store;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

import turtleraine.sandbox.com.lifequest.common.exceptions.CyclicalUpdate;

public class StateStore {

    private AppState state = AppState.builder().build();
    private List<Subscription> subscriptions = new ArrayList<>();

    protected List<AppState> pendingAppStates = new ArrayList<>();
    private boolean updateCycleIsRunning = false;

    public Subscription subscribe(Consumer<AppState> updateFunction) {
        Subscription subscription = Subscription.builder()
                .updateFunction(updateFunction)
                .lastAppState(state)
                .build();

        subscriptions.add(subscription);
        subscription.updateFunction.accept(state);

        return subscription;
    }


    protected List<Subscription> getSubscriptions() {
        return subscriptions;
    }

    public AppState getAppState() {
        return state;
    }

    public void updateState(Function<AppState, AppState> updateFn) {
        pendingAppStates.add(newAppState);

        if (!updateCycleIsRunning) {
            triggerUpdateCycle();
        }
    }

    private void triggerUpdateCycle() {
        updateCycleIsRunning = true;
        state = pendingAppStates.remove(0);

        subscriptions.stream()
                .filter(subscription -> !subscription.lastAppState.equals(state))
                .forEach(subscription -> {
                    subscription.updateFunction.accept(state);
                    subscription.lastAppState = state;
                });

        updateCycleIsRunning = false;

        if(!pendingAppStates.isEmpty()){
            try{
                triggerUpdateCycle();
            } catch (StackOverflowError e){
                throw new CyclicalUpdate();
            }
        }
    }

}
