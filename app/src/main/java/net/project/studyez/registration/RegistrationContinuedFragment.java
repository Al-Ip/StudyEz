package net.project.studyez.registration;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import net.project.studyez.R;
import net.project.studyez.databinding.FragmentRegistrationContinuedBinding;
import net.project.studyez.registration.finished.RegistrationFinishedFragment;

import de.hdodenhof.circleimageview.CircleImageView;


public class RegistrationContinuedFragment extends Fragment implements RegistrationContract.View {

    private RegistrationPresenter presenter;
    private TextInputEditText username;
    private ImageView nextButton, blackFadeImage;
    private ProgressBar progressBar;
    private CircleImageView profileImage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentRegistrationContinuedBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_registration_continued, container, false);
        View view = binding.getRoot();
        presenter = new RegistrationPresenter(this);
        binding.setPresenter(presenter);

        username = view.findViewById(R.id.editTextUsername);
        nextButton = view.findViewById(R.id.mainPageNextButton);
        blackFadeImage = view.findViewById(R.id.blackFadeImage2);
        progressBar = view.findViewById(R.id.registerUsernameProgressBar);

        username.addTextChangedListener(textWatcher);

        return view;
    }

    // Makes button appear or disappear depending on whether or not user has entered values in both fields
    private final TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            presenter.toggleAnimatedTextButtonVisibilityForRegPageTwo(getUsernameText());
        }
        @Override
        public void afterTextChanged(Editable s) {
            showUsernameError();
        }
    };


    @Override
    public void onNextAnimatedButtonClick() {
        presenter.addUsernameToDatabase(getActivity(), getUsernameText());
    }


    @Override
    public String getUsernameText() {
        return this.username.getText().toString();
    }

    @Override
    public void showUsernameError() {
        if(TextUtils.isEmpty(username.getText().toString()))
            username.setError("You need to enter a Valid Username");
    }

    @Override
    public void showBlackFadeIn() {
        blackFadeImage.setVisibility(android.view.View.VISIBLE);
    }

    @Override
    public void hideBlackFadeIn() {
        blackFadeImage.setVisibility(android.view.View.INVISIBLE);
    }

    @Override
    public void showProgressBar() {
        progressBar.setVisibility(android.view.View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(android.view.View.INVISIBLE);
    }

    @Override
    public void onRegistrationUpdateSuccess(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        presenter.doChangeFragment(new RegistrationFinishedFragment(), R.id.registerLayout);
    }

    @Override
    public void onRegistrationUpdateFailure(String message) {
        username.setError(message);
    }

    @Override
    public void showAnimatedNextButton() {
        nextButton.setVisibility(android.view.View.VISIBLE);
    }

    @Override
    public void hideAnimatedNextButton() {
        nextButton.setVisibility(android.view.View.INVISIBLE);
    }

    @Override
    public void changeFragment(Fragment fragment, int id) {
        ((RegisterActivity) getActivity()).changeFragment(fragment, id);
    }

    @Override
    public void onRegistrationSuccess(FirebaseUser firebaseUser) {
    }

    @Override
    public void displayLoginActivity() {

    }

    @Override
    public String getEmailText() {
        return null;
    }

    @Override
    public String getPasswordText() {
        return null;
    }

    @Override
    public void showEmailError() {

    }

    @Override
    public void showPasswordError() {

    }

    @Override
    public void onRegistrationFailure(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }


}

