package net.project.studyez.registration.pageFive;

import android.view.View;

import androidx.fragment.app.Fragment;

public class RegContinuedPageFivePresenter implements RegContinuedPageFiveContract.presenter{

    // to keep reference to view
    private final RegContinuedPageFiveContract.view mView;

    public RegContinuedPageFivePresenter(RegContinuedPageFiveContract.view view){
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
}
