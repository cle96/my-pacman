package org.example.pacman;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Enemy {
    private Bitmap enemy;
    public int x;
    public int y;
    public Game.Orientation direction;
    public int timesMove;

    public Enemy(Context context){
        enemy = BitmapFactory.decodeResource(context.getResources(), R.drawable.enemy);
        direction = Game.Orientation.LEFT;
        timesMove = 1;
    }

    public Bitmap getBitMap(){
        return this.enemy;
    }
}
