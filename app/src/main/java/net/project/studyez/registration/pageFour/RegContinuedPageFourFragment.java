package net.project.studyez.registration.pageFour;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import net.project.studyez.R;
import net.project.studyez.databinding.FragmentRegistrationContinuedPageFourBinding;
import net.project.studyez.registration.main.RegisterActivity;
import net.project.studyez.registration.pageFive.RegContinuedPageFiveFragment;

public class RegContinuedPageFourFragment extends Fragment implements RegContinuedPageFourContract.view {

    private RegContinuedPageFourPresenter presenter;
    private ImageView nextButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_registration_continued_page_two, container, false);

        FragmentRegistrationContinuedPageFourBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_registration_continued_page_four, container, false);
        View view = binding.getRoot();
        presenter = new RegContinuedPageFourPresenter(this);
        binding.setPresenter(presenter);

        nextButton = view.findViewById(R.id.registrationPage4NextButton);

        //Change this is just for debugging
        nextButton.setVisibility(View.VISIBLE);

        return view;
    }

    @Override
    public void onNextAnimatedButtonClick() {
        presenter.doChangeFragment(new RegContinuedPageFiveFragment(), R.id.registerLayout);
    }

    @Override
    public void changeFragment(Fragment fragment, int id) {
        // Casting getActivity so that i can change the fragment from the activity class itself
        ((RegisterActivity) getActivity()).changeFragment(fragment, id);
    }
}
