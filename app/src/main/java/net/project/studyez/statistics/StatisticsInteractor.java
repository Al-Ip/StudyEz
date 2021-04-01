package net.project.studyez.statistics;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import net.project.studyez.user_profile.User;

import java.util.HashMap;
import java.util.Map;

public class StatisticsInteractor implements StatisticsContract.Interactor {

    private final FirebaseFirestore fStore;
    private final FirebaseAuth fAuth;
    private final FirebaseUser fUser;
    private Query query;
    private DocumentReference docRef;
    private CollectionReference colRef;
    private final User user = new User();
    private final Map<String, Object> map = new HashMap<>();

    public StatisticsInteractor(){
        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        fUser = fAuth.getCurrentUser();
    }

//    public StatisticsInteractor(RegistrationContract.onRegistrationListener onRegistrationListener,
//                                  RegistrationContract.onRegistrationUpdateListener onRegistrationUpdateListener){
//        this.mOnRegistrationListener = onRegistrationListener;
//        this.onRegistrationUpdateListener = onRegistrationUpdateListener;
//        fStore = FirebaseFirestore.getInstance();
//        fAuth = FirebaseAuth.getInstance();
//        fUser = fAuth.getCurrentUser();
//    }
}
