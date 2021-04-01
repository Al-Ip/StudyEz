package net.project.studyez.statistics;

public class StatisticsPresenter implements StatisticsContract.Presenter{

    // to keep reference to view
    private final StatisticsContract.View mView;

    // to keep reference to interactor
    private final StatisticsContract.Interactor mInteractor;

    public StatisticsPresenter(StatisticsContract.View view){
        mView = view;
        mInteractor = new StatisticsInteractor();
    }
}
