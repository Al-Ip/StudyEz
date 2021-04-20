package net.project.studyez.user_profile;

import android.net.Uri;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.List;
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
    private int count;

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
        countUserDeckNumber();
        docRef = fStore
                .collection("users")
                .document(fUser.getUid());
        docRef.get().addOnSuccessListener(documentSnapshot -> {
            if(documentSnapshot.exists()) {
                user = documentSnapshot.toObject(User.class);
                onGetInfoListener.onGetInfoSuccess(user, count);
            }
            else {
                onGetInfoListener.onGetInfoFailure("Failed to get user data");
            }
        });
    }

    private void countUserDeckNumber(){
        fStore.collection("users")
            .document(fUser.getUid())
            .collection("decks")
            .document(fUser.getDisplayName())
            .collection("myDecks")
            .get()
            .addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    List<DocumentSnapshot> myListOfDocuments = task.getResult().getDocuments();
                    QuerySnapshot querySnapshot = task.getResult();
                    count = querySnapshot.size();
                    Log.e("RESULT", "// COUNT - " + count);
                } else {
                    Log.e("TAG", "Error getting documents: ", task.getException());
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
        docRef.update(map).addOnCompleteListener(task -> {
            if(!task.isSuccessful()){
                onSetInfoListener.onSetInfoFailure("Uh Oh, Problem saving this image!");
            }
            else{
                onSetInfoListener.onSetInfoSuccess("Successfully Added Profile Image!");
            }
        });
    }

    @Override
    public void updateDescriptionInFirebase(String description) {
        docRef = fStore
                .collection("users")
                .document(fUser.getUid());
        docRef.update("description", description).addOnCompleteListener(task -> {
            if(!task.isSuccessful()){
                onSetInfoListener.onSetInfoFailure("Uh Oh, Problem saving this image!");
            }
            else{
                onSetInfoListener.onSetInfoSuccess("Successfully Updated Description!");
            }
        });
    }

    @Override
    public void updateUsername(String username) {

    }

}
