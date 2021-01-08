package net.project.studyez.registration.pageFour;

import android.view.View;

import androidx.fragment.app.Fragment;

public class RegContinuedPageFourPresenter implements RegContinuedPageFourContract.presenter{

    // to keep reference to view
    private final RegContinuedPageFourContract.view mView;

    public RegContinuedPageFourPresenter(RegContinuedPageFourContract.view view){
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
