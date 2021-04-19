package net.project.studyez.statistics.deck_graph;

import net.project.studyez.home.quickStudy.QuickStudySession;

import java.util.List;
import java.util.Map;

public interface DecksGraphContract {

    interface View {
        void initBarChart();
        void setWeekDateTextField(String date);
        void setBarChartWithData(Map<String, Integer> deckData);
    }

    interface Presenter {
        void getBarChartData();
        void getCurrentWeekDate(int week);
    }

    interface Interactor {
        void getWeeklyDataFromFirebase(String startDate);
    }

    interface onDataCollectionListener{
        void onDataSuccess(List<QuickStudySession> dataList);
        void onDataFailure(String message);
    }
}
