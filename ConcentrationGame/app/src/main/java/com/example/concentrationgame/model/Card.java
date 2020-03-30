package com.example.concentrationgame.model;

import android.content.Context;
import android.media.Image;
import android.util.AttributeSet;
import android.widget.ImageView;

import java.io.Serializable;

public class Card extends android.support.v7.widget.AppCompatImageView implements Serializable {

    private boolean isFaceUp;

    private int imageId;

    public Card(Context context) {
        super(context);
    }

    public Card(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Card(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public boolean isFaceUp() {
        return isFaceUp;
    }

    public void setFaceUp(boolean faceUp) {
        isFaceUp = faceUp;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }
}
