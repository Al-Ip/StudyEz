package net.project.studyez.statistics.deck_graph;

import java.util.Map;

public interface DecksGraphContract {

    interface View {
        void initBarChart();
        void setWeekDateTextField(String date);
        void setBarChartWithData(Map<String, Long> dateMap, long average, long total);
    }

    interface Presenter {
        void getBarChartData();
        void getCurrentWeekDate(int week);
    }

    interface Interactor {
        void getWeeklyDataFromFirebase(String startDate);
    }

    interface onDataCollectionListener{
        void onDataSuccess(DecksStudied decksStudied);
        void onDataFailure(String message);
    }
}
