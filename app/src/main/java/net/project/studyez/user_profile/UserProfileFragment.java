package net.project.studyez.user_profile;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import net.project.studyez.EXTERNAL.ImagePickerActivity;
import net.project.studyez.R;
import net.project.studyez.databinding.FragmentUserProfileAppbarBinding;
import net.project.studyez.main.MainActivity;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import static android.content.ContentValues.TAG;
import static net.project.studyez.main.MainActivity.slidingRootNav;

public class UserProfileFragment extends Fragment implements UserProfileContract.View {

    private UserProfilePresenter presenter;
    private ImageView imgProfile, plus;
    private TextView username, description, numDecks, numEzPoints, numFollowing;
    private Toolbar toolbar;
    private ProfileImageMenuDialog profileImageMenuDialog;

    public static final int REQUEST_IMAGE = 100;


    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentUserProfileAppbarBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_user_profile_appbar, container, false);
        View view = binding.getRoot();
        presenter = new UserProfilePresenter(this);
        binding.setPresenter(presenter);

        toolbar = view.findViewById(R.id.userProfileToolbar);
        imgProfile = view.findViewById(R.id.img_profile);
        plus = view.findViewById(R.id.img_plus);
        username = view.findViewById(R.id.username);
        description = view.findViewById(R.id.profile_desc);
        numDecks = view.findViewById(R.id.numDecks);
        numEzPoints = view.findViewById(R.id.numEzPoints);
        numFollowing = view.findViewById(R.id.numFollowing);

        // Getting user details and storing it into a User Object
        presenter.getUserDetails();

        initToolbar();

        loadProfileImageDefault();

        // Clearing older images from cache directory
        // don't call this line if you want to choose multiple images in the same activity
        // call this once the bitmap(s) usage is over
        ImagePickerActivity.clearCache(requireActivity());

        imgProfile.setOnClickListener(v -> onProfileImageClick());
        plus.setOnClickListener(v -> onProfileImageClick());
        description.setOnClickListener(v -> {
            UpdateUsernameDialog dialog = new UpdateUsernameDialog(description.getText().toString());
            dialog.show(getChildFragmentManager(), "Update Description Dialog");
        });

        return view;
    }

    private void initToolbar(){
        toolbar.setNavigationIcon(R.drawable.ic_baseline_menu_24);
        toolbar.setTitleTextColor(color(R.color.white));
        toolbar.setNavigationOnClickListener(v -> { slidingRootNav.openMenu(); });
    }

    @ColorInt
    public int color(@ColorRes int res){
        return ContextCompat.getColor(requireContext(), res);
    }

    @Override
    public void displayUserInformation(User user, int countDecks) {
        if (user.getProfileImage() != null) {
            Uri imageUriParse = Uri.parse(user.getProfileImage());
            loadProfileImage(imageUriParse.toString());
        }
        username.setText(user.getUsername());
        description.setText(user.getDescription());
        numDecks.setText(String.valueOf(countDecks));
        numEzPoints.setText(String.valueOf(user.getEzPoints()));
        numFollowing.setText(String.valueOf(user.getFriendsCount()));
    }

    @Override
    public void changeFragment(Fragment fragment, int id) {
        ((MainActivity) getActivity()).changeFragment(fragment, id, true);
    }

    @Override
    public void updateButtonClick(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void updateUsernameDialogConfirm(String description) {
        presenter.clickChangeDescription(description);
        this.description.setText(description);
    }


    @Override
    public void onUpdateOfProfileSuccess(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpdateOfProfileFailed(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }


    private void loadProfileImage(String url) {
        Log.d(TAG, "Image cache path: " + url);
        Glide.with(this).load(url).circleCrop().into(imgProfile);
        imgProfile.setColorFilter(ContextCompat.getColor(requireContext(), android.R.color.transparent));
    }

    private void loadProfileImageDefault() {
        Glide.with(this).load(R.drawable.baseline_account_circle_black_48).circleCrop().into(imgProfile);
        imgProfile.setColorFilter(ContextCompat.getColor(requireContext(), R.color.profile_default_tint));
    }

    void onProfileImageClick() {
        Dexter.withContext(getContext())
                .withPermissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {
                            showImagePickerOptions();
                        }

                        if (report.isAnyPermissionPermanentlyDenied()) {
                            showSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }

    private void showImagePickerOptions() {
        ImagePickerActivity.showImagePickerOptions(getContext(), new ImagePickerActivity.PickerOptionListener() {
            @Override
            public void onTakeCameraSelected() {
                launchCameraIntent();
            }

            @Override
            public void onChooseGallerySelected() {
                launchGalleryIntent();
            }
        });
    }

    private void launchCameraIntent() {
        Intent intent = new Intent(getActivity(), ImagePickerActivity.class);
        intent.putExtra(ImagePickerActivity.INTENT_IMAGE_PICKER_OPTION, ImagePickerActivity.REQUEST_IMAGE_CAPTURE);

        // setting aspect ratio
        intent.putExtra(ImagePickerActivity.INTENT_LOCK_ASPECT_RATIO, true);
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_X, 1); // 16x9, 1x1, 3:4, 3:2
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_Y, 1);

        // setting maximum bitmap width and height
        intent.putExtra(ImagePickerActivity.INTENT_SET_BITMAP_MAX_WIDTH_HEIGHT, true);
        intent.putExtra(ImagePickerActivity.INTENT_BITMAP_MAX_WIDTH, 1000);
        intent.putExtra(ImagePickerActivity.INTENT_BITMAP_MAX_HEIGHT, 1000);

        startActivityForResult(intent, REQUEST_IMAGE);
    }

    private void launchGalleryIntent() {
        Intent intent = new Intent(getActivity(), ImagePickerActivity.class);
        intent.putExtra(ImagePickerActivity.INTENT_IMAGE_PICKER_OPTION, ImagePickerActivity.REQUEST_GALLERY_IMAGE);

        // setting aspect ratio
        intent.putExtra(ImagePickerActivity.INTENT_LOCK_ASPECT_RATIO, true);
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_X, 1); // 16x9, 1x1, 3:4, 3:2
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_Y, 1);
        startActivityForResult(intent, REQUEST_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                Uri uri = data.getParcelableExtra("path");
                // You can update this bitmap to your server
                // loading profile image from local cache
                loadProfileImage(uri.toString());
                String stringUri = uri.toString();
                presenter.clickChangeProfileImage(stringUri);
            }
        }
    }

    /**
     * Showing Alert Dialog with Settings option
     * Navigates user to app settings
     * NOTE: Keep proper title and message depending on your app
     */
    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(getString(R.string.dialog_permission_title));
        builder.setMessage(getString(R.string.dialog_permission_message));
        builder.setPositiveButton(getString(R.string.go_to_settings), (dialog, which) -> {
            dialog.cancel();
            openSettings();
        });
        builder.setNegativeButton(getString(android.R.string.cancel), (dialog, which) -> dialog.cancel());
        builder.show();
    }

    // navigating user to app settings
    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", requireActivity().getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }


    @Override
    public void onStart() {
        super.onStart();
        ((MainActivity)getActivity()).getSupportActionBar().hide();
    }
    @Override
    public void onStop() {
        super.onStop();
        ((MainActivity)getActivity()).getSupportActionBar().show();
    }
}
