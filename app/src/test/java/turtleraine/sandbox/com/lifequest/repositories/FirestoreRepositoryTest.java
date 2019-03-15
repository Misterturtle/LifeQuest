package turtleraine.sandbox.com.lifequest.repositories;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import lombok.SneakyThrows;
import turtleraine.sandbox.com.lifequest.Application.DaggerTest;
import turtleraine.sandbox.com.lifequest.entities.TaskEntity;
import turtleraine.sandbox.com.lifequest.entities.UserEntity;
import turtleraine.sandbox.com.lifequest.wrappers.FirestoreWrapper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class FirestoreRepositoryTest extends DaggerTest {

    @Mock
    private DocumentReference mockDocumentReference;

    @Mock
    private DocumentSnapshot mockDocumentSnapshot;

    @Mock
    private Task<DocumentSnapshot> mockDocumentSnapshotTask;

    FirestoreRepository subject;

    FirestoreWrapper firestoreWrapper;

    private final String HARD_CODED_USER = "Turtle";

    @Before
    public void setup() {
        daggerSetup();
        initMocks(this);

        subject = new FirestoreRepository();

        firestoreWrapper = testAppModule.makeFirestoreWrapper();
    }

    @Test
    public void updateUserPersistsTheUserFirestore() {
        String userName = "Turtle";

        when(firestoreWrapper.document("users/" + userName)).thenReturn(mockDocumentReference);

        List<TaskEntity> taskList = Arrays.asList(
                TaskEntity.builder().title("First Task").description("First Description").build(),
                TaskEntity.builder().title("Second Task").description("Second Description").build()
        );
        UserEntity user = new UserEntity(userName, taskList);

        subject.updateUser(user);

        verify(mockDocumentReference).set(user);
    }

    @SneakyThrows
    @Test
    public void getUserRetrievesTheUserFromFirestoreSuccessfully() {
        String userName = "Turtle";
        UserEntity expectedUserEntity = new UserEntity(userName, Collections.singletonList(TaskEntity.builder().title("Second Task").description("Second Description").build()));
        ArgumentCaptor<OnCompleteListener> captor = ArgumentCaptor.forClass(OnCompleteListener.class);

        when(firestoreWrapper.document("users/" + userName)).thenReturn(mockDocumentReference);
        when(mockDocumentReference.get()).thenReturn(mockDocumentSnapshotTask);
        when(mockDocumentSnapshotTask.isSuccessful()).thenReturn(true);
        when(mockDocumentSnapshotTask.getResult()).thenReturn(mockDocumentSnapshot);
        when(mockDocumentSnapshot.toObject(UserEntity.class)).thenReturn(expectedUserEntity);
        when(mockDocumentSnapshot.getString("name")).thenReturn("not null");

        CompletableFuture<UserEntity> result = subject.getUser(userName);

        verify(mockDocumentSnapshotTask).addOnCompleteListener(captor.capture());
        captor.getValue().onComplete(mockDocumentSnapshotTask);
        assertSame(expectedUserEntity, result.get());
    }

    @SneakyThrows
    @Test
    public void getUserCreatesABlankUserWhenFirebaseHasNoUser() {
        String userName = "Turtle";
        UserEntity expectedUserEntity = new UserEntity(userName, Collections.emptyList());
        ArgumentCaptor<OnCompleteListener> captor = ArgumentCaptor.forClass(OnCompleteListener.class);

        when(firestoreWrapper.document("users/" + userName)).thenReturn(mockDocumentReference);
        when(mockDocumentReference.get()).thenReturn(mockDocumentSnapshotTask);
        when(mockDocumentSnapshotTask.isSuccessful()).thenReturn(true);
        when(mockDocumentSnapshotTask.getResult()).thenReturn(mockDocumentSnapshot);
        when(mockDocumentSnapshot.getString("name")).thenReturn(null);

        CompletableFuture<UserEntity> result = subject.getUser(userName);

        verify(mockDocumentSnapshotTask).addOnCompleteListener(captor.capture());
        captor.getValue().onComplete(mockDocumentSnapshotTask);
        assertEquals(expectedUserEntity, result.get());
    }

    @SneakyThrows
    @Test
    public void getUserFailsToRetrievesTheUserFromFirestore() {
        String userName = "Turtle";
        TaskEntity expectedTaskEntity = TaskEntity.builder()
                .title("First Task")
                .build();

        UserEntity expectedUserEntity = UserEntity.builder()
                .name(userName)
                .taskList(Collections.singletonList(expectedTaskEntity))
                .build();

        ArgumentCaptor<OnCompleteListener> captor = ArgumentCaptor.forClass(OnCompleteListener.class);

        when(firestoreWrapper.document("users/" + userName)).thenReturn(mockDocumentReference);
        when(mockDocumentReference.get()).thenReturn(mockDocumentSnapshotTask);
        when(mockDocumentSnapshotTask.isSuccessful()).thenReturn(false);
        when(mockDocumentSnapshotTask.getResult()).thenReturn(mockDocumentSnapshot);
        when(mockDocumentSnapshot.toObject(UserEntity.class)).thenReturn(expectedUserEntity);

        CompletableFuture<UserEntity> result = subject.getUser(userName);

        verify(mockDocumentSnapshotTask).addOnCompleteListener(captor.capture());
        captor.getValue().onComplete(mockDocumentSnapshotTask);
        assertSame(true, result.isCompletedExceptionally());
    }

    @SneakyThrows
    @Test
    public void setTasks_setsTheGivenTaskAsTheHardCodedUsersTaskList() {
        when(firestoreWrapper.document("users/" + HARD_CODED_USER)).thenReturn(mockDocumentReference);

        List<TaskEntity> taskList = Arrays.asList(
                TaskEntity.builder().title("First Task").build(),
                TaskEntity.builder().title("Second Task").build()
        );
        UserEntity user = new UserEntity(HARD_CODED_USER, taskList);

        subject.setTasks(taskList);

        verify(mockDocumentReference).set(user);
    }

    @SneakyThrows
    @Test
    public void getTasks_retrievesTheTaskListForTheHardCodedUser() {
        TaskEntity expectedTaskEntity = TaskEntity.builder()
                .title("First Task")
                .build();
        List<TaskEntity> expectedTaskList = Collections.singletonList(expectedTaskEntity);

        UserEntity expectedUserEntity = UserEntity.builder()
                .name(HARD_CODED_USER)
                .taskList(expectedTaskList)
                .build();

        ArgumentCaptor<OnCompleteListener> captor = ArgumentCaptor.forClass(OnCompleteListener.class);

        when(firestoreWrapper.document("users/" + HARD_CODED_USER)).thenReturn(mockDocumentReference);
        when(mockDocumentReference.get()).thenReturn(mockDocumentSnapshotTask);
        when(mockDocumentSnapshotTask.isSuccessful()).thenReturn(true);
        when(mockDocumentSnapshotTask.getResult()).thenReturn(mockDocumentSnapshot);
        when(mockDocumentSnapshot.getString("name")).thenReturn("not null");
        when(mockDocumentSnapshot.toObject(UserEntity.class)).thenReturn(expectedUserEntity);

        CompletableFuture<List<TaskEntity>> result = subject.getTasks();

        verify(mockDocumentSnapshotTask).addOnCompleteListener(captor.capture());
        captor.getValue().onComplete(mockDocumentSnapshotTask);
        assertEquals(expectedTaskList, result.get());
    }

}