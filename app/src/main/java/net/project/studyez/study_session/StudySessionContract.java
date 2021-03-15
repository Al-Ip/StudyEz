package net.project.studyez.study_session;

import net.project.studyez.home.quickStudy.QuickStudySession;

public interface StudySessionContract {

    interface view{
        void displayOnGetStatsSuccess(String deckName, int numCards, int ansCorrect, String timeToFinish);
        void displayOnGetStatsFailure(String message);
    }

    interface presenter{
        void getStudySessionStatistics();
    }

    interface interactor{
        void getStudySessionStatisticsFromDatabase();
    }

    interface onGetStats{
        void onGetStatsSuccess(QuickStudySession quickStudySession);
        void onGetStatsFailure(String message);
    }
}
