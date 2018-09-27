package org.example.pacman;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class GoldCoin {
    private Bitmap coin;
    public int x;
    public int y;

    public GoldCoin(Context context){
        coin = BitmapFactory.decodeResource(context.getResources(), R.drawable.coin);
    }

    public Bitmap getBitMap(){
        return this.coin;
    }
}
