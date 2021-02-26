package net.project.studyez.decks;

import androidx.fragment.app.Fragment;

import net.project.studyez.dependencyInjector.DependencyInjector;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class DeckPresenterTest {

    @Mock
    private DeckContract.view view;
    private DeckPresenter presenter;
    private final DependencyInjector.DeckDependencyInjector dependencyInjector = new StubDependencyInjector();

    // Dependency
    public static class StubDependencyInjector implements DependencyInjector.DeckDependencyInjector {
        @Override
        public DeckInteractor deckInteractor() {
            return new StubDeckImplementation();
        }
    }
    public static class StubDeckImplementation extends DeckInteractor{
        public StubDeckImplementation(){

        }
    }

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        presenter = new DeckPresenter(view, dependencyInjector);
    }

    @Test
    public void OnDeckCreateFailureMessage() {
        String message = "Failed to create a new deck";
        presenter.onDeckCreateFailure(message);
        verify(view).onDeckCreationFailure(message);
    }

    @Test
    public void OnDeckCreateSuccessMessage() {
        String message = "Successfully created a new deck";
        presenter.onDeckCreateSuccess(message);
        verify(view).onDeckCreationSuccess(message);
    }

    @Test
    public void OnDeckDeleteFailureMessage() {
        String message = "Failed to delete the deck";
        presenter.onDeleteFailure(message);
        verify(view).onDeckDeletionFailure(message);
    }

    @Test
    public void OnDeckDeleteSuccessMessage() {
        String message = "Successfully deleted the deck";
        presenter.onDeleteSuccess(message);
        verify(view).onDeckDeletionSuccess(message);
    }

    @Test
    public void ShowEmptyDeckMessage() {
        presenter.showEmptyDeckMessage();
        verify(view).displayEmptyDeckMessage();
    }

    @Test
    public void HideEmptyDeckMessage() {
        presenter.hideEmptyDeckMessage();
        verify(view).hideEmptyDeckMessage();
    }

    @Test
    public void shortPressOnDeck() {
        //FragmentTestRule<?, DecksFragment> fragmentTestRule = FragmentTestRule.create(DecksFragment.class);
        int id = 0;
        Fragment fragment = new Fragment();
        presenter.shortPressOnDeck(fragment, id);
        verify(view).changeFragment(fragment, id);
    }




}