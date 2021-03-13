package net.project.studyez.user_profile;

import android.net.Uri;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.HashMap;
import java.util.Map;

public class UserProfileInteractor implements UserProfileContract.Interactor{

    private UserProfileContract.onGetInfoListener onGetInfoListener;
    private UserProfileContract.onSetInfoListener onSetInfoListener;
    private final FirebaseFirestore fStore;
    private final FirebaseAuth fAuth;
    private final FirebaseUser fUser;
    private Query query;
    private DocumentReference docRef;
    private User user;
    private final Map<String, Object> map = new HashMap<>();

    public UserProfileInteractor(){
        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        fUser = fAuth.getCurrentUser();
    }

    public UserProfileInteractor(UserProfileContract.onGetInfoListener onGetInfoListener, UserProfileContract.onSetInfoListener onSetInfoListener){
        this.onGetInfoListener = onGetInfoListener;
        this.onSetInfoListener = onSetInfoListener;
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
    public void setUserProfileImageToFirebase(String stringUri) {
        Uri imageUriParse = Uri.parse(stringUri);
        //Changing user profile picture in firebase to the new picture selected
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setPhotoUri(imageUriParse).build();
        fUser.updateProfile(profileUpdates);

        //You can store new user information here
        docRef = fStore.collection("users")
                .document(fUser.getUid());
        map.put("profileImage", stringUri);
        docRef.update(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(!task.isSuccessful()){
                    onSetInfoListener.onSetInfoFailure("Uh Oh, Problem saving this image!");
                }
                else{
                    onSetInfoListener.onSetInfoSuccess("Successfully Added Profile Image!");
                }
            }
        });
    }
}
