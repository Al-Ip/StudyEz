package net.project.studyez.registration.finished;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import net.project.studyez.R;
import net.project.studyez.databinding.FragmentRegistrationFinishedBinding;
import net.project.studyez.main.MainActivity;

public class RegistrationFinishedFragment extends Fragment implements RegistrationFinishedContract.view{

    private RegistrationFinishedPresenter presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_registration_continued_page_one, container, false);

        FragmentRegistrationFinishedBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_registration_finished, container, false);
        View view = binding.getRoot();
        presenter = new RegistrationFinishedPresenter(this);
        binding.setPresenter(presenter);
        return view;
    }

    @Override
    public void onNextAnimatedButtonClick() {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
        getActivity().onBackPressed();
    }

}
