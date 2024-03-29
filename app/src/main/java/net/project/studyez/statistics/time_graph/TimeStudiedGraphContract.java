package net.project.studyez.statistics.time_graph;

import java.util.Map;

public interface TimeStudiedGraphContract {

    interface View {
        void initLineChart();
        void setWeekDateTextField(String date);
        void setLineChartWithData(Map<Integer, Integer> deckMap, long average, long total);
    }

    interface Presenter {
        void getLineChartData();
        void getCurrentWeekDate(int week);
    }

    interface Interactor {
        void getWeeklyDataFromFirebase(String startDate);
    }

    interface onDataCollectionListener{
        void onDataSuccess(TimeStudied timeStudied);
        void onDataFailure(String message);
    }
}
