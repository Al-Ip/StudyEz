package net.project.studyez.registration.pageTwo;

import android.view.View;

import androidx.fragment.app.Fragment;

public class RegContinuedPageTwoPresenter implements RegContinuedPageTwoContract.presenter{

    // to keep reference to view
    private final RegContinuedPageTwoContract.view mView;

    public RegContinuedPageTwoPresenter(RegContinuedPageTwoContract.view view){
        mView = view;
    }

    @Override
    public void doChangeFragment(Fragment fragment, int id) { mView.changeFragment(fragment, id);
    }

    @Override
    public void clickAnimatedNextButton(View view) {
        mView.onNextAnimatedButtonClick();
    }

    @Override
    public void clickMaleImage(View view) {
        mView.focusMaleImage(1f, 0.2f);
        mView.showAnimatedNextButton();
    }

    @Override
    public void clickFemaleImage(View view) {
        mView.focusFemaleImage(1f, 0.2f);
        mView.showAnimatedNextButton();
    }

    @Override
    public void clickOtherImage(View view) {
        mView.focusOtherImage(1f, 0.2f);
        mView.showAnimatedNextButton();
    }

}
