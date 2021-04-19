package net.project.studyez.statistics.deck_graph;

import android.util.Log;

import net.project.studyez.home.quickStudy.QuickStudySession;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static java.util.Calendar.getInstance;

public class DecksGraphPresenter implements DecksGraphContract.Presenter, DecksGraphContract.onDataCollectionListener {

    private String startDate;
    private Map<String, Integer> deckMapData;

    // to keep reference to view
    private final DecksGraphContract.View mView;

    // to keep reference to interactor
    private final DecksGraphContract.Interactor mInteractor;

    public DecksGraphPresenter(DecksGraphContract.View view){
        mView = view;
        mInteractor = new DecksGraphInteractor(this);
    }


    @Override
    public void getBarChartData() {
        mInteractor.getWeeklyDataFromFirebase(startDate);
    }

    @Override
    public void getCurrentWeekDate(int week) {
        Calendar c = getInstance();
        c.setFirstDayOfWeek(Calendar.SUNDAY);
        c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek());
        c.add(Calendar.DAY_OF_WEEK, week);
        DateFormat df = new SimpleDateFormat("dd, MMM yyyy", Locale.ENGLISH);
        startDate = df.format(c.getTime());
        c.add(Calendar.DAY_OF_MONTH, 6);
        mView.setWeekDateTextField(startDate);
    }

    @Override
    public void onDataSuccess(List<QuickStudySession> dataList) {
        deckMapData = new HashMap<>();
        for(QuickStudySession temp: dataList){
//            Log.e("LIST DeckName", "- " + temp.getDeckName());
//            Log.e("LIST Seconds", "- " + temp.getSecondsToFinish());
            if(temp.getSecondsToFinish() != 0) {
                if (deckMapData.containsKey(temp.getDeckName())) {
                    deckMapData.put(temp.getDeckName(), (int) (deckMapData.get(temp.getDeckName()) + temp.getSecondsToFinish()));
                } else
                    deckMapData.put(temp.getDeckName(), (int) temp.getSecondsToFinish());
            }
        }

        for (Map.Entry<String, Integer> entry : deckMapData.entrySet()) {
            String key = entry.getKey();
            int value = entry.getValue();
            Log.e("KEY_"+key, "// VALUE_"+value);
            //dateMap.put(Integer.parseInt(String.valueOf(key)), Integer.parseInt(value));
        }


        mView.setBarChartWithData(deckMapData);
    }

    @Override
    public void onDataFailure(String message) {

    }
}
