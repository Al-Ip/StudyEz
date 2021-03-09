package net.project.studyez.decks;


import javax.inject.Inject;


public class Deck {

    private String name;
    private String creatorID;
    private String creator;
    private String image;
    private String dateTimeCreated;
    private int numCards;

    public Deck() {}

    @Inject
    public Deck(String creatorID, String name, String dateTimeCreated, String creator, int numCards, String image) {
        this.creatorID = creatorID;
        this.name = name;
        this.dateTimeCreated = dateTimeCreated;
        this.creator = creator;
        this.numCards = numCards;
        this.image = image;
    }

    public Deck(String creatorID, String name, String dateTimeCreated, String creator, int numCards) {
        this.creatorID = creatorID;
        this.name = name;
        this.dateTimeCreated = dateTimeCreated;
        this.creator = creator;
        this.numCards = numCards;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getImage() {
        return image;
    }

    public void setImage(String creatorImage) {
        this.image = creatorImage;
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
