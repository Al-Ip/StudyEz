package net.project.studyez.statistics.time_graph;


import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.GregorianCalendar.getInstance;

public class TimeStudiedGraphPresenter implements TimeStudiedGraphContract.Presenter, TimeStudiedGraphContract.onDataCollectionListener {

    String startDate;
    String dateKey= "";
    String dateValue= "";

    // to keep reference to view
    private final TimeStudiedGraphContract.View mView;

    // to keep reference to interactor
    private final TimeStudiedGraphContract.Interactor mInteractor;

    public TimeStudiedGraphPresenter(TimeStudiedGraphContract.View view){
        mView = view;
        mInteractor = new TimeStudiedGraphInteractor(this);
    }

    @Override
    public void getLineChartData() {
        mInteractor.getWeeklyDataFromFirebase(startDate);
    }

    @Override
    public void getCurrentDate(int week) {
        Calendar c = getInstance();
        c.setFirstDayOfWeek(Calendar.SUNDAY);
        c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek());
        c.add(Calendar.DAY_OF_WEEK, week);
        DateFormat df = new SimpleDateFormat("dd, MMM yyyy", Locale.getDefault());
        startDate = df.format(c.getTime());
        c.add(Calendar.DAY_OF_MONTH, 6);
        mView.setWeekDateTextField(startDate);
    }

    @Override
    public void onDataSuccess(TimeStudied timeStudied) {
        Map<Integer, Integer> dateMap = IntStream.range(1,8).boxed().collect(Collectors.toMap(Function.identity(), i -> 0));

        for (Map.Entry<String, Long> entry : timeStudied.getDailyTimesList().entrySet()) {
            dateKey = entry.getKey();
            dateValue = entry.getValue().toString();
            Log.e("KEY_"+dateKey, "// VALUE_"+dateValue);

            for (Map.Entry<Integer, Integer> entrys : dateMap.entrySet()) {
                int key = entrys.getKey();
                String value = entrys.getValue().toString();
                Log.e("KEY2_"+key, "// VALUE2_"+value);
                dateMap.put(Integer.parseInt(dateKey), Integer.parseInt(dateValue));
            }
        }
        mView.setLineChartWithData(dateMap, timeStudied.getAverageTime(), timeStudied.getTotalTime());
    }

    @Override
    public void onDataFailure(String message) {
        Map<Integer, Integer> dateMap = IntStream.range(1,8).boxed().collect(Collectors.toMap(Function.identity(), i -> 0));

        for (Map.Entry<Integer, Integer> entry : dateMap.entrySet()) {
            int key = entry.getKey();
            String value = entry.getValue().toString();
            Log.e("KEY2_"+key, "// VALUE2_"+value);
            dateMap.put(Integer.parseInt(String.valueOf(key)), Integer.parseInt(value));
        }
        mView.setLineChartWithData(dateMap, 0, 0);
    }
}
