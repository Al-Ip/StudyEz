package net.project.studyez.cards;

import android.app.Activity;
import android.view.View;

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
public class CardPresenterTest {

    @JvmField
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    Activity activity = mock(Activity.class);

    @Mock
    CardContract.view view;

    @Mock
    CardContract.Interactor cardInteractor;

    @Mock
    private CardPresenter presenter;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        presenter = new CardPresenter(view, cardInteractor);
    }

    @Test
    public void on_card_create_failure_message() {
        String message = "Failed to create a new card";
        presenter.onCreateFailure(message);
        verify(view).onCardCreationFailure(message);
    }

    @Test
    public void on_card_create_success_message() {
        String message = "Successfully created a new card";
        presenter.onCreateSuccess(message);
        verify(view).onCardCreationSuccess(message);
    }

    @Test
    public void on_card_delete_failure_message() {
        String message = "Failed to delete the deck";
        presenter.onDeleteFailure(message);
        verify(view).onCardDeletionFailure(message);
    }

    @Test
    public void on_card_delete_success_message() {
        String message = "Successfully deleted the deck";
        presenter.onDeleteSuccess(message);
        verify(view).onCardDeletionSuccess(message);
    }

    @Test
    public void show_empty_card_message() {
        presenter.showEmptyCardsMessage();
        verify(view).displayEmptyCardsMessage();
    }

    @Test
    public void hide_empty_card_message() {
        presenter.hideEmptyCardsMessage();
        verify(view).hideEmptyCardsMessage();
    }

    @Test
    public void short_press_on_card() {
        int id = 0;
        Fragment fragment = mock(Fragment.class);
        presenter.toolbarBackArrowPress(fragment, id);
        verify(view).changeFragment(fragment, id);
    }

    @Test
    public void click_remove_card_image() {
        View viewMock = mock(View.class);
        presenter.clickRemoveImage(viewMock);
        verify(view).displayDeleteCardPopupWindow();
    }

    @Test
    public void click_edit_card_image(){
        View viewMock = mock(View.class);
        String question = "Test Question";
        String answer = "Test Answer";
        presenter.clickEditImage(viewMock, question, answer);
        verify(view).displayEditCardPopupWindow(question, answer);
    }

    @Test
    public void get_cards_from_deck(){
        String deckName = "Test";
        presenter.getCardsFromDeck(activity, deckName);
        verify(cardInteractor).getCardsFromFirebase(activity, deckName);
    }

}