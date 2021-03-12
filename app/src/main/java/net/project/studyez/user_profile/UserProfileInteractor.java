package net.project.studyez.user_profile;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class UserProfileInteractor implements UserProfileContract.Interactor{

    private UserProfileContract.onGetInfoListener onGetInfoListener;
    private final FirebaseFirestore fStore;
    private final FirebaseAuth fAuth;
    private final FirebaseUser fUser;
    private Query query;
    private DocumentReference docRef;
    private User user;

    public UserProfileInteractor(){
        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        fUser = fAuth.getCurrentUser();
    }

    public UserProfileInteractor(UserProfileContract.onGetInfoListener onGetInfoListener){
        this.onGetInfoListener = onGetInfoListener;
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
}
