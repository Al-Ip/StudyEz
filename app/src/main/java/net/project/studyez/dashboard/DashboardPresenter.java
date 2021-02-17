package net.project.studyez.dashboard;

import android.view.View;

import androidx.fragment.app.Fragment;

public class DashboardPresenter implements DashboardContract.presenter {

    private final DashboardContract.view mView;

    public DashboardPresenter(DashboardContract.view view){
        mView = view;
    }

    @Override
    public void clickQuickStudy(View view) {
        mView.prepQuickStudy();
    }

    @Override
    public void pressOnQuickStudy(Fragment fragment, int id) {
        mView.changeFragment(fragment, id);
    }

}
