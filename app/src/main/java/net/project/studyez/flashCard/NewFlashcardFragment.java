package net.project.studyez.flashCard;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DiffUtil;

import com.yuyakaido.android.cardstackview.CardStackLayoutManager;
import com.yuyakaido.android.cardstackview.CardStackListener;
import com.yuyakaido.android.cardstackview.CardStackView;
import com.yuyakaido.android.cardstackview.Direction;
import com.yuyakaido.android.cardstackview.StackFrom;
import com.yuyakaido.android.cardstackview.SwipeableMethod;

import net.project.studyez.R;
import net.project.studyez.cards.Card;

import java.util.ArrayList;
import java.util.List;

public class NewFlashcardFragment extends Fragment {

    private static final String TAG = NewFlashcardFragment.class.getSimpleName();
    private CardStackLayoutManager manager;
    private NewFlashcardAdapter adapter;
    private ImageView correct, incorrect;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.new__fragment_quickstudy, container, false);
        init(root);
        return root;
    }

    private void init(View root) {
        correct = root.findViewById(R.id.correctAnswer);
        incorrect = root.findViewById(R.id.wrongAnswer);
//        Animation myFadeInAnimation = AnimationUtils.loadAnimation(root.getContext(), R.anim.fade_in);
//        correct.startAnimation(myFadeInAnimation); //Set animation to your ImageView
//        incorrect.startAnimation(myFadeInAnimation); //Set animation to your ImageView

        CardStackView cardStackView = root.findViewById(R.id.card_stack_view);
        manager = new CardStackLayoutManager(getContext(), new CardStackListener() {
            @Override
            public void onCardDragging(Direction direction, float ratio) {
                Log.d(TAG, "onCardDragging: d=" + direction.name() + " ratio=" + ratio);
                if (direction == Direction.Left && ratio > 0.4) {
                    incorrect.setVisibility(View.VISIBLE);
                    correct.setVisibility(View.INVISIBLE);
                }
                else if (direction == Direction.Right && ratio > 0.4) {
                    correct.setVisibility(View.VISIBLE);
                    incorrect.setVisibility(View.INVISIBLE);
                }
                else {
                    correct.setVisibility(View.INVISIBLE);
                    incorrect.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onCardSwiped(Direction direction) {
                Log.d(TAG, "onCardSwiped: p=" + manager.getTopPosition() + " d=" + direction);
                if (direction == Direction.Right){
                    Toast.makeText(getContext(), "Direction Right", Toast.LENGTH_SHORT).show();
                }
                if (direction == Direction.Left){
                    Toast.makeText(getContext(), "Direction Left", Toast.LENGTH_SHORT).show();
                }

                // Paginating
                if (manager.getTopPosition() == adapter.getItemCount() - 5){
                    paginate();
                }

            }

            @Override
            public void onCardRewound() {
                correct.setVisibility(View.INVISIBLE);
                incorrect.setVisibility(View.INVISIBLE);
                Log.d(TAG, "onCardRewound: " + manager.getTopPosition());
            }

            @Override
            public void onCardCanceled() {
                correct.setVisibility(View.INVISIBLE);
                incorrect.setVisibility(View.INVISIBLE);
                Log.d(TAG, "onCardRewound: " + manager.getTopPosition());
            }

            @Override
            public void onCardAppeared(View view, int position) {
                TextView tv = view.findViewById(R.id.cardText);
                Log.d(TAG, "onCardAppeared: " + position + ", nama: " + tv.getText());
            }

            @Override
            public void onCardDisappeared(View view, int position) {
                correct.setVisibility(View.INVISIBLE);
                incorrect.setVisibility(View.INVISIBLE);
                TextView tv = view.findViewById(R.id.cardText);
                Log.d(TAG, "onCardAppeared: " + position + ", nama: " + tv.getText());
            }
        });
        manager.setStackFrom(StackFrom.None);
        manager.setVisibleCount(3);
        manager.setTranslationInterval(8.0f);
        manager.setScaleInterval(0.95f);
        manager.setSwipeThreshold(0.3f);
        manager.setMaxDegree(20.0f);
        manager.setDirections(Direction.HORIZONTAL);
        manager.setCanScrollHorizontal(true);
        manager.setSwipeableMethod(SwipeableMethod.Manual);
        manager.setOverlayInterpolator(new LinearInterpolator());
        adapter = new NewFlashcardAdapter(addList());
        cardStackView.setLayoutManager(manager);
        cardStackView.setAdapter(adapter);
        cardStackView.setItemAnimator(new DefaultItemAnimator());
    }

    private void paginate() {
        List<Card> old = adapter.getItems();
        List<Card> baru = new ArrayList<>(addList());
        NewFlashcardCallback callback = new NewFlashcardCallback(old, baru);
        DiffUtil.DiffResult hasil = DiffUtil.calculateDiff(callback);
        adapter.setItems(baru);
        hasil.dispatchUpdatesTo(adapter);
    }

    private List<Card> addList() {
        List<Card> items = new ArrayList<>();
        items.add(new Card("My Favourite color?", "black"));
        items.add(new Card("The color of the sky?", "blue"));
        items.add(new Card("The earths size in cm?", "Uhmmmmmmm What?"));
        items.add(new Card("Reason why the sky is blue?", "Because it has the color blue"));
        items.add(new Card("When are pubs opening up again?", "Never :'("));
        items.add(new Card("Hopscotch or doritos?", "Candles"));
        items.add(new Card("Nickname in high school?", "black"));
        items.add(new Card("The Love of my life?", "blue"));
        items.add(new Card("Sauraons intentions in LOTR?", "Uhmmmmmmm What?"));
        items.add(new Card("How to get more friends?", "Because it has the color blue"));
        items.add(new Card("The app is exactly like tinder haha", "Never :'("));
        items.add(new Card("What you think?", "Candles"));

        return items;
    }


}
