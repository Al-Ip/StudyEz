package net.project.studyez.main;

import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.yarolegovich.slidingrootnav.SlidingRootNav;
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder;

import net.project.studyez.R;
import net.project.studyez.decks.DecksFragment;
import net.project.studyez.drawer.DrawerAdapter;
import net.project.studyez.drawer.DrawerItem;
import net.project.studyez.drawer.SimpleItem;
import net.project.studyez.drawer.SpaceItem;
import net.project.studyez.home.HomeFragment;
import net.project.studyez.splash_screen.SplashScreenActivity;
import net.project.studyez.statistics.StatisticsFragment;
import net.project.studyez.user_profile.User;
import net.project.studyez.user_profile.UserProfileFragment;
import net.project.studyez.view.AboutUsFragment;

import java.util.Arrays;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements DrawerAdapter.OnItemSelectedListener, MainContract.View{

    private static final int POS_CLOSE = 0;
    private static final int POS_HOME = 1;
    private static final int POS_MY_PROFILE = 2;
    private static final int POS_DECKS = 3;
    private static final int POS_STATISTICS = 4;
    private static final int POS_ABOUT_US = 5;
    private static final int POS_LOGOUT = 7;

    private String[] screenTitles;
    private Drawable[] screenIcons;

    public static SlidingRootNav slidingRootNav;
    public Toolbar toolbar;

    private NoUsernameDialog dialog;
    private MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter = new MainPresenter(this);
        presenter.getUserDetails();
        initToolbar();

        slidingRootNav = new SlidingRootNavBuilder(this)
                .withDragDistance(180)
                .withRootViewScale(0.75f)
                .withRootViewElevation(25)
                .withToolbarMenuToggle(toolbar)
                .withMenuOpened(false)
                .withContentClickableWhenMenuOpened(false)
                .withSavedState(savedInstanceState)
                .withMenuLayout(R.layout.drawer_menu)
                .inject();

        screenIcons = loadScreenIcons();
        screenTitles = loadScreenTitles();

        DrawerAdapter adapter = new DrawerAdapter(Arrays.asList(
                createItemFor(POS_CLOSE),
                createItemFor(POS_HOME).setChecked(true),
                createItemFor(POS_MY_PROFILE),
                createItemFor(POS_DECKS),
                createItemFor(POS_STATISTICS),
                createItemFor(POS_ABOUT_US),
                new SpaceItem(260),
                createItemFor(POS_LOGOUT)
        ));
        adapter.setListener(this);

        RecyclerView list = findViewById(R.id.drawer_list);
        list.setNestedScrollingEnabled(false);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(adapter);

        adapter.setSelected(POS_HOME);
    }

    public void initToolbar(){
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.toolbar_home);
        toolbar.setTitleTextColor(color(R.color.white));
        setSupportActionBar(toolbar);
    }

    @SuppressWarnings("rawtypes")
    public DrawerItem createItemFor (int position){
        return new SimpleItem(screenIcons[position], screenTitles[position])
                .withIconTint(color(R.color.default_blue))
                .withTextTint(color(R.color.black))
                .withSelectedIconTint(color(R.color.selected_icon_tint))
                .withSelectedTextTint(color(R.color.selected_text_tint));
    }

    @ColorInt
    public int color(@ColorRes int res){
        return ContextCompat.getColor(this, res);
    }

    public  String[] loadScreenTitles(){
        return getResources().getStringArray(R.array.id_activityScreenTitles);

    }
    public Drawable[] loadScreenIcons(){
        TypedArray ta = getResources().obtainTypedArray(R.array.id_activityScreenIcons);
        Drawable[] icons = new Drawable[ta.length()];
        for(int i = 0; i < ta.length(); i++){
            int id = ta.getResourceId(i, 0);
            if(id != 0){
                icons[i] = ContextCompat.getDrawable(this, id);
            }
        }
        ta.recycle();
        return icons;
    }

    @Override
    public void onBackPressed() {
        int count = getSupportFragmentManager().getBackStackEntryCount();

        if (count == 0) {
            //Toast.makeText(this, "Warning. Press back again to close application.", Toast.LENGTH_SHORT).show();
            moveTaskToBack(true);
        }
        else {
            if(!Objects.requireNonNull(getSupportActionBar()).isShowing()) {
                getSupportActionBar().show();
                getSupportFragmentManager().popBackStack();
            }
        }
    }

    @Override
    public void onItemSelected(int position) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if (position == POS_HOME){
            if(getSupportFragmentManager().getBackStackEntryCount() > 0) {
                getSupportFragmentManager().popBackStack();
            }
            else {
                toolbar.setTitle(R.string.toolbar_home);
                HomeFragment homeFragment = new HomeFragment();
                transaction.replace(R.id.main_container, homeFragment);
            }
        }
        else if (position == POS_MY_PROFILE) {
            if(getSupportFragmentManager().getBackStackEntryCount() > 0) {
                getSupportFragmentManager().popBackStack();
            }
            else {
                UserProfileFragment userProfileFragment = new UserProfileFragment();
                transaction.replace(R.id.main_container, userProfileFragment);
            }
        }
        else if(position == POS_DECKS){
            if(getSupportFragmentManager().getBackStackEntryCount() > 0) {
                getSupportFragmentManager().popBackStack();
            }
            else {
                toolbar.setTitle(R.string.toolbar_decks);
                DecksFragment decksFragment = new DecksFragment();
                transaction.replace(R.id.main_container, decksFragment);
            }
        }
        else if (position == POS_STATISTICS){
            if(getSupportFragmentManager().getBackStackEntryCount() > 0) {
                getSupportFragmentManager().popBackStack();
            }
            else {
                toolbar.setTitle(R.string.toolbar_stats);
                StatisticsFragment statisticsFragment = new StatisticsFragment();
                transaction.replace(R.id.main_container, statisticsFragment);
            }
        }
        else if (position == POS_ABOUT_US){
            if(getSupportFragmentManager().getBackStackEntryCount() > 0) {
                getSupportFragmentManager().popBackStack();
            }
            else {
                toolbar.setTitle(R.string.toolbar_about);
                AboutUsFragment aboutUsFragment = new AboutUsFragment();
                transaction.replace(R.id.main_container, aboutUsFragment);
            }
        }
        else if (position == POS_LOGOUT) {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(getApplicationContext(), SplashScreenActivity.class));
            finish();
        }

        slidingRootNav.closeMenu();
        transaction.commit();
    }

    public void changeFragment(Fragment fragment, int id, boolean addToBackStack) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        if (addToBackStack) {
            ft.addToBackStack(null);

        } else {
            getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

        }
        ft.addToBackStack(null);//add the transaction to the back stack so the user can navigate back
        ft.replace(id, fragment, "new").commit();
        getSupportFragmentManager().executePendingTransactions();
    }

    public void getEnteredUsernameText(String username) {
        presenter.setUsername(username);
    }

    @Override
    public void displayUserInformationDialog() {

    }

    @Override
    public void noUsernameFoundDialog(User user) {
        dialog = new NoUsernameDialog(MainActivity.this, user);
        dialog.show();
    }

    @Override
    public void onRegistrationUpdateSuccess(String message) {
        dialog.dismiss();
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRegistrationUpdateFailure(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onGetUserInfoFailure(String message) {
        dialog.setUsernameAlreadyTakenError(message);
    }

}