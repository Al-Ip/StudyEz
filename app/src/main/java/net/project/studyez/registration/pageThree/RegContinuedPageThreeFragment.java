package net.project.studyez.registration.pageThree;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.tsongkha.spinnerdatepicker.DatePicker;
import com.tsongkha.spinnerdatepicker.SpinnerDatePickerDialogBuilder;

import net.project.studyez.R;
import net.project.studyez.databinding.FragmentRegistrationContinuedPageThreeBinding;
import net.project.studyez.registration.RegisterActivity;
import net.project.studyez.registration.pageFour.RegContinuedPageFourFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;


public class RegContinuedPageThreeFragment extends Fragment implements com.tsongkha.spinnerdatepicker.DatePickerDialog.OnDateSetListener, DialogInterface.OnCancelListener, RegContinuedPageThreeContract.view {

    private RegContinuedPageThreePresenter presenter;
    private ImageView nextButton;
    private TextView dateTextView;
    private SimpleDateFormat simpleDateFormat;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_registration_continued_page_two, container, false);

        FragmentRegistrationContinuedPageThreeBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_registration_continued_page_three, container, false);
        View view = binding.getRoot();
        presenter = new RegContinuedPageThreePresenter(this);
        binding.setPresenter(presenter);

        nextButton = view.findViewById(R.id.registrationPage4NextButton);
        dateTextView = view.findViewById(R.id.dateOfBirthEditText);
        simpleDateFormat = new SimpleDateFormat("dd / MM  / yyyy", Locale.ENGLISH);

        return view;
    }

    @Override
    public void onNextAnimatedButtonClick() {
        presenter.doChangeFragment(new RegContinuedPageFourFragment(), R.id.registerLayout);
    }

    @Override
    public void changeFragment(Fragment fragment, int id) {
        // Casting getActivity so that i can change the fragment from the activity class itself
        ((RegisterActivity) getActivity()).changeFragment(fragment, id);
    }

    @Override
    public void showAnimatedNextButton() {
        nextButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideAnimatedNextButton() {
        nextButton.setVisibility(View.INVISIBLE);
    }

    @Override
    public void setDatePicker() {
        showDate(1990, 0, 1, R.style.DatePickerSpinner);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        Calendar calendar = new GregorianCalendar(year, monthOfYear, dayOfMonth);
        dateTextView.setText(simpleDateFormat.format(calendar.getTime()));
        showAnimatedNextButton();
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        dateTextView.setText(R.string.cancelled);
    }

    void showDate(int year, int monthOfYear, int dayOfMonth, int spinnerTheme) {
        new SpinnerDatePickerDialogBuilder()
                .context(this.getContext())
                .callback(this)
                .spinnerTheme(spinnerTheme)
                .defaultDate(year, monthOfYear, dayOfMonth)
                .build()
                .show();
    }
}