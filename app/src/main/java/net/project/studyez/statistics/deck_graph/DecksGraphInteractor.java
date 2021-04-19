package net.project.studyez.statistics.deck_graph;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import net.project.studyez.home.quickStudy.QuickStudySession;

import java.util.ArrayList;
import java.util.List;

public class DecksGraphInteractor implements DecksGraphContract.Interactor{

    private DecksGraphContract.onDataCollectionListener onDataCollectionListener;
    private final FirebaseFirestore fStore;
    private final FirebaseAuth fAuth;
    private final FirebaseUser fUser;
    private DocumentReference docRef;
    private DecksStudied decksStudied;

    public DecksGraphInteractor(){
        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        fUser = fAuth.getCurrentUser();
    }

    public DecksGraphInteractor(DecksGraphContract.onDataCollectionListener onDataCollectionListener){
        this.onDataCollectionListener = onDataCollectionListener;
        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        fUser = fAuth.getCurrentUser();
    }

    @Override
    public void getWeeklyDataFromFirebase(String startDate) {
        queryAllDeckStudiedInstances();
//        docRef = fStore
//                .collection("users")
//                .document(fUser.getUid())
//                .collection("statistics")
//                .document("deckGraph")
//                .collection("weekly")
//                .document(startDate);
//        docRef.get().addOnSuccessListener(documentSnapshot -> {
//            if(documentSnapshot.exists()) {
//                decksStudied = documentSnapshot.toObject(DecksStudied.class);
//                onDataCollectionListener.onDataSuccess(decksStudied);
//            }
//            else {
//                onDataCollectionListener.onDataFailure("Failed to retrieve data!");
//            }
//        });
    }

    private void queryAllDeckStudiedInstances(){
        List<QuickStudySession> quickStudySessionList = new ArrayList<QuickStudySession>();

        fStore.collection("users")
                .document(fUser.getUid())
                .collection("studySessions")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<DocumentSnapshot> myListOfDocuments = task.getResult().getDocuments();
                            QuerySnapshot querySnapshot = task.getResult();
                            int count = querySnapshot.size();
                            Log.e("RESULT", "// COUNT - " + count);
                            for (DocumentSnapshot document: querySnapshot.getDocuments()) {
                                Log.e("DOC ID", "// ID - " + document.getId());
                            }
//                            int count = 0;
//                            for (DocumentSnapshot document : task.getResult()) {
//                                //decksStudied = document.toObject(DecksStudied.class);
//                                //decksStudied.setDeckTimesList(task.getResult().getDocuments().get(count).toString());
//                                Log.e("RESULT", "// COUNT - " + document.getReference());
//                                //quickStudySessionList.add(document.get());
//                                count++;
//                            }
                        } else {
                            Log.e("TAG", "Error getting documents: ", task.getException());
                        }
                    }
                });
//        fStore.collection("users")
//                .document(fUser.getUid())
//                .collection("studySessions")
    }
}
