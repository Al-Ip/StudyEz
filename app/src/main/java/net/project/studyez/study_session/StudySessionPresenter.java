package net.project.studyez.study_session;

import android.text.format.DateUtils;

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
        mInteractor.getStudySessionStatisticsFromFirebase();
    }

    @Override
    public void onGetStatsSuccess(QuickStudySession quickStudySession) {
        String timeToFinish = DateUtils.formatElapsedTime(quickStudySession.getSecondsToFinish());
        mView.displayOnGetStatsSuccess(quickStudySession.getDeckName(), quickStudySession.getNumCards(), quickStudySession.getAnsweredCorrectly(), timeToFinish);
    }

    @Override
    public void onGetStatsFailure(String message) {
        mView.displayOnGetStatsFailure(message);
    }
}
