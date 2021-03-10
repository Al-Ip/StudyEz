package net.project.studyez.flashCard;

import androidx.recyclerview.widget.DiffUtil;

import net.project.studyez.cards.Card;

import java.util.List;

public class NewFlashcardCallback extends DiffUtil.Callback {

    private final List<Card> old;
    private final List<Card> baru;

    public NewFlashcardCallback(List<Card> old, List<Card> baru) {
        this.old = old;
        this.baru = baru;
    }

    @Override
    public int getOldListSize() {
        return old.size();
    }

    @Override
    public int getNewListSize() {
        return baru.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return old.get(oldItemPosition).getImage() == baru.get(newItemPosition).getImage();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return old.get(oldItemPosition) == baru.get(newItemPosition);
    }
}