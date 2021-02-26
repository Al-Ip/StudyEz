package net.project.studyez.dependencyInjector;

import net.project.studyez.decks.DeckInteractor;

public interface DependencyInjector {

    interface DeckDependencyInjector{
        DeckInteractor deckInteractor();
    }
}
