package net.project.studyez.registration.pageOne;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import net.project.studyez.R;
import net.project.studyez.databinding.FragmentRegistrationContinuedPageOneBinding;
import net.project.studyez.registration.main.RegisterActivity;
import net.project.studyez.registration.pageTwo.RegContinuedPageTwoFragment;

public class RegContinuedPageOneFragment extends Fragment implements RegContinuedPageOneContract.view{

    private RegContinuedPageOnePresenter presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_registration_continued_page_one, container, false);

        FragmentRegistrationContinuedPageOneBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_registration_continued_page_one, container, false);
        View view = binding.getRoot();
        presenter = new RegContinuedPageOnePresenter(this);
        binding.setPresenter(presenter);
        return view;
    }

    @Override
    public void onNextAnimatedButtonClick() {
        presenter.doChangeFragment(new RegContinuedPageTwoFragment(), R.id.registerLayout);
    }

    @Override
    public void changeFragment(Fragment fragment, int id) {
         // Casting getActivity so that i can change the fragment from the activity class itself
        ((RegisterActivity) getActivity()).changeFragment(fragment, id);
    }
}
