package com.example.concentrationgame;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.concentrationgame.model.Card;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private final static String APP_LOG = "concentration_game_log";

    private static int flipsCount;

    private ConstraintLayout mainContainer;

    private TextView flipCountText;

    private List<Integer> cardIds;

    private List<Card> faceUpCards;

    Random randomGenerator = new Random();

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Card card = (Card) v;
            onCardClick(card);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(APP_LOG, "onCreate");
        init();
    }

    private void init() {
        cardIds = new ArrayList<>();
        faceUpCards = new ArrayList<>();
        flipsCount = 0;
        mainContainer = findViewById(R.id.mainContainer);
        flipCountText = findViewById(R.id.flipCountText);
        initCardsIds();

        for (int i = 0; i < mainContainer.getChildCount(); i++) {
            View view = mainContainer.getChildAt(i);
            if(view instanceof Card){
                Card card = (Card) view;
                card.setImageId(getRandomCardId());
                card.setImageResource(R.drawable.card_back);
                card.setOnClickListener(onClickListener);
            }
        }
    }

    private void initCardsIds() {
        for (int i = 0; i < 2; i++) {
            cardIds.add(R.drawable.lebron);
            cardIds.add(R.drawable.kobe);
            cardIds.add(R.drawable.shaq);
            cardIds.add(R.drawable.duncun);
            cardIds.add(R.drawable.wade);
            cardIds.add(R.drawable.curry);
        }
    }

    private int getRandomCardId() {
        int randomIndex = randomGenerator.nextInt(cardIds.size());
        int result = cardIds.get(randomIndex);
        cardIds.remove(randomIndex);
        return result;
    }

    private void onCardClick(Card card) {
        if (!card.isFaceUp()) {
            if (faceUpCards.size() == 2) {
                if (faceUpCards.get(0).getImageId() == faceUpCards.get(1).getImageId()) {
                    faceUpCards.get(0).setVisibility(View.INVISIBLE);
                    faceUpCards.get(1).setVisibility(View.INVISIBLE);
                } else {
                    faceDownCard(faceUpCards.get(0));
                    faceDownCard(faceUpCards.get(1));
                }
                faceUpCards.clear();
            }

            faceUpCard(card);
        }
    }

    private void faceDownCard(Card card) {
        card.setImageResource(R.drawable.card_back);
        card.setFaceUp(false);
    }

    private void faceUpCard(Card card) {
        card.setImageResource(card.getImageId());
        card.setFaceUp(true);
        faceUpCards.add(card);
        updateFlipsCount();
    }

    private void updateFlipsCount(){
        Log.d(APP_LOG, "Flips Count Updated to " + flipsCount);
        flipCountText.setText("Flips: " + (++flipsCount));
    }
}

