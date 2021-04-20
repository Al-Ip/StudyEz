package net.project.studyez.home.quickStudy;

import java.util.Date;

public class QuickStudySession {

    String sessionMode;
    String deckName;
    int numCards;
    int answeredCorrectly;
    Date dateCreated;
    String sessionStartTime;
    String sessionEndTime;
    long secondsToFinish;
    int ezPointsEarned;

    public QuickStudySession(){
    }

    public QuickStudySession(Date dateCreated){
        this.dateCreated = dateCreated;
    }

    public QuickStudySession(String sessionMode, String deckName, int numCards, String sessionStartTime, Date dateCreated, int ezPointsEarned){
        this.sessionMode = sessionMode;
        this.deckName = deckName;
        this.numCards = numCards;
        this.sessionStartTime = sessionStartTime;
        this.dateCreated = dateCreated;
        this.ezPointsEarned = ezPointsEarned;
    }

    public String getSessionMode() {
        return sessionMode;
    }

    public void setSessionMode(String sessionMode) {
        this.sessionMode = sessionMode;
    }

    public String getDeckName() {
        return deckName;
    }

    public void setDeckName(String deckName) {
        this.deckName = deckName;
    }

    public int getNumCards() {
        return numCards;
    }

    public void setNumCards(int numCards) {
        this.numCards = numCards;
    }

    public int getAnsweredCorrectly() {
        return answeredCorrectly;
    }

    public void setAnsweredCorrectly(int answeredCorrectly) {
        this.answeredCorrectly = answeredCorrectly;
    }

    public String getSessionStartTime() {
        return sessionStartTime;
    }

    public void setSessionStartTime(String sessionStartTime) {
        this.sessionStartTime = sessionStartTime;
    }

    public String getSessionEndTime() {
        return sessionEndTime;
    }

    public void setSessionEndTime(String sessionEndTime) {
        this.sessionEndTime = sessionEndTime;
    }

    public long getSecondsToFinish() {
        return secondsToFinish;
    }

    public void setSecondsToFinish(long secondsToFinish) {
        this.secondsToFinish = secondsToFinish;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public int getEzPointsEarned() {
        return ezPointsEarned;
    }

    public void setEzPointsEarned(int ezPointsEarned) {
        this.ezPointsEarned = ezPointsEarned;
    }
}
