package net.project.studyez.flashcard;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import net.project.studyez.decks.Deck;
import net.project.studyez.home.quickStudy.QuickStudySession;
import net.project.studyez.statistics.time_graph.TimeStudied;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static java.util.Calendar.getInstance;

public class FlashCardInteractor implements FlashCardContract.Interactor {

    private FlashCardContract.onCardGetListener onCardGetListener;
    private FlashCardContract.onStudySessionCompleted onStudySessionCompleted;

    // Firebase variables
    private final FirebaseFirestore fStore;
    private final FirebaseAuth fAuth;
    private final FirebaseUser fUser;
    private DocumentReference docRef;
    private Query query;

    // Date variables
    private Map<String, Long> dayValuesList = new HashMap<>();
    private Date currentDate;
    private String currentDateString;
    private Date lastDayInCurrentDate;
    private String lastDayInCurrentDateString;
    private Date startStudyDateAndTime;

    // Average / Sum variables
    private long averageTime;
    private long totalTime;

    // Object variables
    private QuickStudySession quickStudySession;
    private TimeStudied timeStudied;

    // Static variables
    public static String quickStudySessionId;
    public static String deckName;

    public FlashCardInteractor(){
        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        fUser = fAuth.getCurrentUser();
    }

    public FlashCardInteractor(FlashCardContract.onCardGetListener onCardGetListener, FlashCardContract.onStudySessionCompleted onStudySessionCompleted){
        this.onCardGetListener = onCardGetListener;
        this.onStudySessionCompleted = onStudySessionCompleted;
        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        fUser = fAuth.getCurrentUser();
    }

    @Override
    public void getCardsToDisplayOnFlashcards(String deckID) {
        query = fStore
                .collection("users")
                .document(fUser.getUid())
                .collection("decks")
                .document(fUser.getDisplayName())
                .collection("myDecks")
                .document(deckID)
                .collection("Cards")
                .orderBy("dateTimeCreated", Query.Direction.DESCENDING);
        query.get().addOnCompleteListener(task -> {
            if(task.isComplete()){
                onCardGetListener.onGetSuccess(task.getResult());
            }
        });
    }

    @Override
    public void createNewStudySessionInFirebase(String studyType, String deckName, int numCards, LocalTime startTime, String dateText) {
        DateFormat format = new SimpleDateFormat("EEE LLL dd HH:mm:ss Z yyyy", Locale.ENGLISH);
        try {
            startStudyDateAndTime = format.parse(dateText);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        quickStudySession = new QuickStudySession(studyType, deckName, numCards, startTime.toString(), startStudyDateAndTime);

        FlashCardInteractor.deckName = deckName;

        quickStudySessionId = fStore
                .collection("users")
                .document(fUser.getUid())
                .collection("studySessions")
                .document(deckName)
                .collection(quickStudySession.getSessionMode())
                .document()
                .getId();

        // Checking to see if the document already exists
        docRef = fStore.collection("users")
                .document(fUser.getUid())
                .collection("studySessions")
                .document(deckName);
        docRef.get().addOnCompleteListener(task -> {
            if(task.getResult().exists()){
                docRef = fStore
                        .collection("users")
                        .document(fUser.getUid())
                        .collection("studySessions")
                        .document(deckName)
                        .collection(studyType)
                        .document(quickStudySessionId);
                docRef.set(quickStudySession).addOnCompleteListener(tasks -> {
                    if(!tasks.isComplete()){
                        onStudySessionCompleted.onWriteToFirebaseFail(tasks.getException().getMessage());
                    }
                    else{
                        setupWeeklyTimeStudiedStatisticsToFirebase();
                        onStudySessionCompleted.onWriteToFirebaseSuccess();
                    }
                });
            }
            else {
                // Document does not exist so write it
                // Init list of days as-well as the study session weekly object
                Deck newDeck = new Deck(fUser.getUid(), deckName, dateText, fUser.getDisplayName(), numCards, "");
                docRef = fStore
                        .collection("users")
                        .document(fUser.getUid())
                        .collection("studySessions")
                        .document(deckName);
                docRef.set(newDeck).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isComplete()){
                            docRef = fStore
                                    .collection("users")
                                    .document(fUser.getUid())
                                    .collection("studySessions")
                                    .document(deckName)
                                    .collection(studyType)
                                    .document(quickStudySessionId);
                            docRef.set(quickStudySession).addOnCompleteListener(tasks -> {
                                if(!tasks.isComplete()){
                                    onStudySessionCompleted.onWriteToFirebaseFail(tasks.getException().getMessage());
                                }
                                else{
                                    setupWeeklyTimeStudiedStatisticsToFirebase();
                                    onStudySessionCompleted.onWriteToFirebaseSuccess();
                                }
                            });
                        }
                    }
                });
            }
        });
    }

    @Override
    public void updateEndOfStudySession(int answeredCorrectly, LocalTime endTime) {
        long secondsBetween = ChronoUnit.SECONDS.between(LocalTime.parse(quickStudySession.getSessionStartTime()), LocalTime.parse(endTime.toString()));

        quickStudySession.setSecondsToFinish(secondsBetween);
        quickStudySession.setAnsweredCorrectly(answeredCorrectly);
        quickStudySession.setSessionEndTime(endTime.toString());

        Map<String, Object> map = new HashMap<>();
        map.put("secondsToFinish", secondsBetween);
        map.put("answeredCorrectly", answeredCorrectly);
        map.put("sessionEndTime", endTime.toString());
        docRef = fStore
                .collection("users")
                .document(fUser.getUid())
                .collection("studySessions")
                .document(deckName)
                .collection(quickStudySession.getSessionMode())
                .document(quickStudySessionId);
        docRef.update(map).addOnCompleteListener(task -> {
            if(!task.isComplete()){
                onStudySessionCompleted.onWriteToFirebaseFail(task.getException().getMessage());
            }
            else{
                if(isWithinRange(quickStudySession.getDateCreated())){
                    writeTimeStudiedToCorrespondingDay(quickStudySession.getDateCreated());
                }
                else{
                    onStudySessionCompleted.onWriteToFirebaseFail("Date not in range!");
                }
                onStudySessionCompleted.onWriteToFirebaseSuccess();
            }
        });
    }

    private void setupWeeklyTimeStudiedStatisticsToFirebase(){
        // Initialize dates
        initDatesForCurrentWeek();

        // Checking to see if the document already exists
        docRef = fStore.collection("users")
                .document(fUser.getUid())
                .collection("statistics")
                .document("timeGraph")
                .collection("weekly")
                .document(currentDateString);
        docRef.get().addOnCompleteListener(task -> {
            if(task.getResult().exists()){
                Log.e("TAG", "Document exists!");
            }
            else {
                // Document does not exist so write it
                // Init list of days as-well as the study session weekly object
                timeStudied = new TimeStudied(currentDate);
                docRef = fStore
                        .collection("users")
                        .document(fUser.getUid())
                        .collection("statistics")
                        .document("timeGraph")
                        .collection("weekly")
                        .document(currentDateString);
                docRef.set(timeStudied).addOnCompleteListener(task1 -> {
                    if (task1.isComplete()) {
                        Log.e("TAG", "Successfully Added Time stats to database!");
                    }
                });
            }
        });
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

    public boolean isWithinRange(Date testDate) {
        return !(testDate.before(currentDate) || testDate.after(lastDayInCurrentDate));
    }

    private void writeTimeStudiedToCorrespondingDay(Date dateStudied){
//        Date dateStudied = new Date();
//        try {
//            dateStudied = new SimpleDateFormat("ddMMyyyy").parse("14042021");
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
        Log.e("Testing End Session", "HERE!!!!!!!!!!");
        Log.e("dateStudied", String.valueOf(dateStudied));

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateStudied);
        String dayNumber = String.valueOf(calendar.get(Calendar.DAY_OF_WEEK));
        Log.e("DAY_OF_WEEK", String.valueOf(dayNumber));

        fStore.collection("users")
            .document(fUser.getUid())
            .collection("statistics")
            .document("timeGraph")
            .collection("weekly")
            .whereEqualTo("weekCreated", currentDate)
            .get()
            .addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    if(task.getResult().isEmpty()){
                        Log.d("TAG", "Empty TASK result");
                    }
                    else{
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Log.d("TAG", document.getId() + " => " + document.getData());
                            dayValuesList = (Map<String, Long>) document.get("dailyTimesList");
                        }
                    }
                }
                else {
                    Log.d("TAG", "get failed with ", task.getException());
                }
                if (dayValuesList != null && dayValuesList.containsKey(dayNumber)) {
                    dayValuesList.put(dayNumber, quickStudySession.getSecondsToFinish() + dayValuesList.get(dayNumber));
                    totalTime = dayValuesList.values().stream().reduce((long)0, Long::sum);
                    averageTime = totalTime / dayValuesList.size();
                } else {
                    dayValuesList.put(dayNumber, quickStudySession.getSecondsToFinish());
                    averageTime = quickStudySession.getSecondsToFinish();
                    totalTime = quickStudySession.getSecondsToFinish();
                }
                // Updating the Map fields with new time
                Map<String, Object> map = new HashMap<>();
                map.put("dailyTimesList", dayValuesList);
                map.put("averageTime", averageTime);
                map.put("totalTime", totalTime);
                docRef = fStore
                        .collection("users")
                        .document(fUser.getUid())
                        .collection("statistics")
                        .document("timeGraph")
                        .collection("weekly")
                        .document(currentDateString);
                docRef.update(map).addOnCompleteListener(tasks -> {
                    if(!tasks.isComplete()){
                        Log.e("!task.isComplete()", "Failed!");
                    }
                    else{
                        Log.e("task.isComplete()", "Completed!");
                    }
                });
            });
    }
}
