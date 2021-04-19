package net.project.studyez.decks;


import javax.inject.Inject;


public class Deck {

    private String deckName;
    private String previousDeckName;
    private String creatorID;
    private String creator;
    private String dateTimeCreated;
    private int numCards;

    public Deck() {}


    @Inject
    public Deck(String creatorID, String deckName, String dateTimeCreated, String creator, int numCards, String previousDeckName) {
        this.creatorID = creatorID;
        this.deckName = deckName;
        this.dateTimeCreated = dateTimeCreated;
        this.creator = creator;
        this.numCards = numCards;
        this.previousDeckName = previousDeckName;
    }

    public String getPreviousDeckName() {
        return previousDeckName;
    }

    public void setPreviousDeckName(String previousDeckName) {
        this.previousDeckName = previousDeckName;
    }

    public String getDeckName() {
        return deckName;
    }

    public void setDeckName(String deckName) {
        this.deckName = deckName;
    }

    public String getCreatorID() {
        return creatorID;
    }

    public void setCreatorID(String creatorID) {
        this.creatorID = creatorID;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getDateTimeCreated() {
        return dateTimeCreated;
    }

    public void setDateTimeCreated(String dateTimeCreated) {
        this.dateTimeCreated = dateTimeCreated;
    }

    public int getNumCards() {
        return numCards;
    }

    public void setNumCards(int numCards) {
        this.numCards = numCards;
    }
}
