package net.project.studyez.dashboard.quickStudy;

import android.app.Activity;

import androidx.fragment.app.Fragment;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class QuickStudyPresenter implements QuickStudyContract.presenter{

    private final QuickStudyContract.view mView;
    private final QuickStudyContract.Interactor mInteractor;

    public QuickStudyPresenter(QuickStudyContract.view view){
        mView = view;
        mInteractor = new QuickStudyInteractor();
    }

    @Override
    public FirestoreRecyclerOptions getDecks(Activity activity) {
        return mInteractor.getDecksFromFirebase(activity);
    }

    @Override
    public void shortPressOnDeck(Fragment fragment, int id) {
        mView.changeFragment(fragment, id);
    }

    @Override
    public void showNoDeckMessage() {
        mView.displayNoDeckMessage();
    }

    @Override
    public void hideNoDeckMessage() {
        mView.hideNoDeckMessage();
    }
}
