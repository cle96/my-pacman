package org.example.pacman;

import android.content.Context;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class GameView extends View {

    Game game;
    int h, w; //used for storing our height and width of the view

    public void setGame(Game game) {
        this.game = game;
    }


    /* The next 3 constructors are needed for the Android view system,
    when we have a custom view.
     */
    public GameView(Context context) {
        super(context);

    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    public GameView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(final Canvas canvas) {
        if (!game.gameOver) {
            h = canvas.getHeight();
            w = canvas.getWidth();

            game.setSize(h, w);
            Log.d("GAMEVIEW", "h = " + h + ", w = " + w);
            Paint paint = new Paint();
            canvas.drawColor(Color.WHITE);

            canvas.drawBitmap(game.getPacBitmap(), game.getPacx(), game.getPacy(), paint);
            canvas.drawBitmap(game.enemy.getBitMap(), game.enemy.x, game.enemy.y, paint);
            for (GoldCoin coin : game.getCoins()) {
                canvas.drawBitmap(coin.getBitMap(), coin.x, coin.y, paint);
            }

            if (!game.gamePaused) {
                final int MOVE_SPEED = 5;
                switch (game.direction) {
                    case UP:
                        game.movePacmanUp(MOVE_SPEED);
                        break;
                    case DOWN:
                        game.movePacmanDown(MOVE_SPEED);
                        break;
                    case LEFT:
                        game.movePacmanLeft(MOVE_SPEED);
                        break;
                    case RIGHT:
                        game.movePacmanRight(MOVE_SPEED);
                        break;
                }
                game.moveEnemyRandomly(MOVE_SPEED + 5);
                directionTimer();
            }
            super.onDraw(canvas);
        }
    }


    public void directionTimer() {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {

            }
        }, 2 * 60 * 1000);
    }
}
