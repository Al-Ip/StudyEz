package net.project.studyez.cards;

import net.project.studyez.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Card {
    //Keep these variable names the same as those in the firebase database
    private String question;
    private String answer;
    private boolean isStarred;

    public Card(){}

    public Card(String question, String answer){
        this.question = question;
        this.answer = answer;
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

}
