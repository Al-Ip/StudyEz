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
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import net.project.studyez.home.quickStudy.QuickStudySession;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static java.util.Calendar.getInstance;

public class DecksGraphInteractor implements DecksGraphContract.Interactor{

    private DecksGraphContract.onDataCollectionListener onDataCollectionListener;
    private final FirebaseFirestore fStore;
    private final FirebaseAuth fAuth;
    private final FirebaseUser fUser;
    private Query query;
    private DocumentReference docRef;

    private Date currentDate;
    private String currentDateString;
    private Date lastDayInCurrentDate;
    private String lastDayInCurrentDateString;
    private QuickStudySession quickStudySession;
    private List<String> deckIDList;
    private List<QuickStudySession> quickStudySessionList;

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
        initDatesForCurrentWeek();
    }

    @Override
    public void getWeeklyDataFromFirebase(String startDate) {
        if(isWithinRange(currentDate)) {
            deckIDList = new ArrayList<>();
            fStore.collection("users")
                    .document(fUser.getUid())
                    .collection("studySessions")
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            List<DocumentSnapshot> myListOfDocuments = task.getResult().getDocuments();
                            QuerySnapshot querySnapshot = task.getResult();
                            int count = querySnapshot.size();
                            //Log.e("RESULT", "// COUNT - " + count);
                            for (DocumentSnapshot document : querySnapshot.getDocuments()) {
                                //Log.e("DOC ID", "// ID - " + document.getId());
                                deckIDList.add(document.getId());
                            }
                            queryAllDeckStudiedTime();
                        } else {
                            Log.e("TAG", "Error getting documents: ", task.getException());
                        }
                    });
        }
        else
            Log.e("Deck Graph Error", "- Date not in current Week");
    }

    private void queryAllDeckStudiedTime(){
        quickStudySessionList = new ArrayList<>();
        for (String temp : deckIDList) {
            fStore.collection("users")
                    .document(fUser.getUid())
                    .collection("studySessions")
                    .document(temp)
                    .collection("quickStudy")
                    .whereEqualTo("deckName", temp)
                    .orderBy("dateCreated")
                    .startAt(currentDate)
                    .endAt(lastDayInCurrentDate)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    //Log.e("GET DOCUMENT", document.getId() + " => " + document.getData());
                                    quickStudySession = document.toObject(QuickStudySession.class);
                                    quickStudySessionList.add(quickStudySession);
                                }
                                onDataCollectionListener.onDataSuccess(quickStudySessionList);
                            } else {
                                Log.e("Deck Graph Interactor", "- Error getting documents: ", task.getException());
                            }
                        }
                    });
        }
    }

    public boolean isWithinRange(Date testDate) {
        return !(testDate.before(currentDate) || testDate.after(lastDayInCurrentDate));
    }

    public void initDatesForCurrentWeek(){
        // Get week starting on Sunday
        Calendar current = getInstance();
        current.setFirstDayOfWeek(Calendar.SUNDAY);
        current.set(Calendar.DAY_OF_WEEK, current.getFirstDayOfWeek());
        current.add(Calendar.DAY_OF_WEEK, 0);
        DateFormat df = new SimpleDateFormat("dd, MMM yyyy", Locale.ENGLISH);
        currentDateString = df.format(current.getTime());
        try{
            currentDate = df.parse(currentDateString);
            Log.e("BEGINING DATE", "// " + currentDateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // Get last day of week
        Calendar last = getInstance();
        last.setFirstDayOfWeek(Calendar.SUNDAY);
        last.set(Calendar.DAY_OF_WEEK, current.getFirstDayOfWeek());
        last.add(Calendar.DAY_OF_WEEK, 7);
        lastDayInCurrentDateString = df.format(last.getTime());
        try{
            lastDayInCurrentDate = df.parse(lastDayInCurrentDateString);
            Log.e("LAST DAY", "// " + lastDayInCurrentDateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

}
