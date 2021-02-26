package net.project.studyez.decks;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.android21buttons.fragmenttestrule.FragmentTestRule;

import net.project.studyez.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class DecksFragmentTest {

    @Rule
    public FragmentTestRule<?, DecksFragment> fragmentTestRule = FragmentTestRule.create(DecksFragment.class);

    @Test
    public void use_app_context() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("net.project.studyez", appContext.getPackageName());
    }

    @Test
    public void check_if_deck_fragment_can_be_instantiated(){
        fragmentTestRule.getFragment().getActivity().runOnUiThread(() -> {
            DecksFragment decksFragment = new DecksFragment();
        });
        onView(withId(R.id.deckLayout)).check(matches(isDisplayed()));
    }

    @Test
    public void deck_is_present_on_screen() {
        if (getRVcount() > 0) {
            onView(withId(R.id.emptyDecksImage)).check(matches(not(isDisplayed())));
        }
    }

    @Test
    public void click_on_a_deck(){
        if (getRVcount() > 0){
            onView(withId(R.id.deckRecycler)).perform(actionOnItemAtPosition(0, click()));
        }
    }

    @Test
    public void click_create_new_deck_fab(){
        onView(withId(R.id.DeckFAB)).perform(click());
    }

    // Checking recycle view item count
    private int getRVcount(){
        RecyclerView recyclerView = fragmentTestRule.getFragment().getView().findViewById(R.id.deckRecycler);
        return recyclerView.getAdapter().getItemCount();
    }

}