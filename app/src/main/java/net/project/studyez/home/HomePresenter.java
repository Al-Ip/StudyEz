package net.project.studyez.home;

import android.view.View;

import androidx.fragment.app.Fragment;

public class HomePresenter implements HomeContract.presenter {

    private final HomeContract.view mView;

    public HomePresenter(HomeContract.view view){
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
