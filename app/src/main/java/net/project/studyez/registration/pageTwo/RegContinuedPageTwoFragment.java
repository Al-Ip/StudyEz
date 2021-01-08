package net.project.studyez.registration.pageTwo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import net.project.studyez.R;
import net.project.studyez.databinding.FragmentRegistrationContinuedPageTwoBinding;
import net.project.studyez.registration.main.RegisterActivity;
import net.project.studyez.registration.pageThree.RegContinuedPageThreeFragment;

public class RegContinuedPageTwoFragment extends Fragment implements RegContinuedPageTwoContract.view{

    private RegContinuedPageTwoPresenter presenter;
    private ImageView male, female, other, nextButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_registration_continued_page_two, container, false);

        FragmentRegistrationContinuedPageTwoBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_registration_continued_page_two, container, false);
        View view = binding.getRoot();
        presenter = new RegContinuedPageTwoPresenter(this);
        binding.setPresenter(presenter);

        male = view.findViewById(R.id.imageMale);
        female = view.findViewById(R.id.imageFemale);
        other = view.findViewById(R.id.imageOther);
        nextButton = view.findViewById(R.id.registrationPage2NextButton);

        return view;
    }

    @Override
    public void onNextAnimatedButtonClick() {
        presenter.doChangeFragment(new RegContinuedPageThreeFragment(), R.id.registerLayout);
    }

    @Override
    public void changeFragment(Fragment fragment, int id) {
        // Casting getActivity so that i can change the fragment from the activity class itself
        ((RegisterActivity) getActivity()).changeFragment(fragment, id);
    }

    @Override
    public void focusMaleImage(float opaque, float transparent) {
        male.setAlpha(opaque);
        female.setAlpha(transparent);
        other.setAlpha(transparent);
    }

    @Override
    public void focusFemaleImage(float opaque, float transparent) {
        female.setAlpha(opaque);
        male.setAlpha(transparent);
        other.setAlpha(transparent);
    }

    @Override
    public void focusOtherImage(float opaque, float transparent) {
        other.setAlpha(opaque);
        male.setAlpha(transparent);
        female.setAlpha(transparent);
    }

    @Override
    public void showAnimatedNextButton() {
        nextButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideAnimatedNextButton() {
        nextButton.setVisibility(View.INVISIBLE);
    }


}
