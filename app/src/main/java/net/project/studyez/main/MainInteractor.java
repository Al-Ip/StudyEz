package net.project.studyez.main;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import net.project.studyez.user_profile.User;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MainInteractor implements MainContract.Interactor{

    private MainContract.onGetInfoListener onGetInfoListener;
    private MainContract.onRegistrationUpdateListener onRegistrationUpdateListener;
    private final FirebaseFirestore fStore;
    private final FirebaseAuth fAuth;
    private final FirebaseUser fUser;
    private Query query;
    private DocumentReference docRef;
    private User user;

    public MainInteractor(){
        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        fUser = fAuth.getCurrentUser();
    }

    public MainInteractor(MainContract.onGetInfoListener onGetInfoListener, MainContract.onRegistrationUpdateListener onRegistrationUpdateListener){
        this.onGetInfoListener = onGetInfoListener;
        this.onRegistrationUpdateListener = onRegistrationUpdateListener;
        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        fUser = fAuth.getCurrentUser();
    }

    @Override
    public void getUserDetailsFromFirebase() {
        docRef = fStore
                .collection("users")
                .document(fUser.getUid());
        docRef.get().addOnSuccessListener(documentSnapshot -> {
            if(documentSnapshot.exists()) {
                user = documentSnapshot.toObject(User.class);
                onGetInfoListener.onGetInfoSuccess(user);
            }
            else {
                onGetInfoListener.onGetInfoFailure("Failed to get user data");
            }
        });
    }

    @Override
    public void setUsernameForAccount(String username) {
        Map<String, Object> map = new HashMap<>();
        query = fStore.collection("users").whereEqualTo("username", username);
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(DocumentSnapshot documentSnapshot : Objects.requireNonNull(task.getResult())){
                        String user = documentSnapshot.getString("username");
                        assert user != null;
                        if(user.equals(username)){
                            onGetInfoListener.onGetInfoFailure("Username Already Exists. Try Another!");
                        }
                    }
                }
                if(task.getResult().size() == 0 ){
                    //You can store new user information here
                    docRef = fStore.collection("users")
                            .document(fUser.getUid());
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
}
