package net.project.studyez.study_session;

import android.text.format.DateUtils;
import android.util.Log;

import net.project.studyez.home.quickStudy.QuickStudyContract;
import net.project.studyez.home.quickStudy.QuickStudyInteractor;
import net.project.studyez.home.quickStudy.QuickStudySession;

public class StudySessionPresenter implements StudySessionContract.presenter, StudySessionContract.onGetStats {

    private final StudySessionContract.view mView;
    private final StudySessionContract.interactor mInteractor;

    public StudySessionPresenter(StudySessionContract.view view){
        mView = view;
        mInteractor = new StudySessionInteractor(this);
    }

    @Override
    public void getStudySessionStatistics() {
        mInteractor.getStudySessionStatisticsFromDatabase();
    }

    @Override
    public void onGetStatsSuccess(QuickStudySession quickStudySession) {
        Log.e("Presenter", String.valueOf(quickStudySession.getSecondsToFinish()));
        String timeToFinish = DateUtils.formatElapsedTime(quickStudySession.getSecondsToFinish());
        mView.displayOnGetStatsSuccess(quickStudySession.getDeckName(), quickStudySession.getNumCards(), quickStudySession.getAnsweredCorrectly(), timeToFinish);
    }

    @Override
    public void onGetStatsFailure(String message) {
        mView.displayOnGetStatsFailure(message);
    }
}
