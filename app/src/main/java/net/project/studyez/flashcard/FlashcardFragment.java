package net.project.studyez.flashcard;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;

import com.yuyakaido.android.cardstackview.CardStackLayoutManager;
import com.yuyakaido.android.cardstackview.CardStackListener;
import com.yuyakaido.android.cardstackview.CardStackView;
import com.yuyakaido.android.cardstackview.Direction;
import com.yuyakaido.android.cardstackview.StackFrom;
import com.yuyakaido.android.cardstackview.SwipeableMethod;

import net.project.studyez.R;
import net.project.studyez.cards.Card;
import net.project.studyez.main.MainActivity;
import net.project.studyez.study_session.StudySessionFinishedFragment;

import java.time.LocalTime;
import java.util.List;

import static net.project.studyez.home.quickStudy.QuickStudyFragment.deckID;
import static net.project.studyez.home.quickStudy.QuickStudyFragment.deckName;


public class FlashcardFragment extends Fragment implements FlashCardContract.view{

    private static final String TAG = FlashcardFragment.class.getSimpleName();
    private CardStackLayoutManager manager;
    private CardStackView cardStackView;
    private FlashcardAdapter adapter;
    private SeekBar seekBar;
    private Toolbar toolbar;
    private FlashCardPresenter presenter;
    private List<Card> cardList;
    private int seekBarCounter = 1;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_study, container, false);

        presenter = new FlashCardPresenter(this);
        toolbar = root.findViewById(R.id.flashcardToolbar);
        seekBar = root.findViewById(R.id.seekBar);
        cardStackView = root.findViewById(R.id.card_stack_view);

        presenter.getCardsFromDeck(deckID);
        initToolbar();
        initCardStackLayout(root);

        return root;
    }

    private void initToolbar(){
        ((MainActivity)getActivity()).getSupportActionBar().hide();
        toolbar.setTitle(deckName);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        toolbar.setTitleTextColor(color(R.color.white));
        toolbar.setNavigationOnClickListener(v -> getActivity().onBackPressed());
    }

    private void setSeekBar() {
        Log.e("GetDeckBar", String.valueOf(seekBar.getMax()));
        // Allows the progress bar to animate for API 24 and above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            seekBar.setProgress(seekBarCounter++, true);
            Log.e("SETDeckBar", String.valueOf(seekBarCounter));
            Log.e("manager", String.valueOf(manager.getChildCount()));
            Log.e("adapter", String.valueOf(adapter.getItemCount()));
        } else {
            Log.e("SETDeckBar", String.valueOf(seekBarCounter));
            seekBar.setProgress(seekBarCounter++);
        }
    }

    @ColorInt
    public int color(@ColorRes int res){
        return ContextCompat.getColor(getContext(), res);
    }

    private void initCardStackLayout(View root) {
        manager = new CardStackLayoutManager(getContext(), new CardStackListener() {
            @Override
            public void onCardDragging(Direction direction, float ratio) {
                Log.d(TAG, "onCardDragging: d=" + direction.name() + " ratio=" + ratio);
                if (direction == Direction.Left && ratio > 0.3) {

                }
                else if (direction == Direction.Right && ratio > 0.3) {

                }
                else {

                }
            }

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onCardSwiped(Direction direction) {
                Log.d(TAG, "onCardSwiped: p=" + manager.getTopPosition() + " d=" + direction);
                if (direction == Direction.Right){
                    //Toast.makeText(getContext(), "Direction Right", Toast.LENGTH_SHORT).show();
                    presenter.swipedRight();
                }
                if (direction == Direction.Left){
                    //Toast.makeText(getContext(), "Direction Left", Toast.LENGTH_SHORT).show();
                }
                // At the end of cards
                if (seekBarCounter > seekBar.getMax() + 1) {
                    // -------------------- last position reached, do something ---------------------
                    //Toast.makeText(getContext(), "FINISHD ALL THE CARDS", Toast.LENGTH_SHORT).show();-
                    presenter.endStudySession();
                    presenter.finishedCards(new StudySessionFinishedFragment(), R.id.main_container);
                }
            }

            @Override
            public void onCardRewound() {
                Log.d(TAG, "onCardRewound: " + manager.getTopPosition());
            }

            @Override
            public void onCardCanceled() {
                Log.d(TAG, "onCardRewound: " + manager.getTopPosition());
            }

            @Override
            public void onCardAppeared(View view, int position) {
                //TextView tv = view.findViewById(R.id.cardFrontText);
                //Log.d(TAG, "onCardAppeared: " + position + ", nama: " + tv.getText());
            }

            @Override
            public void onCardDisappeared(View view, int position) {
                setSeekBar();
                //TextView tv = view.findViewById(R.id.cardText);
                //Log.d(TAG, "onCardAppeared: " + position + ", nama: " + tv.getText());
            }
        });
        manager.setStackFrom(StackFrom.Top);
        manager.setVisibleCount(3);
        manager.setTranslationInterval(8.0f);
        manager.setScaleInterval(0.95f);
        manager.setSwipeThreshold(0.3f);
        manager.setMaxDegree(20.0f);
        manager.setDirections(Direction.HORIZONTAL);
        manager.setCanScrollHorizontal(true);
        manager.setSwipeableMethod(SwipeableMethod.Manual);
        manager.setOverlayInterpolator(new LinearInterpolator());
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void initFlashCards(List list) {
        cardList = list;
        seekBar.setMax(cardList.size() - 1);
        adapter = new FlashcardAdapter(getContext(), cardList);
        cardStackView.setLayoutManager(manager);
        cardStackView.setAdapter(adapter);
        cardStackView.setItemAnimator(new DefaultItemAnimator());

        // Starting study Session
        presenter.initStudySession("quickStudy", deckName, cardList.size(), LocalTime.now());
    }

    @Override
    public void displayFailedToWriteMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void changeFragment(Fragment fragment, int id) {
        ((MainActivity) getActivity()).changeFragment(fragment, id, false);
    }
}
