package net.project.studyez.decks;


import net.project.studyez.cards.Card;

import java.util.ArrayList;


public class Deck {

    private String name;
    private ArrayList<Card> cards;
    private String id;
    private String creator;
    private String image;
    private String dateTimeCreated;
    private int numCards;

    public Deck() {}

    public Deck(String name, String dateTimeCreated, String creator, int numCards) {
        this.name = name;
        this.dateTimeCreated = dateTimeCreated;
        this.creator = creator;
        this.numCards = numCards;
    }

    public Deck(String name, ArrayList<Card> cards) {
        this.name = name;
        this.cards = cards;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public void setCards(ArrayList<Card> cards) {
        this.cards = cards;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
