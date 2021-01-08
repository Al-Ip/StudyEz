package net.project.studyez.registration.pageThree;

import android.view.View;

import androidx.fragment.app.Fragment;

import net.project.studyez.registration.pageTwo.RegContinuedPageTwoContract;

public class RegContinuedPageThreePresenter implements RegContinuedPageThreeContract.presenter{

    // to keep reference to view
    private final RegContinuedPageThreeContract.view mView;

    public RegContinuedPageThreePresenter(RegContinuedPageThreeContract.view view){
        mView = view;
    }

    @Override
    public void doChangeFragment(Fragment fragment, int id) {
        mView.changeFragment(fragment, id);
    }

    @Override
    public void clickAnimatedNextButton(View view) {
        mView.onNextAnimatedButtonClick();
    }

    @Override
    public void clickDatePicker(View view) {
        mView.setDatePicker();
    }
}
