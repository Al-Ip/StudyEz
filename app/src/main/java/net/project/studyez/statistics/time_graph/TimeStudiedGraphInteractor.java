package net.project.studyez.statistics.time_graph;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class TimeStudiedGraphInteractor implements TimeStudiedGraphContract.Interactor {

    private TimeStudiedGraphContract.onDataCollectionListener onDataCollectionListener;
    private final FirebaseFirestore fStore;
    private final FirebaseAuth fAuth;
    private final FirebaseUser fUser;
    private DocumentReference docRef;
    private TimeStudied timeStudied;

    public TimeStudiedGraphInteractor(){
        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        fUser = fAuth.getCurrentUser();
    }

    public TimeStudiedGraphInteractor(TimeStudiedGraphContract.onDataCollectionListener onDataCollectionListener){
        this.onDataCollectionListener = onDataCollectionListener;
        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        fUser = fAuth.getCurrentUser();
    }


    @Override
    public void getWeeklyDataFromFirebase(String startDate) {

        docRef = fStore
                .collection("users")
                .document(fUser.getUid())
                .collection("statistics")
                .document("timeGraph")
                .collection("weekly")
                .document(startDate);
        docRef.get().addOnSuccessListener(documentSnapshot -> {
            if(documentSnapshot.exists()) {
                timeStudied = documentSnapshot.toObject(TimeStudied.class);
                onDataCollectionListener.onDataSuccess(timeStudied);
            }
            else {
                onDataCollectionListener.onDataFailure("Failed to retrieve data!");
            }
        });

    }
}
