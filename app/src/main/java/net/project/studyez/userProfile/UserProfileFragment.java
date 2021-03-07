package net.project.studyez.userProfile;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import net.project.studyez.MainActivity;
import net.project.studyez.R;
import net.project.studyez.databinding.FragmentUserProfileBinding;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserProfileFragment extends Fragment implements UserProfileContract.View {

    private UserProfilePresenter presenter;
    private CircleImageView profileImage;
    private TextView username, email;
    private Button updateButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentUserProfileBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_user_profile, container, false);
        View view = binding.getRoot();
        presenter = new UserProfilePresenter(this);
        binding.setPresenter(presenter);

        profileImage = view.findViewById(R.id.userProfile_Image);
        username = view.findViewById(R.id.userProfile_Username);
        email = view.findViewById(R.id.userProfile_Email);
        updateButton = view.findViewById(R.id.userProfile_UpdateButton);

        // Getting user details and storing it into a User Object
        presenter.getUserDetails();

        return view;
    }

    @Override
    public void displayUserInformation(User user) {
        Uri imageUriParse = Uri.parse(user.getProfileImage());
        profileImage.setImageURI(imageUriParse);
        username.setText(user.getUsername());
        email.setText(user.getEmail());
    }

    @Override
    public void changeFragment(Fragment fragment, int id) {
        ((MainActivity) getActivity()).changeFragment(fragment, id);
    }

    @Override
    public void updateButtonClick(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}
