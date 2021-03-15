package net.project.studyez.study_session;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import net.project.studyez.home.quickStudy.QuickStudySession;

import static net.project.studyez.flashcard.FlashCardInteractor.quickStudySessionId;
import static net.project.studyez.flashcard.FlashCardInteractor.studySessionId;

public class StudySessionInteractor implements StudySessionContract.interactor {

    private StudySessionContract.onGetStats onGetStats;
    private final FirebaseFirestore fStore;
    private final FirebaseAuth fAuth;
    private final FirebaseUser fUser;
    private DocumentReference docRef;

    public StudySessionInteractor(){
        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        fUser = fAuth.getCurrentUser();
    }

    public StudySessionInteractor(StudySessionContract.onGetStats onGetStats){
        this.onGetStats = onGetStats;
        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        fUser = fAuth.getCurrentUser();
    }

    @Override
    public void getStudySessionStatisticsFromDatabase() {
        docRef = fStore
                .collection("users")
                .document(fUser.getUid())
                .collection("studySessions")
                .document(studySessionId)
                .collection("quickStudy")
                .document(quickStudySessionId);

        docRef.get().addOnSuccessListener(documentSnapshot -> {
            if(documentSnapshot.exists()){
                QuickStudySession quickStudySession = documentSnapshot.toObject(QuickStudySession.class);
                onGetStats.onGetStatsSuccess(quickStudySession);
            }
            else {
                onGetStats.onGetStatsFailure("Could not find session data");
            }

        });
    }
}
