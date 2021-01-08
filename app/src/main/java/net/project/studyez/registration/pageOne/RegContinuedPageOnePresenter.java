package net.project.studyez.registration.pageOne;

import android.view.View;

import androidx.fragment.app.Fragment;

public class RegContinuedPageOnePresenter implements RegContinuedPageOneContract.presenter{

    // to keep reference to view
    private final RegContinuedPageOneContract.view mView;

    public RegContinuedPageOnePresenter(RegContinuedPageOneContract.view view){
        mView = view;
    }

    @Override
    public void doChangeFragment(Fragment fragment, int id) { mView.changeFragment(fragment, id);
    }

    @Override
    public void clickAnimatedNextButton(View view) {
        mView.onNextAnimatedButtonClick();
    }
}
