package net.project.studyez.registration.pageFive;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import net.project.studyez.MainActivity;
import net.project.studyez.R;
import net.project.studyez.databinding.FragmentRegistrationContinuedPageFiveBinding;
import net.project.studyez.registration.RegisterActivity;

public class RegContinuedPageFiveFragment extends Fragment implements RegContinuedPageFiveContract.view {

    private RegContinuedPageFivePresenter presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_registration_continued_page_two, container, false);

        FragmentRegistrationContinuedPageFiveBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_registration_continued_page_five, container, false);
        View view = binding.getRoot();
        presenter = new RegContinuedPageFivePresenter(this);
        binding.setPresenter(presenter);

        //Change this is just for debugging
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        }, 5000);

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
