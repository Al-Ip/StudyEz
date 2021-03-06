package net.project.studyez.registration;

import android.app.Activity;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import net.project.studyez.account.User;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RegistrationInteractor implements RegistrationContract.Interactor {

    private RegistrationContract.onRegistrationListener mOnRegistrationListener;
    private RegistrationContract.onRegistrationUpdateListener onRegistrationUpdateListener;
    private RegistrationContract.onRegistrationAddImageListener onRegistrationAddImageListener;
    private final FirebaseFirestore fStore;
    private final FirebaseAuth fAuth;
    private final FirebaseUser fUser;
    private Query query;
    private DocumentReference docRef;
    private CollectionReference colRef;
    private final User user = new User();
    private final Map<String, Object> map = new HashMap<>();

    public RegistrationInteractor(){
        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        fUser = fAuth.getCurrentUser();
    }

    public RegistrationInteractor(RegistrationContract.onRegistrationListener onRegistrationListener,
                                  RegistrationContract.onRegistrationUpdateListener onRegistrationUpdateListener,
                                  RegistrationContract.onRegistrationAddImageListener onRegistrationAddImageListener){
        this.mOnRegistrationListener = onRegistrationListener;
        this.onRegistrationUpdateListener = onRegistrationUpdateListener;
        this.onRegistrationAddImageListener = onRegistrationAddImageListener;
        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        fUser = fAuth.getCurrentUser();
    }

    @Override
    public void performFirebaseRegistration(Activity activity, String email, String password) {
        user.setEmail(email);
        user.setPassword(password);
        fAuth.createUserWithEmailAndPassword(user.getEmail(), user.getPassword())
                .addOnCompleteListener(task -> {
                    if(!task.isSuccessful()){
                        mOnRegistrationListener.onRegFailure(task.getException().getMessage());
                    }
                    else{
                        docRef = fStore.collection("users")
                                .document(task.getResult().getUser().getUid());
                        docRef.set(user).addOnCompleteListener(task1 -> {
                            if (!task1.isSuccessful()) {
                                mOnRegistrationListener.onRegFailure(task1.getException().getMessage());
                            }
                            else {
                                mOnRegistrationListener.onRegSuccess(task.getResult().getUser());
                            }
                        });
                        //mOnRegistrationListener.onRegSuccess(task.getResult().getUser());
                    }
                });
    }

    @Override
    public void storeUserDetailsInFirebase(Activity activity, String username) {
        //Changing user display name in firebase to the username entered
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(username).build();
        fUser.updateProfile(profileUpdates);

        query = fStore.collection("users").whereEqualTo("username", username);
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(DocumentSnapshot documentSnapshot : Objects.requireNonNull(task.getResult())){
                        String user = documentSnapshot.getString("username");
                        assert user != null;
                        if(user.equals(username)){
                            onRegistrationUpdateListener.onRegUpdateFailure("Username Already Exists. Try Another!");
                        }
                    }
                }
                if(task.getResult().size() == 0 ){
                    //You can store new user information here
                    docRef = fStore.collection("users")
                            .document(fUser.getUid());
                    map.put("id", fUser.getUid());
                    map.put("username", username);
                    docRef.update(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(!task.isSuccessful()){
                                onRegistrationUpdateListener.onRegUpdateFailure(task.getException().getMessage());
                            }
                            else{
                                onRegistrationUpdateListener.onRegUpdateSuccess("Successfully Added Username to Account!");
                            }
                        }
                    });
                }
            }
        });
    }

    @Override
    public void addProfilePictureToDatabase(String imageUri) {
        //You can store new user information here
        docRef = fStore.collection("users")
                .document(fUser.getUid());
        map.put("profileImage", imageUri);
        docRef.update(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(!task.isSuccessful()){
                    onRegistrationAddImageListener.onRegAddImageFailure(task.getException().getMessage());
                }
                else{
                    onRegistrationAddImageListener.onRegAddImageSuccess("Successfully Added Profile Image!");
                }
            }
        });
    }
}
