package net.project.studyez.statistics.deck_graph;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import static java.util.Calendar.getInstance;

public class DecksGraphPresenter implements DecksGraphContract.Presenter, DecksGraphContract.onDataCollectionListener {

    String startDate;
    String deckMapKey= "";
    String deckMapValue= "";

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
    public void onDataSuccess(DecksStudied decksStudied) {
        mView.setBarChartWithData(decksStudied.getDeckTimesList(), decksStudied.getAverageTime(), decksStudied.getTotalTime());
    }

    @Override
    public void onDataFailure(String message) {

    }
}
