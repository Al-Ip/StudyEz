package net.project.studyez.home.quickStudy;

import java.time.LocalTime;

public class QuickStudySession {

    String sessionMode;
    String deckName;
    int numCards;
    int answeredCorrectly;
    String sessionStartTime;
    String sessionEndTime;
    long secondsToFinish;

    public QuickStudySession(){
    }

    public QuickStudySession(String sessionMode, String deckName, int numCards, String sessionStartTime){
        this.sessionMode = sessionMode;
        this.deckName = deckName;
        this.numCards = numCards;
        this.sessionStartTime = sessionStartTime;
    }

    public QuickStudySession(String sessionMode, String deckName, int numCards, int answeredCorrectly, String sessionStartTime, String sessionEndTime){
        this.sessionMode = sessionMode;
        this.deckName = deckName;
        this.numCards = numCards;
        this.answeredCorrectly = answeredCorrectly;
        this.sessionStartTime = sessionStartTime;
        this.sessionEndTime = sessionEndTime;
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
}
