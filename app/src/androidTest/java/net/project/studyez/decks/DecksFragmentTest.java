package net.project.studyez.decks;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;

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
import static androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.not;

@RunWith(AndroidJUnit4.class)
public class DecksFragmentTest {

    @Rule
    public FragmentTestRule<?, DecksFragment> fragmentTestRule = FragmentTestRule.create(DecksFragment.class);

//    @Test
//    public void userHasNoDecksCreated() {
//        if (getRVcount() < 0) {
//            //onView(withId(R.id.emptyDecksImage)).check(matches(isCompletelyDisplayed()));
//            onView(withId(R.id.emptyDecksImage)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
//        }
//    }

    @Test
    public void userHasDecksCreated() {
        if (getRVcount() > 0) {
            onView(withId(R.id.emptyDecksImage)).check(matches(not(isDisplayed())));
        }
        else
            onView(withId(R.id.emptyDecksImage)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));

    }

    @Test
    public void clickOnADeck(){
        if (getRVcount() > 0){
            onView(withId(R.id.deckRecycler)).perform(actionOnItemAtPosition(0, click()));        }
    }

    @Test
    public void clickCreateNewDeckFab(){
        onView(withId(R.id.DeckFAB)).perform(click());
    }

    // Checking recycle view item count
    private int getRVcount(){
        RecyclerView recyclerView = fragmentTestRule.getActivity().findViewById(R.id.deckRecycler);
        return recyclerView.getAdapter().getItemCount();
    }

}