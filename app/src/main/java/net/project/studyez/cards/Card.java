package net.project.studyez.cards;

import android.os.Parcel;
import android.os.Parcelable;

import net.project.studyez.R;
import net.project.studyez.decks.Deck;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Card extends Deck implements Parcelable {

    //Keep these variable names the same as those in the firebase database
    private String deckName;
    private String question;
    private String answer;
    private boolean isStarred;
    private String dateTimeCreated;
    boolean isFlipped;

    public Card(){}

    public Card(String deckName, String question, String answer, String dateTimeCreated, boolean isStarred) {
        this.deckName = deckName;
        this.question = question;
        this.answer = answer;
        this.dateTimeCreated = dateTimeCreated;
        this.isStarred = isStarred;
    }

    public Card(String question, String answer){
        this.question = question;
        this.answer = answer;
    }

    public String getDeckName() {
        return deckName;
    }

    public void setDeckName(String deckName) {
        this.deckName = deckName;
    }

    public boolean isStarred() {
        return isStarred;
    }

    public void setStarred(boolean starred) {
        isStarred = starred;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getDateTimeCreated() {
        return dateTimeCreated;
    }

    public void setDateTimeCreated(String dateTimeCreated) {
        this.dateTimeCreated = dateTimeCreated;
    }

    public boolean isFlipped() {
        return isFlipped;
    }

    public void setFlipped(boolean flipped) {
        isFlipped = flipped;
    }

    public void toggleStarred() {
        isStarred = !isStarred;
    }

    public static int getRandomCardColor(){
        List<Integer> colorCode = new ArrayList<>();
        colorCode.add(R.color.silverPink);
        colorCode.add(R.color.canary);
        colorCode.add(R.color.grannySmithApple);
        colorCode.add(R.color.fireOpal);
        colorCode.add(R.color.beige);
        colorCode.add(R.color.babyPink);
        colorCode.add(R.color.lightStaleGray);
        colorCode.add(R.color.lightPink);
        colorCode.add(R.color.maximumPurple);
        colorCode.add(R.color.azureWebColor);
        colorCode.add(R.color.maroon);
        colorCode.add(R.color.oldRose);
        colorCode.add(R.color.maximumBluePurple);
        colorCode.add(R.color.uranianBlue);
        colorCode.add(R.color.champagnePink);
        colorCode.add(R.color.magicMint);
        colorCode.add(R.color.purpureus);
        colorCode.add(R.color.darkSalmon);
        colorCode.add(R.color.teaGreen);
        colorCode.add(R.color.almond);
        colorCode.add(R.color.deepChampagne);
        colorCode.add(R.color.opal);
        colorCode.add(R.color.lighCyan);
        colorCode.add(R.color.lavendarFloral);
        colorCode.add(R.color.wisteria);

        Random randomColor = new Random();
        int number = randomColor.nextInt(colorCode.size());
        return colorCode.get(number);
    }

    @Override
    public String toString() {
        return "Card{" +
                "deckName='" + deckName + '\'' +
                ", question='" + question + '\'' +
                ", answer='" + answer + '\'' +
                ", isStarred=" + isStarred +
                ", dateTimeCreated='" + dateTimeCreated + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(question);
        dest.writeString(answer);
        dest.writeByte((byte) (isStarred ? 1 : 0));
    }

    protected Card(Parcel in) {
        question = in.readString();
        answer = in.readString();
        isStarred = in.readByte() != 0;
    }

    public static final Creator<Card> CREATOR = new Creator<Card>() {
        @Override
        public Card createFromParcel(Parcel in) {
            return new Card(in);
        }

        @Override
        public Card[] newArray(int size) {
            return new Card[size];
        }
    };


}
