package net.project.studyez.introduction;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import net.project.studyez.R;
import net.project.studyez.databinding.FragmentIntro4Binding;
import net.project.studyez.login.LoginActivity;
import net.project.studyez.registration.RegisterActivity;

public class IntroductionFragment4 extends Fragment implements IntroContract.View{

    private IntroPresenter introPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_intro4, container, false);

        FragmentIntro4Binding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_intro4, container, false);
        View view = binding.getRoot();
        introPresenter = new IntroPresenter(this);
        binding.setPresenter(introPresenter);
        return view;
    }

    @Override
    public void showRegisterScreen() {
        Intent intent = new Intent(getActivity(), RegisterActivity.class);
        startActivity(intent);
    }

    @Override
    public void showLoginScreen() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
    }
}
