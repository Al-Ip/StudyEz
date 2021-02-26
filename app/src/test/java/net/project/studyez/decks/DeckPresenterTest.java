package net.project.studyez.decks;

import android.app.Activity;

import androidx.fragment.app.Fragment;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.MockitoRule;

import kotlin.jvm.JvmField;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;


@RunWith(MockitoJUnitRunner.class)
public class DeckPresenterTest {

    @JvmField
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    Activity activity = mock(Activity.class);

    @Mock
    DeckContract.view view;

    @Mock
    DeckContract.Interactor deckInteractor;

    @Mock
    private DeckPresenter presenter;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        presenter = new DeckPresenter(view, deckInteractor);
    }

    @Test
    public void on_deck_create_failure_message() {
        String message = "Failed to create a new deck";
        presenter.onDeckCreateFailure(message);
        verify(view).onDeckCreationFailure(message);
    }

    @Test
    public void on_deck_create_success_message() {
        String message = "Successfully created a new deck";
        presenter.onDeckCreateSuccess(message);
        verify(view).onDeckCreationSuccess(message);
    }

    @Test
    public void on_deck_delete_failure_message() {
        String message = "Failed to delete the deck";
        presenter.onDeleteFailure(message);
        verify(view).onDeckDeletionFailure(message);
    }

    @Test
    public void on_deck_delete_success_message() {
        String message = "Successfully deleted the deck";
        presenter.onDeleteSuccess(message);
        verify(view).onDeckDeletionSuccess(message);
    }

    @Test
    public void show_empty_deck_message() {
        presenter.showEmptyDeckMessage();
        verify(view).displayEmptyDeckMessage();
    }

    @Test
    public void hide_empty_deck_message() {
        presenter.hideEmptyDeckMessage();
        verify(view).hideEmptyDeckMessage();
    }

    @Test
    public void short_press_on_deck() {
        int id = 0;
        Fragment fragment = mock(Fragment.class);
        presenter.shortPressOnDeck(fragment, id);
        verify(view).changeFragment(fragment, id);
    }

    @Test
    public void long_press_on_deck() {
        presenter.longPressOnDeck();
        verify(view).displayDeleteDeckPopupWindow();
    }

    @Test
    public void delete_a_deck_from_firebase() {
        String deck_position_in_recyclerview = "test";
        presenter.deleteDeckFromFirebase(deck_position_in_recyclerview);
        verify(deckInteractor).deleteDeck(deck_position_in_recyclerview);
    }

    @Test
    public void get_all_decks_from_firebase() {
        presenter.getDecks(activity);
        verify(deckInteractor).getDecksFromFirebase(activity);
    }

}